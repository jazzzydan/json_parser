package org.example;

public class Main {
    public static void main(String[] args) {
        String jsonString = "-34.eee";
        System.out.println(JsonParser.parse(jsonString));
    }
}