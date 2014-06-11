/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jodel.validator;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes.OBJECT;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.jodel.store.stereotype.Id;

/**
 *
 * @author praveen
 */
public class Validator {

    private final ObjectMapper objectMapper;

    public Validator() {
        objectMapper = new ObjectMapper();
    }

    public String getIDField(JsonSchema jsonSchema) {
        String idField = null;
        if (jsonSchema.getType().equals(OBJECT)) {
            Map<String, JsonSchema> properties = jsonSchema.asObjectSchema().getProperties();
            Map<String, Object> propSchema;
            Object idValue;
            for (Map.Entry<String, JsonSchema> property : properties.entrySet()) {
                propSchema = objectMapper.convertValue(property.getValue(), Map.class);
                idValue = propSchema.get("id");
                if (idValue != null) {
                    System.out.println(property.getKey() + " Id value is " + idValue);
                }
            }
        }
        return idField;
    }

    public ValidatedObject getObject(Object dataObj) throws JsonMappingException {
        ValidatedObject validatedObject = null;
        if (dataObj != null) {
            validatedObject = getObject(dataObj.getClass(), objectMapper.convertValue(dataObj, Map.class));
        }
        return validatedObject;
    }

    public ValidatedObject getObject(Class type, Map<String, Object> jsonData) throws JsonMappingException {
        JsonSchema jsonSchema = getJsonSchema(type);
        return new ValidatedObject(jsonSchema, getObject(jsonSchema, jsonData));
    }

    public ValidatedObject getObject(String jsonSchemaAsString, Map<String, Object> jsonData) throws IOException {
        JsonSchema jsonSchema = getJsonSchema(jsonSchemaAsString);
        return new ValidatedObject(jsonSchema, getObject(jsonSchema, jsonData));
    }

    /**
     *
     * Gets a Raw JSON String and converts that as JSON Schema compatible JSON
     *
     * @param jsonSchema - Schema which will be validated against the data
     * @param jsonData - data to be processed
     * @return
     */
    private Map<String, Object> getObject(JsonSchema jsonSchema, Map<String, Object> jsonData) {
        Map<String, Object> validatedData = new HashMap<>();
        if (jsonSchema.getType().equals(OBJECT)) {
            Map<String, JsonSchema> properties = jsonSchema.asObjectSchema().getProperties();
            for (Map.Entry<String, Object> dataProperty : jsonData.entrySet()) {
                String key = dataProperty.getKey();
                Object value = dataProperty.getValue();
                validatedData.put(key, getConvertedObject(properties.get(key), value));
            }
        }
        return validatedData;
    }

    private JsonSchema getJsonSchema(Class type) throws JsonMappingException {
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        objectMapper.acceptJsonFormatVisitor(objectMapper.constructType(type), visitor);
        JsonSchema jsonSchema = visitor.finalSchema();
        addIdFields(type, jsonSchema);
        return jsonSchema;
    }

    private void addIdFields(Class type, JsonSchema jsonSchema) {
        if (jsonSchema.getType().equals(OBJECT)) {
            Map<String, JsonSchema> properties = jsonSchema.asObjectSchema().getProperties();
            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    properties.get(field.getName()).setId("true");
                }
            }
        }
    }

    private JsonSchema getJsonSchema(String jsonSchemaAsString) throws IOException {
        JsonSchema schema = objectMapper.readValue(jsonSchemaAsString, JsonSchema.class);
        return schema;
    }

    private Object getConvertedObject(JsonSchema jsonSchema, Object value) {
        Object convertedValue = value;
        switch (jsonSchema.getType()) {
            case INTEGER:
                if (value instanceof Long) {
                    convertedValue = value;
                } else {
                    convertedValue = Long.parseLong(value.toString());
                }
                break;
            case NUMBER:
                if (value instanceof Double) {
                    convertedValue = value;
                } else {
                    convertedValue = Double.parseDouble(value.toString());
                }
                break;
            case BOOLEAN:
                if (value instanceof Boolean) {
                    convertedValue = value;
                } else {
                    convertedValue = Boolean.parseBoolean(value.toString());
                }
                break;
        }
        return convertedValue;
    }

}
