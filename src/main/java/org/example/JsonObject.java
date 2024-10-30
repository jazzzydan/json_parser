package org.example;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
public class JsonObject {
    // Generic constructor to initialize a DynamicObject with a LinkedHashMap
    public JsonObject(LinkedHashMap<String, Object> map) {
        for (String key : map.keySet()) {
            try {
                // Dynamically create fields in the object and set values
                Field field = this.getClass().getDeclaredField(key);
                field.setAccessible(true);
                field.set(this, map.get(key));
            } catch (NoSuchFieldException e) {
                // If the field does not exist, add it using reflection
                try {
                    Field newField = this.getClass().getField(key);
                    newField.setAccessible(true);
                    newField.set(this, map.get(key));
                } catch (Exception ex) {
                    System.out.println("Error setting field: " + key + " - " + ex.getMessage());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                output.append(field.getName()).append(" = ").append(field.get(this)).append(",\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return output.toString();
    }
}
