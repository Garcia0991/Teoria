package teoria.de.la.computacion.parser;

import java.util.List;

import teoria.de.la.computacion.lexer.Lexer.Token;
import teoria.de.la.computacion.lexer.Lexer.TokenType;

public class Parser {

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token advance() {
        return tokens.get(current++);
    }

    private boolean match(TokenType expected) {
        if (peek().type == expected) {
            advance();
            return true;
        }
        return false;
    }

    public void parseProgram() {
        while (peek().type != TokenType.EOF) {
            parseStatement();
        }
        System.out.println("Sintaxis válida.");
    }

    private void parseStatement() {
        if (match(TokenType.LET)) {
            if (!match(TokenType.IDENTIFIER)) error("Se esperaba un identificador.");
            if (!match(TokenType.EQUAL)) error("Se esperaba '='.");
            parseExpression();
            if (!match(TokenType.SEMICOLON)) error("Se esperaba ';'.");
        } else if (match(TokenType.PRINT)) {
            if (!match(TokenType.LPAREN)) error("Se esperaba '(' después de print.");
            if (!match(TokenType.IDENTIFIER)) error("Se esperaba un identificador.");
            if (!match(TokenType.RPAREN)) error("Se esperaba ')'.");
            if (!match(TokenType.SEMICOLON)) error("Se esperaba ';'.");
        } else {
            error("Instrucción no válida.");
        }
    }

    private void parseExpression() {
        if (!match(TokenType.IDENTIFIER) && !match(TokenType.NUMBER)) {
            error("Se esperaba una expresión.");
        }

        while (match(TokenType.PLUS) || match(TokenType.MINUS) ||
               match(TokenType.STAR) || match(TokenType.SLASH)) {
            if (!match(TokenType.IDENTIFIER) && !match(TokenType.NUMBER)) {
                error("Se esperaba una expresión después del operador.");
            }
        }
    }

    private void error(String message) {
        throw new RuntimeException("Error de sintaxis: " + message + " En token: " + peek());
    }
}
