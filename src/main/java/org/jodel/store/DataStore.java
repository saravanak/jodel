/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jodel.store;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.*;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sathish_ku
 */
public class DataStore {

    public Map<String, Object> getObject(Class type, Map<String, String> jsonData) throws JsonMappingException {
        return getObject(getJsonSchema(type),jsonData);
    }
    
    public Map<String, Object> getObject(String jsonSchemaAsString, Map<String, String> jsonData) throws IOException {
        return getObject(getJsonSchema(jsonSchemaAsString),jsonData);
    }

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
                validatedData.put(key, getConvertedObject(properties.get(key), value));
            }
        }
        return validatedData;
    }

    private JsonSchema getJsonSchema(Class type) throws JsonMappingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        objectMapper.acceptJsonFormatVisitor(objectMapper.constructType(type), visitor);
        return visitor.finalSchema();
    }
    
    private JsonSchema getJsonSchema(String jsonSchemaAsString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchema schema = objectMapper.readValue(jsonSchemaAsString, JsonSchema.class);
        return schema;
    }

    private Object getConvertedObject(JsonSchema jsonSchema, String value) {
        Object convertedValue = value;
        switch (jsonSchema.getType()) {
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
