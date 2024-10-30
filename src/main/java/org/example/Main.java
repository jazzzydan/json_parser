package org.example;

import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) {
        String jsonString = """
                {
                  "key 1": "value with spaces",
                  "key 2": true,
                  "key 3": null,
                  "key 4": 42,
                  "key 5": {
                    "nested Key 1": "nestedValue"
                  }
                }""";

        JsonParser.parse(jsonString);

        // Sample LinkedHashMap with unknown keys
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("name", "Alice");
        map.put("age", 30);
        map.put("city", "Tallinn");
        // Create a DynamicObject from the map
        JsonObject obj = new JsonObject(map);
        // Display the object
        System.out.println(obj);
    }
}