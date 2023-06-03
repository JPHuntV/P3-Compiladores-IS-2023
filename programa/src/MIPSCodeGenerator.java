package src;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import jflex.core.RegExp;

public class MIPSCodeGenerator {

    private static FileWriter writer;
    private StringBuilder code;
    String dotData = ".data\n";
    int tempCount = 0;
    int floatCount = 0;
    int floatTempCount = 0;
    boolean[] tRegisters = new boolean[10];

    public void generateMips() {
        code = new StringBuilder();
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
        code.append("main:\n");
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
            return;
        }
        else if(instruction.startsWith("dataFloat")){
            code.append("#declaracion de float\n");
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
        else if(instruction.startsWith("t")){
            code.append("#declaracion de temporal\n");
            code.append("#largo = " +data.length + "\n");
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
                    dotData += "float" + floatCount + ": .float " + data[2] + "\n";
                    code.append("l.s $f" + floatTempCount + ", float" + floatCount + "\n");
                    floatCount++;
                    floatTempCount++;
                }
                else if(instruction.contains("= t") && largo == 3){
                    code.append("sb $t" + (tempCount) + ", " + data[2] + "\n");
                }else{
                    code.append("li $t" + tempCount + ", " + data[2] + "\n");
                    //code.append("sb $t" + tempCount + ", " + data[0] + "\n");
                    tempCount++;
                }
            }
            else if(largo == 5){
                String op = data[3];
                code.append("#op = " + op + "\n");
                if(op.equals("+")){
                    code.append("add $t" + tempCount + ", $t" + (tempCount-2) + ", $t" + (tempCount-1) + "\n");
                    tempCount++;
                    code.append("move $t" + tempCount + ", $t" + (tempCount-1) + "\n");
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
        else if(instruction.contains("= t") && largo == 3){
            code.append("sb $t" + (tempCount-1) + ", " + data[0] + "\n");
        }

        else{

            code.append(instruction + "\n");
        }
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
}
