package teoria.de.la.computacion.semantic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import teoria.de.la.computacion.lexer.Lexer.Token;
import teoria.de.la.computacion.lexer.Lexer.TokenType;

public class SemanticAnalyzer {

    private final List<Token> tokens;
    private int current = 0;
    private final Set<String> declaredVariables = new HashSet<>();

    public SemanticAnalyzer(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void analyze() {
        while (!isAtEnd()) {
            checkStatement();
        }
        System.out.println("Análisis semántico exitoso.");
    }

    private void checkStatement() {
        Token token = peek();

        if (token.type == TokenType.LET) {
            advance(); // LET
            Token identifier = consume(TokenType.IDENTIFIER, "Se esperaba un identificador.");
            String varName = identifier.value;

            if (declaredVariables.contains(varName)) {
                error("Variable '" + varName + "' ya fue declarada.");
            }

            declaredVariables.add(varName);
            consume(TokenType.EQUAL, "Se esperaba '='.");
            checkExpression();
            consume(TokenType.SEMICOLON, "Se esperaba ';' al final.");
        } else if (token.type == TokenType.PRINT) {
            advance(); // PRINT
            consume(TokenType.LPAREN, "Se esperaba '(' después de print.");
            Token identifier = consume(TokenType.IDENTIFIER, "Se esperaba un identificador.");
            if (!declaredVariables.contains(identifier.value)) {
                error("Variable '" + identifier.value + "' no fue declarada.");
            }
            consume(TokenType.RPAREN, "Se esperaba ')'.");
            consume(TokenType.SEMICOLON, "Se esperaba ';'.");
        } else {
            error("Instrucción no válida.");
        }
    }

    private void checkExpression() {
        Token t = peek();
        if (t.type == TokenType.IDENTIFIER) {
            if (!declaredVariables.contains(t.value)) {
                error("Variable '" + t.value + "' no fue declarada.");
            }
            advance();
        } else if (t.type == TokenType.NUMBER) {
            advance();
        } else {
            error("Expresión no válida.");
        }

        while (match(TokenType.PLUS, TokenType.MINUS, TokenType.STAR, TokenType.SLASH)) {
            Token next = peek();
            if (next.type == TokenType.IDENTIFIER && !declaredVariables.contains(next.value)) {
                error("Variable '" + next.value + "' no fue declarada.");
            }
            if (next.type != TokenType.IDENTIFIER && next.type != TokenType.NUMBER) {
                error("Operando no válido.");
            }
            advance();
        }
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (peek().type == type) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType expected, String message) {
        if (peek().type == expected) return advance();
        error(message + " Encontrado: " + peek().value);
        return null;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token advance() {
        return tokens.get(current++);
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private void error(String message) {
        throw new RuntimeException("❌ Error semántico: " + message);
    }
}
