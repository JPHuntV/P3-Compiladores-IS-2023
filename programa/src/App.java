package src;

import java_cup.runtime.Symbol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.Map;


public class App {
    /**
     * analizar
     * E::archivoFuente: ruta del archivo que contiene el codigo a analizar
     * S::Escritura de un archivo .txt que contiene todos los tokens encontrados en archivoFuente
     * R::archivoFuente debe ser una ruta valida
     * O::Analizar los lexemas de un archivoFuente y listarlos en un archivo como resultado 
     */
    public void analizar(String archivoFuente) {
        try {
            Reader reader = new BufferedReader(new FileReader(archivoFuente));
            BufferedWriter writer = new BufferedWriter(new FileWriter("programa/resultados/lexemas.txt")); 
            Analizador analizador = new Analizador(reader);
            int i = 0;
            Symbol token;
            while(true){
                token = analizador.next_token();
                if(token.sym != 0){
                    System.out.println("I: " + i + "\tToken: " + token.sym + "\tvalor: "+analizador.yytext());
                    writer.write("Token: " + token.sym + "\tvalor: "+analizador.yytext());
                    writer.newLine();
                }
                else{
                    System.out.println("Lexemas encontrados: " + i);
                    writer.close();
                    return;
                }
                i++;
            }
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * parsear
     * E::archivoFuente: ruta del archivo que contiene el codigo a parsear
     * S::Escritura de un archivo .txt que contiene las tablas de simbolos definidas durante el parseo 
     * R::archivoFuente debe ser una ruta valida
     * O::Analizar la estructura sintactica de un archivoFuente y listar las tablas de simbolos definidas en un archivoRespuesta,
     * ademas de indicar si el archivo puede ser generado haciendo uso de la gramatica definida
     */
    public void parsear(String archivoFuente) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("programa/resultados/tablasimbolos.txt"));
            Reader reader = new BufferedReader(new FileReader(archivoFuente));
            Analizador analizador = new Analizador(reader);
            parser miParser = new parser(analizador);
            Symbol result = miParser.parse();
            if(miParser.getErrores() == false){
                SymbolTableStack tableStack = miParser.getSymbolTableStack();
                if(tableStack.isEmpty()) System.out.println("No hay tablas de simbolos");
                else{
                    for(SymbolTable tabla : tableStack.getStack()){
                            System.out.println("\nTabla: "+tabla.getName()+"  Tipo de retorno: "+tabla.getTipoRetorno());
                            writer.write("\n\nTabla: "+tabla.getName()+"  Tipo de retorno: "+tabla.getTipoRetorno());
                            writer.newLine();
                        for (Map.Entry<String, String> entry : tabla.getSymbols().entrySet()) {
                            String valor = entry.getKey();
                            String tipo = entry.getValue();
                            writer.write("\tvalor: "+valor+"    tipo: "+tipo+"\n");
                            System.out.println("\tvalor: "+valor+"    tipo: "+tipo);
                            
                        }
                    }
                }
                BufferedWriter writer2 = new BufferedWriter(new FileWriter("programa/resultados/AST.txt"));
                writer2.write(result.value.toString());
                writer2.close();
                //generar codigo intermedio
                IntermediateCodeGenerator intermediateCodeGenerator = new IntermediateCodeGenerator();
                String intermediateCode = intermediateCodeGenerator.generateCode((ASTNode) result.value);
                BufferedWriter writer3 = new BufferedWriter(new FileWriter("programa/resultados/codigoIntermedio.txt"));
                writer3.write(intermediateCode);
                writer3.close();
                System.out.println("Codigo intermedio\n\n" + intermediateCode);
            }else{
                System.out.println("El archivo no puede ser generado ya que se han reportado errores");
                writer.write("El archivo no puede ser generado ya que se han reportado errores");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("El archivo no puede ser generado ya que se han reportado errores");
        }
    }
    
}
    