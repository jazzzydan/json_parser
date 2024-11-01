package org.example;

public class Main {
    public static void main(String[] args) {
        String jsonString = """
                {
                    "employee": {
                        "id": 34,
                        "shoe size": 56.6,
                        "name": "Johnson Burger",
                        "skills": ["Flipping", "Cleaning", "Fork master"],
                        "department": "Hamburger engineer"
                    },
                    "isFullTime": true
                }
                """;
        System.out.println(JsonParser.parse(jsonString));
    }
}