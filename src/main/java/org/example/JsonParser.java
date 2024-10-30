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
        while (read != -1) {
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
        skipNewLineOrWhitespace();
        while (c != '}') {
            String key = readString();
            if (c != ':') throw new IllegalArgumentException("Expected ':' after key");
            Object value = parse();
            map.put(key, value);
            if (c != ',' && c != '}') {
                throw new IllegalArgumentException("Expected ',' or '}' after value");
            }
            c = (char) input.read();
            if (c == '\n') c = (char) input.read();
        }
        return map;
    }

    private void skipNewLineOrWhitespace() throws IOException {
        do {
            c = (char) input.read();
        } while (c == '\n' || Character.isWhitespace(c));
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
        if (input.read() == 'u') {
            if (input.read() == 'l') {
                if (input.read() == 'l') {
                    return null;
                }
            }
        }
        throw new IllegalArgumentException("Unexpected character: " + (char) input.read());
    }

}
//    String trim(String json) {
//        String singleLineJson = stringifyJson(json);
//        singleLineJson = removeWrapper(singleLineJson);
//        StringBuilder trimmed = new StringBuilder();
//        boolean inQuotes = false;
//
//        for (int index = 0; index < singleLineJson.length(); index++) {
//            char currentChar = singleLineJson.charAt(index);
//
//            if (currentChar == '"') {
//                inQuotes = !inQuotes;
//                trimmed.append(currentChar);
//            } else if (currentChar != ' ') {
//                trimmed.append(currentChar);
//            } else if (inQuotes) {
//                trimmed.append(currentChar);
//            }
//        }
//        return trimmed.toString();
//    }
//
//    String stringifyJson(String json) {
//        return json.replaceAll("\\s+", " ").trim();
//    }
//
//    String removeWrapper(String singleLineJson) {
//        return singleLineJson.substring(1, singleLineJson.length() - 1).trim();
//    }
//
//    String[] split(String trimmedJson) {
//        return trimmedJson.split(",");
//    }


//    public Object parse(Reader input) {
//
//        return null;
//    }
//
//    public Object parse(String input) {
//        return new StringReader(input);

//        if (!isValidJson(json)) {
//            throw new IllegalArgumentException("Invalid JSON format");
//        }
//
//        String trimmedJson = trim(json);
//        String[] keyValuePairs = split(trimmedJson);
//        Map<String, Object> jsonMap = jsonToMap(keyValuePairs);
//
//        System.out.println(jsonMap.keySet());
//
//
//    private String parseString(String input) {
//        StringBuilder output = new StringBuilder();
//        for (int index = 0; index < input.length(); index++) {
//            char currentChar = input.charAt(index);
//            if (currentChar != '"') { output.append(currentChar); }
//        }
//        return output.toString();
//    }
//
//    boolean isValidJson(String json) {
//        if (hasProperWrapper(json)) {
//            return true;
//        }
//        return false;

//    }
//
//    boolean hasProperWrapper(String json) {
//        int index = 0;
//        return json.charAt(index) == '{' && json.charAt(json.length() - 1) == '}';
//    }


//    Map<String, Object> jsonToMap(String[] keyValuePairs) {
//        Map<String, Object> jsonMap = new HashMap<>();
//        for (String keyValuePair : keyValuePairs) {
//            String[] keyValue = keyValuePair.split(":");
//            String key = keyValue[0].trim();
//            Object value = keyValue[1].trim();
//            jsonMap.put(key, value);
//        }
//
//        return jsonMap;
//    }

