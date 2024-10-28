package org.example;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    JsonParser parser = new JsonParser();
    @Test
    void validateTest() {
        assertTrue(parser.isValidJson("{\n\"key\": \"string\"\n}"));
        assertFalse(parser.isValidJson("{\n\"key\": \"string\"\n"));
    }

    @Test
    void trimTest() {
        String json = "{\n" +
                "  \"key 1\": \"value with spaces\", \n" +
                "  \"key 2\": true,\n" +
                "  \"key 3\": null\n" +
                "}";
        assertEquals("\"key 1\":\"value with spaces\",\"key 2\":true,\"key 3\":null", parser.trim(json));
    }

    @Test
    void splitTest() {
        String json = "\"key 1\":\"value with spaces\",\"key 2\":true,\"key 3\":null";
        String[] expected = {"\"key 1\":\"value with spaces\"", "\"key 2\":true", "\"key 3\":null"};
        assertArrayEquals(expected, parser.split(json));
    }

//    @Test
//    void jsonToMapTest() {
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

    @Test
    void stringValueTest() {
        String[] keyValuePairs = {"\"key\":\"string\""};
        assertEquals("string", parser.jsonToMap(keyValuePairs).get("key"));
    }

}