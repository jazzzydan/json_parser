package org.example;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

public class JsonParser {
    private Reader input;
    private char c;

    public JsonParser(Reader input) {
        this.input = input;
    }

    public static Object parse(String input) {
        try {
            return new JsonParser(new StringReader(input)).parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Object parse() throws IOException {
        var read = input.read();
        c = (char) read;
        while (c != -1) {
            while (Character.isWhitespace(c)) {
                c = (char) input.read();
            }
            if (c == '{') return readObject();
            else if (c == '[') return readList();
            else if (c == '"') return readString();
            else if (Character.isDigit(c) || c == '-') return readNumber();
            else if (c == 't' || c == 'f') return readBoolean();
            else if (c == 'n') return readNull();
            else throw new IllegalArgumentException("Unexpected character: " + (char) c);
        }
        throw new IllegalArgumentException("Unexpected end");
    }


    private Map<String, Object> readObject() throws IOException {
        Map<String, Object> map = new LinkedHashMap<>();
        c = (char) input.read();

        while (c != '}') {
            skipNewLineOrWhitespace();
            String key = readString();
            if (c != ':') throw new IllegalArgumentException("Expected ':' after key");
            Object value = parse();
            map.put(key, value);
            skipNewLineOrWhitespace();
            if (c == ',') {
                c = (char) input.read();
                continue;
            }
            if (c != '}') {
                throw new IllegalArgumentException("Expected ',' or '}' after value");
            }
        }
        c = (char) input.read();
        return map;
    }

    private void skipNewLineOrWhitespace() throws IOException {
        while (c == '\n' || Character.isWhitespace(c)) {
            c = (char) input.read();
        }
    }

    private List<Object> readList() throws IOException {
        List<Object> list = new ArrayList<>();
        while (c != ']') {
            Object parsed = parse();
            list.add(parsed);
            if (c == ',') {
                c = (char) input.read();
            }
        }
        c = (char) input.read();
        return list;
    }

    private Number readNumber() throws IOException {
        StringBuilder numberString = new StringBuilder();
        numberString.append(c);
        boolean hasDecimalPoint = (c == '.');
        c = (char) input.read();
        while (Character.isDigit(c) || c == '.') {
            if (c == '.') {
                if (hasDecimalPoint) {
                    throw new IOException("Invalid number format: multiple decimal points");
                }
                hasDecimalPoint = true;
            }
            numberString.append(c);
            c = (char) input.read();
        }
        if (hasDecimalPoint) {
            return Double.valueOf(numberString.toString());
        } else {
            return Integer.valueOf(numberString.toString());
        }
    }

    private String readString() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        c = (char) input.read();
        while (c != '"') {
            stringBuilder.append(c);
            c = (char) input.read();
        }
        c = (char) input.read();
        return stringBuilder.toString();
    }

    private Boolean readBoolean() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        do {
            stringBuilder.append(c);
            c = (char) input.read();
        } while (Character.isAlphabetic(c));
        String value = stringBuilder.toString();
        if (value.equals("true")) return true;
        else if (value.equals("false")) return false;
        else throw new IllegalArgumentException("Invalid boolean value: " + value);
    }

    private Objects readNull() throws IOException {
        c = (char) input.read();
        if (c == 'u') {
            c = (char) input.read();
            if (c == 'l') {
                c = (char) input.read();
                if (c == 'l') {
                    c = (char) input.read();
                    skipNewLineOrWhitespace();
                    return null;
                }
            }
        }
        throw new IllegalArgumentException("Unexpected character: " + (char) input.read());
    }
}


