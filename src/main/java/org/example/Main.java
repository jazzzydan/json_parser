package org.example;

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
    }
}