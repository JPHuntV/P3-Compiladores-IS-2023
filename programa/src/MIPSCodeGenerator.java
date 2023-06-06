package src;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;


public class MIPSCodeGenerator {

    private static FileWriter writer;
    private StringBuilder code;
    String dotData = ".data\n";
    int tempCount = 0;
    int floatCount = 0;
    int floatTempCount = 0;
    boolean[] tRegisters = new boolean[10];
    Map<String, String> dataTypesLoads = new HashMap<String, String>();
    Map<String, String> dataTypesStores = new HashMap<String, String>();
    Map<String, String> dotDatMap = new HashMap<String, String>();
    Map<String, String> tempValues = new HashMap<String, String>();
    Map<String, String> dataTypesTest = new HashMap<String, String>();
    Map<String, String> interToGoal = new HashMap<String, String>();//<intermediate, goal> guardar los temporales del codigo intermedio con su equivalente en el codigo final
    //calling function stack list
    String[] callingFunctionStack = new String[100];
    ArrayList<String> paramStack = new ArrayList<String>();
    String currentDataType = "";

    public void generateMips() {
        code = new StringBuilder();
        InicializarDiccionarios();

        String codigoIntermedio = getCode();
        traducir(codigoIntermedio);
    }

    private static String getCode() {
        //how to transfor txt file to string
        String code = readFileAsString("programa/resultados/codigoIntermedio.txt");
        //System.out.println(code);
        return code; // Return the code as a string
    }

    private void traducir(String code) {
        try {
            writer = new FileWriter("programa/resultados/output.asm");
            escribirEncabezado();
            traducirCodigo(code);
            writer.write(dotData);
            writer.write(this.code.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void escribirEncabezado() throws IOException {
        //writer.write(".data\n");
        code.append(".text\n");
        code.append(".globl main\n");
    }

    private void traducirCodigo(String code) throws IOException {
        String[] lines = code.split("\n");

        for (String line : lines) {
            line = line.trim();

            if (line.isEmpty() || line.startsWith("#"))
                continue;

            if (line.endsWith(":")) {
                writeLabel(line);
            } else if (line.startsWith("")) {
                writeInstruction(line);
            }
        }
    }

    private void writeLabel(String label) throws IOException {
        code.append(label + "\n");
    }

    private void writeInstruction(String instruction) throws IOException {
        // Translate the instruction to MIPS and write it to the file
        // Handle each instruction accordingly
        String[] data = instruction.split(" ");
        int largo = data.length;
        if(instruction.startsWith("dataArray")){
            code.append("#declaracion de array\n");     
            String nombreArray = data[1].substring(0, data[1].indexOf("["));
            String size = data[1].substring(data[1].indexOf("[")+1, data[1].indexOf("]"));
            dotData += nombreArray + ": .space " + Integer.toString(Integer.parseInt(size)*4) + "\n";
            return;
        }
        else if(instruction.startsWith("dataChar")){
            code.append("#declaracion de char\n");
            String nombreChar = data[1];
            dotData += nombreChar + ": .space 1\n";
            return;
        }
        else if(instruction.startsWith("dataInt")){
            code.append("#declaracion de int\n");
            String nombreInt = data[1];
            dotData += nombreInt + ": .word 0\n";
            dotDatMap.put(nombreInt, "int");
            currentDataType = "int";
            return;
        }
        else if(instruction.startsWith("dataFloat")){
            code.append("#declaracion de float\n");
            String nombreFloat = data[1];
            dotData += nombreFloat + ": .float 0.0\n";
            dotDatMap.put(nombreFloat, "float");
            currentDataType = "float";
            return;
        }
        else if(instruction.startsWith("if")){
            code.append("#if\n");
            code.append("beqz $t" + (tempCount-1) + ", " + data[3] + "\n");
        }
        else if(instruction.startsWith("goto")){
            code.append("#goto\n");
            code.append("j " + data[1] + "\n");
        }
        else if(instruction.startsWith("return")){
            if(callingFunctionStack[callingFunctionStack.length-1] != null){
                code.append("#return\n");
                code.append("j " + callingFunctionStack[callingFunctionStack.length-1] + "\n");
            }
            else{
                code.append("#return\n");
                code.append("li $v0, 10\n");
                code.append("syscall\n");
            }
        }
        else if(instruction.startsWith("param")){
            paramStack.add(data[1]);
        }
        else if(instruction.startsWith("call")){
            if(data[1].equals("print,")){
                code.append("#print\n");
                String param = paramStack.remove(paramStack.size()-1);
                //System.out.println(param);
                String registroParam = interToGoal.get(param);
                System.out.println(registroParam);
                System.out.println(tempValues.get(registroParam));
                //code.append( dataTypesLoads.get(dotDatMap.get(tempValues.get(param)))+", "+tempValues.get(param)+ " ---\n");
                code.append("move $a0, $" + param+ "\n");
                //code.append("li $v0, 1\n");
                code.append("li $v0, 1\n");
                code.append("syscall\n");
            }



                
        }
        else if(instruction.startsWith("t")){
            if(largo == 3){
                if(data[2].startsWith("++")){
                    code.append("lw $t" + tempCount + ", " + data[2].substring(2) + "\n");
                    tempCount++;
                    code.append("addi $t" + tempCount + ", $t" + (tempCount-1) + ", 1\n");
                    code.append("sw $t" + tempCount + ", " + data[2].substring(2) + "\n");
                    return;
                }
                else if(data[2].startsWith("'") || isStringInteger(data[2])){
                    code.append("li $t" + tempCount + ", " + data[2] + "\n");
                    //code.append("sb $t" + tempCount + ", " + data[0] + "\n");
                    tempCount++;
                }
                else if(isStringFloat(data[2])){
                    //dotData += "float" + floatCount + ": .float " + data[2] + "\n";
                    code.append("li.s $f" + floatTempCount + ", " + data[2] + "\n");
                    //floatCount++;
                    floatTempCount++;
                }
                else if(instruction.contains("= t") && largo == 3){
                    code.append("sb $t" + (tempCount) + ", " + data[2] + "\n");
                }else{
                    String register = getRegisterAsig(dotDatMap.get(data[2])).substring(1);
                    code.append(dataTypesLoads.get(dotDatMap.get(data[2]))+ " "+ register + ", " + data[2] + "\n");
                    //code.append("sb $t" + tempCount + ", " + data[0] + "\n");
                    System.out.println("inter:"+ data[0] +" register:"+ register +    ",  data[2] = " + data[2]);
                    tempValues.put(register, data[2]);

                    interToGoal.put(data[0], register);
                    //tempCount++;
                }
            }
            else if(largo == 5){
                String op = data[3];
                code.append("#op = " + op + "\n");
                if(op.equals("+")){
                    if(currentDataType.equals("int")){
                        code.append("add $t" + tempCount + ", $t" + (tempCount-2) + ", $t" + (tempCount-1) + "\n");
                        tempCount++;
                    }
                    else if(currentDataType.equals("float")){
                        code.append("add.s $f" + floatTempCount + ", $f" + (floatTempCount-2) + ", $f" + (floatTempCount-1) + "\n");
                        floatTempCount++;
                    }
                    //code.append("sb $t" + tempCount + ", " + data[0] + "\n");
                }
                else if(op.equals("-")){
                    code.append("sub $t" + tempCount + ", $t" + (tempCount-2) + ", $t" + (tempCount-1) + "\n");
                    tempCount++;
                    code.append("move $t" + tempCount + ", $t" + (tempCount-1) + "\n");
                    //code.append("sb $t" + tempCount + ", " + data[0] + "\n");                
                }
                else if(op.equals(">")){
                    code.append("sgt $t" + tempCount + ", $t" + (tempCount-2) + ", $t" + (tempCount-1) + "\n");
                    tempCount++;
                }
                else if(op.equals("<")){
                    code.append("slt $t" + tempCount + ", $t" + (tempCount-2) + ", $t" + (tempCount-1) + "\n");
                    tempCount++;
                }
                else if(op.equals("==")){
                    code.append("seq $t" + tempCount + ", $t" + (tempCount-2) + ", $t" + (tempCount-1) + "\n");
                    tempCount++;
                }
                else if(op.equals("!=")){
                    code.append("sne $t" + tempCount + ", $t" + (tempCount-2) + ", $t" + (tempCount-1) + "\n");
                    tempCount++;
                }
                else if(op.equals("^")){
                    code.append("and $t" + tempCount + ", $t" + (tempCount-2) + ", $t" + (tempCount-1) + "\n");
                    tempCount++;
                }
                else if(op.equals("#")){
                    code.append("or $t" + tempCount + ", $t" + (tempCount-2) + ", $t" + (tempCount-1) + "\n");
                    tempCount++;
                }
                else if(op.equals("*")){
                    code.append("mult $t" + (tempCount-2) + ", $t" + (tempCount-1) + "\n");
                    code.append("mflo $t" + tempCount + "\n");
                    tempCount++;
                }
                else if(op.equals("/")){
                    code.append("div $t" + (tempCount-2) + ", $t" + (tempCount-1) + "\n");
                    code.append("mflo $t" + tempCount + "\n");
                    tempCount++;
                }
            }
            return;
        }
        else if(asignacionTemp(instruction) && largo == 3){
            if(currentDataType.equals("int")){
                code.append(dataTypesStores.get(currentDataType)+" $t" + (tempCount-1) + ", " + data[0] + "\n");
            }
            else if(currentDataType.equals("float")){
                code.append(dataTypesStores.get(currentDataType)+" $f" + (floatTempCount-1) + ", " + data[0] + "\n");
            }
            
        

            }

        else{

            code.append(instruction + "\n");
        }
    }

    public boolean asignacionTemp(String instruction){
        Pattern pattern = Pattern.compile("= t\\d+");
        Matcher matcher = pattern.matcher(instruction);
        return matcher.find();
    }

    public static String readFileAsString(String fileName) {
        String text = "";
        try {
          text = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
          e.printStackTrace();
        }
    
        return text;
    }

    public static boolean isStringInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isStringFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void InicializarDiccionarios(){
        dataTypesLoads.put("int", "lw");
        dataTypesLoads.put("float", "lwci");
        dataTypesLoads.put("char", "lb");
        dataTypesLoads.put("String", "la");

        dataTypesStores.put("int", "sw");
        dataTypesStores.put("float", "swci");
        dataTypesStores.put("char", "sb");
        dataTypesStores.put("String", "la");


        //dataTypesTest.put("int", "$t"+tempCount);


        for(int i = 0; i < 11; i++){
            this.tempValues.put("t" + i, "");
        }
    }

    public String getRegisterAsig(String dataType){
        if(dataType.equals("int")){
            tempCount++;
            return " $t" + (tempCount-1);
        }
        else if(dataType.equals("float")){
            floatTempCount++;
            return " $f" + (floatTempCount-1);
        }
        else if(dataType.equals("char")){
            tempCount++;
            return " $t" + tempCount;
        }
        else if(dataType.equals("String")){
            tempCount++;
            return " $t" + tempCount;
        }
        return "";
    }
}
