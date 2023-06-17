package src;
public class main {
    public static void main(String[] args) {
        App app = new App();
       //app.analizar("programa/src/ejemplo.txt");//lexer
       //app.parsear("programa/src/ejemplo.txt");//parser

        app.analizar("programa/src/testCode.txt");//lexer
        app.parsear("programa/src/testCode.txt");//parser
    }  
}