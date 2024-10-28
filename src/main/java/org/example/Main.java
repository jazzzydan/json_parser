package org.example;

public class Main {
    public static void main(String[] args) {
        String jsonString = "{\n" +
                "  \"key 1\": \"value with spaces\",\n" +
                "  \"key 2\": true,\n" +
                "  \"key 3\": null,\n" +
                "  \"key 4\": 42,\n" +
                "  \"key 5\": {\n" +
                "    \"nested Key 1\": \"nestedValue\"\n" +
                "  }\n" +
                "}";

        JsonParser parser = new JsonParser();
        parser.parse(jsonString);

    }
}