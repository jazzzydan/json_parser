package org.example;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    @Test
    void parseString() {
        assertEquals("value", JsonParser.parse("\"value\""));
    }

    @Test
    void parseDifficultString() {
        assertEquals("value is complex String nr.1 with symbol &",
                JsonParser.parse("\"value is complex String nr.1 with symbol &\""));
    }

    @Test
    void parseNull() {
        assertNull(JsonParser.parse("null"));
    }

    @Test
    void parseInteger() {
        assertEquals(-101, JsonParser.parse("-101"));
    }

    @Test
    void parseDouble() {
        assertEquals(-333.1457, JsonParser.parse("-333.1457"));
    }

    @Test
    void parseTrueBoolean() {
        assertEquals(true, JsonParser.parse("true"));
    }

    @Test
    void parseFalseBoolean() {
        assertEquals(false, JsonParser.parse("false"));
    }

    @Test
    void parseList() {
        assertEquals(List.of(1, 2, 3), JsonParser.parse("[1, 2, 3]"));
        assertEquals(List.of(1.17, 2.59, -66.77, 100.99), JsonParser.parse("[1.17, 2.59, -66.77, 100.99]"));
        assertEquals(List.of(false, true, false), JsonParser.parse("[false, true, false]"));
        assertEquals(List.of("string with spaces", "string with special characters @#!", "another string")
                , JsonParser.parse("[\"string with spaces\", \"string with special characters @#!\", \"another string\"]"));
    }

    @Test
    void parseObject() {
        // language=json
        String json = """
                {
                  "key 1": "value with spaces",
                  "key 2": true,
                  "key 3": 999,
                  "key 4": -123.45,
                  "key 5": null
                }""";
        Map<String, Object> expectedMap = new LinkedHashMap<>();
        expectedMap.put("key 1", "value with spaces");
        expectedMap.put("key 2", true);
        expectedMap.put("key 3", 999);
        expectedMap.put("key 4", -123.45);
        expectedMap.put("key 5", null);

        Object result = JsonParser.parse(json);
        assertEquals(expectedMap, result);
    }

    @Test
    void parseMultilayerObject() {
        Map<String, Object> expectedMap = new LinkedHashMap<>();

        Map<String, Object> employee = new LinkedHashMap<>();
        employee.put("id", 34);
        employee.put("shoe size", 56.6);
        employee.put("name", "Johnson Burger");
        employee.put("skills", List.of("Flipping", "Cleaning", "Fork master"));
        employee.put("department", "Hamburger engineer");
        expectedMap.put("employee", employee);
        expectedMap.put("isFullTime", true);
        // language=json
        assertEquals(expectedMap, JsonParser.parse("""
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
                """));
    }

    @Test
    void unfinishedJson() {
        assertEquals("Unexpected end",
        assertThrows(IllegalArgumentException.class, () -> JsonParser.parse("   ")).getMessage());
        assertThrows(IllegalArgumentException.class, () -> JsonParser.parse("{  "));
    }

    @Test
    void correctObjectInput() {
        assertEquals("Expected ':' after key",
                assertThrows(IllegalArgumentException.class, () ->
                        JsonParser.parse("{\"key 1\" \"value with spaces\"}")).getMessage());
        assertEquals("Expected ',' or '}' after value",
        assertThrows(IllegalArgumentException.class, () ->
                        JsonParser.parse("{\"key 1\": \"value with spaces\"")).getMessage());
    }

    @Test
    void correctListInput() {
        assertEquals("Expected ',' or ']' after value in array",
                assertThrows(IllegalArgumentException.class, () ->
                        JsonParser.parse("[1.17, 2.59, -66.77 100.99]")).getMessage());
    }

    @Test
    void correctNumberInput() {
        assertEquals("Invalid number format: multiple decimal points",
                assertThrows(IllegalArgumentException.class, () ->
                        JsonParser.parse("33.31.457")).getMessage());

        // TODO: explain to others
        assertEquals("For input string: \"-\"",
                assertThrows(IllegalArgumentException.class, () ->
                        JsonParser.parse("-")).getMessage());
//        assertEquals("Expected ',' or '}' after value",
//                assertThrows(IllegalArgumentException.class, () ->
//                        JsonParser.parse("-34.eeee")).getMessage());
    }

    @Test
    void correctStringInput() {
        assertThrows(IllegalArgumentException.class, () ->
                        JsonParser.parse("\"value"),
                "Unexpected end of input while reading String");
    }

    @Test
    void correctBooleanInput() {
        assertThrows(IllegalArgumentException.class, () ->
                        JsonParser.parse("truu"),
                "Invalid boolean value: truu");
    }

    @Test
    void correctNullInput() {
        assertThrows(IllegalArgumentException.class, () ->
                        JsonParser.parse("nuul"),
                "Unexpected character: u");
    }

    @Test
    void unexpectedCharacterDuringParse() {
        assertThrows(IllegalArgumentException.class, () ->
                        JsonParser.parse("z"),
                "Unexpected character: z");
    }
}