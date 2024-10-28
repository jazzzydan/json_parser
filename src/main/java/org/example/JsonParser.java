package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonParser {

    private String json;
    private int index;

    public JsonParser(String json) {
        this.json = json;
        this.index = 0;
    }

    public boolean validate() {
        json.trim();
        if (json.charAt(index) == '{' && json.charAt(json.length() - 1) == '}') {
            return true;
        }
        return false;
    }

    public String trim() {
        String singleLineJson = json.replaceAll("\\s+", " ").trim();
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

    public void parse() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("Test");
        objects.add("Test2");

        Map<String, Object> map = new HashMap<>();
        map.put("Test", true);
        map.put("Test1", 54);
        map.put("Test2", null);
        map.put("Test3", "tere");
        map.put("objectList", objects);

        Map<String, Object> rebane = new HashMap<>();
        rebane.put("name", "Rein");



//        System.out.println(objects);
//        System.out.println(map);
        System.out.println(rebane);


    }

    public static void main(String[] args) {
        String jsonString = "{\n" +
                "  \"key 1\": \"value with spaces\", \n" +
                "  \"key 2\": true,\n" +
                "  \"key 3\": null\n" +
                "}";

        JsonParser parser = new JsonParser(jsonString);
        String trimmedJson = parser.trim();
        System.out.println(trimmedJson);
    }
}
