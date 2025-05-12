package teoria.de.la.computacion;

import java.util.List;

import teoria.de.la.computacion.executor.Executor;
import teoria.de.la.computacion.lexer.Lexer;
import teoria.de.la.computacion.lexer.Lexer.Token;
import teoria.de.la.computacion.parser.Parser;
import teoria.de.la.computacion.semantic.SemanticAnalyzer;

public class App {
    public static void main(String[] args) {
        try {
            Lexer lexer = new Lexer("C:\\Users\\juang\\OneDrive\\Documentos\\Teoria de la computacion\\app\\src\\main\\resources\\Minilang.txt");
            List<Token> tokens = lexer.tokenize();

            Parser parser = new Parser(tokens);
                parser.parseProgram();  

       
            SemanticAnalyzer analyzer = new SemanticAnalyzer(tokens);
                analyzer.analyze();

            Executor executor = new Executor(tokens);
                executor.execute();

        }
        catch (Exception e) {
            System.out.println("💥 Error: " + e.getMessage());
        }
    }
}
