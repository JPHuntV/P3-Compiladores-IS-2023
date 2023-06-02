package src;
public class main {
    public static void main(String[] args) {
        App app = new App();
        app.analizar("programa/src/test.txt");//lexer
        app.parsear("programa/src/test.txt");//parser
    }  
}