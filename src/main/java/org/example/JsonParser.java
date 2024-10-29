package org.example;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class JsonParser {

    public Object parse(String input) {
        if (input == null) return null;
        if (input.matches("-?[0-9]+")) return Integer.valueOf(input);
        if (input.matches("-?\\d+(\\.\\d+)?")) return Double.valueOf(input);
        if (input.matches("true|false")) return Boolean.valueOf(input);



        return null;
    }

    public Object parse(Reader input) {

        return null;
    }


}
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
//
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
//
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

