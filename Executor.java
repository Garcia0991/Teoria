package teoria.de.la.computacion.executor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teoria.de.la.computacion.lexer.Lexer.Token;
import teoria.de.la.computacion.lexer.Lexer.TokenType;

public class Executor {

    private final List<Token> tokens;
    private int current = 0;
    private final Map<String, Integer> variables = new HashMap<>();

    public Executor(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void execute() {
        while (!isAtEnd()) {
            Token token = peek();

            if (token.type == TokenType.LET) {
                advance(); // LET
                String name = consume(TokenType.IDENTIFIER).value;
                consume(TokenType.EQUAL);
                int value = evaluateExpression();
                variables.put(name, value);
                consume(TokenType.SEMICOLON);
            } else if (token.type == TokenType.PRINT) {
                advance(); // PRINT
                consume(TokenType.LPAREN);
                String name = consume(TokenType.IDENTIFIER).value;
                consume(TokenType.RPAREN);
                consume(TokenType.SEMICOLON);

                Integer value = variables.get(name);
                if (value == null) {
                    throw new RuntimeException("Variable '" + name + "' no fue declarada.");
                }
                System.out.println( name + " = " + value);
            } else {
                throw new RuntimeException("Instrucción inválida: " + token.value);
            }
        }
    }

    private int evaluateExpression() {
        Token token = peek();
        int result;

        if (token.type == TokenType.NUMBER) {
            result = Integer.parseInt(token.value);
            advance();
        } else if (token.type == TokenType.IDENTIFIER) {
            if (!variables.containsKey(token.value)) {
                throw new RuntimeException("Variable '" + token.value + "' no fue declarada.");
            }
            result = variables.get(token.value);
            advance();
        } else {
            throw new RuntimeException("Expresión inválida.");
        }

        while (match(TokenType.PLUS, TokenType.MINUS, TokenType.STAR, TokenType.SLASH)) {
            Token operator = previous();
            int rhs;

            Token next = peek();
            if (next.type == TokenType.NUMBER) {
                rhs = Integer.parseInt(next.value);
            } else if (next.type == TokenType.IDENTIFIER && variables.containsKey(next.value)) {
                rhs = variables.get(next.value);
            } else {
                throw new RuntimeException("Operando inválido en expresión.");
            }

            advance();

            switch (operator.type) {
                case PLUS: result += rhs; break;
                case MINUS: result -= rhs; break;
                case STAR: result *= rhs; break;
                case SLASH: result /= rhs; break;
            }
        }

        return result;
    }

    private Token consume(TokenType expected) {
        Token token = advance();
        if (token.type != expected) {
            throw new RuntimeException("Se esperaba " + expected + " pero se encontró: " + token.value);
        }
        return token;
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

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private Token advance() {
        return tokens.get(current++);
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }
}
