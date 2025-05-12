package teoria.de.la.computacion.lexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lexer {

    public enum TokenType {
        LET, IDENTIFIER, EQUAL, NUMBER, SEMICOLON,
        PRINT, PLUS, MINUS, STAR, SLASH, LPAREN, RPAREN,
        EOF, UNKNOWN
    }

    public static class Token {
        public TokenType type;
        public String value;

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return type + " (" + value + ")";
        }
    }

    private BufferedReader reader;
    private int currentChar;

    public Lexer(String filename) throws IOException {
        reader = new BufferedReader(new FileReader(filename));
        currentChar = reader.read();
    }

    private void consume() throws IOException {
        currentChar = reader.read();
    }

    public List<Token> tokenize() throws IOException {
        List<Token> tokens = new ArrayList<>();

        while (currentChar != -1) {
            if (Character.isWhitespace(currentChar)) {
                consume();
            } else if (Character.isLetter(currentChar)) {
                StringBuilder sb = new StringBuilder();
                while (Character.isLetterOrDigit(currentChar)) {
                    sb.append((char) currentChar);
                    consume();
                }
                String word = sb.toString();
                switch (word) {
                    case "let": tokens.add(new Token(TokenType.LET, word)); break;
                    case "print": tokens.add(new Token(TokenType.PRINT, word)); break;
                    default: tokens.add(new Token(TokenType.IDENTIFIER, word));
                }
            } else if (Character.isDigit(currentChar)) {
                StringBuilder sb = new StringBuilder();
                while (Character.isDigit(currentChar)) {
                    sb.append((char) currentChar);
                    consume();
                }
                tokens.add(new Token(TokenType.NUMBER, sb.toString()));
            } else {
                switch (currentChar) {
                    case '=': tokens.add(new Token(TokenType.EQUAL, "=")); break;
                    case ';': tokens.add(new Token(TokenType.SEMICOLON, ";")); break;
                    case '+': tokens.add(new Token(TokenType.PLUS, "+")); break;
                    case '-': tokens.add(new Token(TokenType.MINUS, "-")); break;
                    case '*': tokens.add(new Token(TokenType.STAR, "*")); break;
                    case '/': tokens.add(new Token(TokenType.SLASH, "/")); break;
                    case '(': tokens.add(new Token(TokenType.LPAREN, "(")); break;
                    case ')': tokens.add(new Token(TokenType.RPAREN, ")")); break;
                    default: tokens.add(new Token(TokenType.UNKNOWN, String.valueOf((char) currentChar))); break;
                }
                consume();
            }
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}
