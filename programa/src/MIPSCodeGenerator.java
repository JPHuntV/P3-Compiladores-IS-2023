package src;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MIPSCodeGenerator {

    private static FileWriter writer;
    private StringBuilder code;
    String dotData = ".data\n";
    int tempCount = 0;

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
        if(instruction.startsWith("dataArray")){
            code.append("#declaracion de array\n");
            String[] data = instruction.split(" ");
            String nombreArray = data[1].substring(0, data[1].indexOf("["));
            String size = data[1].substring(data[1].indexOf("[")+1, data[1].indexOf("]"));
            dotData += nombreArray + ": .space " + Integer.toString(Integer.parseInt(size)*4) + "\n";
            return;
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
}
