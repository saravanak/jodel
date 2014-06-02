/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jodel.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import java.util.Map;

/**
 *
 * @author sathish_ku
 */
public class DataStore {

    private ObjectMapper objectMapper;

    public DataStore() {
        objectMapper = new ObjectMapper();
    }

    /**
     *
     * @param jsonSchema - Schema which will be validated against the data
     * @param jsonData - data to be processed
     * @return
     */
    public Map<String, Object> getObject(JsonSchema jsonSchema, Map<String, String> jsonData) {
        return null;
    }

    private JsonSchema getSampleJsonSchema() throws JsonMappingException {
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        objectMapper.acceptJsonFormatVisitor(objectMapper.constructType(SimpleBean.class), visitor);
        return visitor.finalSchema();
    }

    private String getAsJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static void main(String[] a) throws JsonMappingException, JsonProcessingException {
        DataStore dataStore = new DataStore();
        
        // display sample schema
        System.out.println(dataStore.getAsJsonString(dataStore.getSampleJsonSchema()));
    }

}
