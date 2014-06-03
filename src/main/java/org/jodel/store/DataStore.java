/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jodel.store;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.*;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sathish_ku
 */
public class DataStore {

    /**
     *
     * Gets a Raw JSON String and converts that as JSON Schema compatible JSON
     * 
     * @param jsonSchema - Schema which will be validated against the data
     * @param jsonData - data to be processed
     * @return
     */
    public Map<String, Object> getObject(JsonSchema jsonSchema, Map<String, String> jsonData) {
        Map<String, Object> validatedData = new HashMap<>();
        if (jsonSchema.getType().equals(OBJECT)) {
            Map<String, JsonSchema> properties = jsonSchema.asObjectSchema().getProperties();
            for (Map.Entry<String, String> dataProperty : jsonData.entrySet()) {
                String key = dataProperty.getKey();
                String value = dataProperty.getValue();
                validatedData.put(key, getConvertedObject(properties.get(key).getType(), value));
            }
        }
        return validatedData;
    }

    private Object getConvertedObject(JsonFormatTypes formatTypes, String value) {
        Object convertedValue = value;
        switch (formatTypes) {
            case INTEGER:
                convertedValue = Integer.parseInt(value);
                break;
            case NUMBER:
                convertedValue = Double.parseDouble(value);
                break;
            case BOOLEAN:
                convertedValue = Boolean.parseBoolean(value);
                break;
        }
        return convertedValue;
    }

}