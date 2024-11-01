package org.example;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

import static java.lang.Character.isDigit;
import static java.lang.Character.isWhitespace;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class JsonParser {
    private final Reader input;
    private char c;
    Set<Character> validEndOfValue = new HashSet<>(Set.of('}', ',', ']', '\n', ' ', Character.MAX_VALUE));

    public JsonParser(Reader input) {
        this.input = input;
    }

    /*
    The try-with-resources statement ensures that the StringReader is closed automatically
    after the block is executed, even if an exception occurs. This is important for resource
    management and avoiding memory leaks.
    */
    public static Object parse(String input) {
        try (var reader = new StringReader(input)) {
            return new JsonParser(reader).parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object parse() throws IOException {
        while ((c = (char) input.read()) < Character.MAX_VALUE) {
            if (isWhitespace(c)) continue;
            if (c == '{') return readObject();
            else if (c == '[') return readList();
            else if (c == '"') return readString();
            else if (isDigit(c) || c == '-') return readNumber();
            else if (c == 't' || c == 'f') return readBoolean();
            else if (c == 'n') return readNull();
            else throw new IllegalArgumentException("Unexpected character: " + c);
        }
        throw new IllegalArgumentException("Unexpected end");
    }


    private Map<String, Object> readObject() throws IOException {
        Map<String, Object> map = new LinkedHashMap<>();
        readNextChar();

        while (c != '}') {
            checkUnexpectedEndOfInput("Object");
            skipNewLineOrWhitespace();
            String key = readString();
            if (c != ':') throw new IllegalArgumentException("Expected ':' after key");
            Object value = parse();
            map.put(key, value);
            skipNewLineOrWhitespace();
            if (c == ',') {
                readNextChar();
                continue;
            }
            if (c != '}') {
                throw new IllegalArgumentException("Expected ',' or '}' after value");
            }
        }
        readNextChar();
        return map;
    }

    private void readNextChar() throws IOException {
        c = (char) input.read();
    }

    private String readNextChars(int count) throws IOException {
        var chars = new char[count];
        input.read(chars);
        return new String(chars);
    }

    private void checkUnexpectedEndOfInput(String forObjectType) {
        String message = "Unexpected end of input while reading " + forObjectType;
        if (c == Character.MAX_VALUE) {
            throw new IllegalArgumentException(message);
        }
    }

    private void skipNewLineOrWhitespace() throws IOException {
        while (c == '\n' || isWhitespace(c)) {
            readNextChar();
        }
    }

    private List<Object> readList() throws IOException {
        List<Object> list = new ArrayList<>();
        while (c != ']') {
            Object parsed = parse();
            list.add(parsed);
            if (c == ',') {
                readNextChar();
            } else if (c != ']') {
                throw new IllegalArgumentException("Expected ',' or ']' after value in array");
            }
        }
        readNextChar();
        return list;
    }

    private Number readNumber() throws IOException {
        var numberString = new StringBuilder();
        numberString.append(c);
        boolean isNegativeNumber = c == '-';
        boolean numberHasDecimalPoint = isDecimalPoint(c);
        readNextChar();
        if (isNegativeNumber && isDecimalPoint(c)) {
            throw new IllegalArgumentException("Invalid number format: decimal point after minus sign");
        }
        while (isDigit(c) || isDecimalPoint(c)) {
            if (isDecimalPoint(c)) {
                if (numberHasDecimalPoint) {
                    throw new IllegalArgumentException("Invalid number format: multiple decimal points");
                }
                numberHasDecimalPoint = true;
            }
            numberString.append(c);
            readNextChar();
        }

        if (!validEndOfValue.contains(c)) {
            throw new IllegalArgumentException("Invalid number format: " + c);
        }
        if (numberHasDecimalPoint) {
            return parseDouble(numberString.toString());
        } else {
            return parseInt(numberString.toString());
        }
    }

    private boolean isDecimalPoint(char c) {
        return c == '.';
    }

    private String readString() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        readNextChar();
        while (c != '"') {
            checkUnexpectedEndOfInput("String");
            stringBuilder.append(c);
            readNextChar();
        }
        readNextChar();
        return stringBuilder.toString();
    }

    private Boolean readBoolean() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        do {
            stringBuilder.append(c);
            readNextChar();
        } while (Character.isAlphabetic(c));
        String value = stringBuilder.toString();
        if (value.equals("true")) return true;
        else if (value.equals("false")) return false;
        else throw new IllegalArgumentException("Invalid boolean value: " + value);
    }

    private Objects readNull() throws IOException {
        var ull = readNextChars(3);
        if (ull.equals("ull")) {
            readNextChar();
            if (!validEndOfValue.contains(c)) {
                throw new IllegalArgumentException("Invalid format: n" + ull + c);
            }
            skipNewLineOrWhitespace();
            return null;
        }
        throw new IllegalArgumentException("Unexpected format: n" + ull);
    }
}
