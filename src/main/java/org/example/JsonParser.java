package org.example;

import java.util.HashMap;
import java.util.Map;

public class JsonParser {

    boolean isValidJson(String json) {
        if (hasProperWrapper(json)) {
            return true;
        }
        return false;
    }

    boolean hasProperWrapper(String json) {
        int index = 0;
        return json.charAt(index) == '{' && json.charAt(json.length() - 1) == '}';
    }

    String trim(String json) {
        String singleLineJson = stringifyJson(json);
        singleLineJson = removeWrapper(singleLineJson);
        StringBuilder trimmed = new StringBuilder();
        boolean inQuotes = false;

        for (int index = 0; index < singleLineJson.length(); index++) {
            char currentChar = singleLineJson.charAt(index);

            if (currentChar == '"') {
                inQuotes = !inQuotes;
                trimmed.append(currentChar);
            } else if (currentChar != ' ') {
                trimmed.append(currentChar);
            } else if (inQuotes) {
                trimmed.append(currentChar);
            }
        }
        return trimmed.toString();
    }

    String stringifyJson(String json) {
        return json.replaceAll("\\s+", " ").trim();
    }

    String removeWrapper(String singleLineJson) {
        return singleLineJson.substring(1, singleLineJson.length() - 1).trim();
    }

    String[] split(String trimmedJson) {
        return trimmedJson.split(",");
    }

    Map<String, Object> jsonToMap(String[] keyValuePairs) {
        Map<String, Object> jsonMap = new HashMap<>();
        for (String keyValuePair : keyValuePairs) {
            String[] keyValue = keyValuePair.split(":");
            String key = keyValue[0].trim();
            Object value = keyValue[1].trim();
            jsonMap.put(key, value);
        }

        return jsonMap;
    }

    public void parse(String json) {

        if (!isValidJson(json)) {
            throw new IllegalArgumentException("Invalid JSON format");
        }

        String trimmedJson = trim(json);
        String[] keyValuePairs = split(trimmedJson);
        Map<String, Object> jsonMap = jsonToMap(keyValuePairs);

        System.out.println(jsonMap.keySet());

//        ArrayList<Object> objects = new ArrayList<>();
//        objects.add("Test");
//        objects.add("Test2");
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("Test", true);
//        map.put("Test1", 54);
//        map.put("Test2", null);
//        map.put("Test3", "tere");
//        map.put("objectList", objects);
//
//        Map<String, Object> rebane = new HashMap<>();
//        rebane.put("name", "Rein");

//        System.out.println(objects);
//        System.out.println(map);
//        System.out.println(rebane);


    }
}
