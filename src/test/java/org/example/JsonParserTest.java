package org.example;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    JsonParser parser = new JsonParser();

    @Test
    void parseString() {
        assertEquals("value", parser.parse("\"value\""));
    }

    @Test
    void parseNull() {
        assertNull(parser.parse("null"));
    }

    @Test
    void parseInteger() {
        assertEquals(-101, parser.parse("-101"));
    }

    @Test
    void parseDouble() {
        assertEquals(-333.1457, parser.parse("-333.1457"));
    }

    @Test
    void parseTrueBoolean() {
        assertEquals(true, parser.parse("true"));
    }

    @Test
    void parseFalseBoolean() {
        assertEquals(false, parser.parse("false"));
    }

    @Test
    void parseList() {
        assertEquals(List.of(1, 2, 3), parser.parse("[1, 2, 3]"));
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