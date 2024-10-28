package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    @Test
    void validateTest() {
        JsonParser parser = new JsonParser("{\n\"key\": \"string\"\n}");
        assertTrue(parser.validate());
        parser = new JsonParser("{\n\"key\": \"string\"\n");
        assertFalse(parser.validate());

        parser.parse();

    }

    @Test
    void trimTest() {
        JsonParser parser = new JsonParser("{\n" +
                "  \"key 1\": \"value with spaces\", \n" +
                "  \"key 2\": true,\n" +
                "  \"key 3\": null\n" +
                "}");

        assertEquals("{\"key 1\":\"value with spaces\",\"key 2\":true,\"key 3\":null}", parser.trim());
    }

    @Test
    void splitTest() {
        JsonParser parser = new JsonParser("{\"key 1\":\"value with spaces\",\"key 2\":true,\"key 3\":null}");
        assertEquals(ArrayList<>);
    }

//    @Test
//    void stringValueTest() {
//        JsonParser jsonParser = new JsonParser("{\n\"key\": \"string\"\n}");
//        assertEquals("string", jsonObject.getValue());
//    }

}