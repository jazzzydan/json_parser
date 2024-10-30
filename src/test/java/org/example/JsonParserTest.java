package org.example;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    //TODO: excemption testing

    @Test
    void parseString() {
        assertEquals("value", JsonParser.parse("\"value\""));
    }

    @Test
    void parseDifficultString() {
        assertEquals("value is Difficult String nr.1 with symbol &"
                , JsonParser.parse("\"value is Difficult String nr.1 with symbol &\""));
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

//    @Test
//    void validate() {
//        assertTrue(parser.isValidJson("{\n\"key\": \"string\"\n}"));
//        assertFalse(parser.isValidJson("{\n\"key\": \"string\"\n"));
//    }
//
//    @Test
//    void trim() {
//        // language=json
//        String json = """
//                {
//                  "key 1": "value with spaces",
//                  "key 2": true,
//                  "key 3": null
//                }""";
//        assertEquals("\"key 1\":\"value with spaces\",\"key 2\":true,\"key 3\":null", parser.trim(json));
//    }
//
//    @Test
//    void split() {
//        String json = "\"key 1\":\"value with spaces\",\"key 2\":true,\"key 3\":null";
//        String[] expected = {"\"key 1\":\"value with spaces\"", "\"key 2\":true", "\"key 3\":null"};
//        assertArrayEquals(expected, parser.split(json));

//    }
//    @Test
//    void jsonToMap() {
//        String[] keyValuePairs = {
//                "\"key 1\":\"value with spaces\"",
//                "\"key 2\":true",
//                "\"key 3\":null"
//        };
//        Map<String, Object> expectedMap = Map.of(
//                "\"key 1\"", "value with spaces",
//                "\"key 2\"", "true",
//                "\"key 3\"", "null"
//        );
//
//        Map<String, Object> result = parser.jsonToMap(keyValuePairs);
//        assertEquals(expectedMap, result);

//    }
//    @Test
//    void stringValue() {
//        String[] keyValuePairs = {"\"key\":\"string\""};
//        assertEquals("string", parser.jsonToMap(keyValuePairs).get("\"key\""));

//    }

}