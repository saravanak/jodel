/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jodel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.jodel.domain.SimpleBean;

/**
 *
 * @author sathish_ku
 */
public class JSONUtil {

    private final ObjectMapper objectMapper;

    public JSONUtil() {
        objectMapper = new ObjectMapper();
    }

    public JsonSchema getSampleJsonSchema() throws JsonMappingException {
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        objectMapper.acceptJsonFormatVisitor(objectMapper.constructType(SimpleBean.class), visitor);
        return visitor.finalSchema();
    }

    public Map<String, String> getJsonStringObject(String name) throws IOException {
        Map<String, String> map;
        map = objectMapper.readValue(getJsonString(name),
                new TypeReference<HashMap<String, String>>() {
                });
        return map;

    }

    private String getJsonString(String name) throws JsonProcessingException, IOException {
        InputStream stream = this.getClass().getResourceAsStream("/org/jodel/store/" + name + ".json");
        StringBuilder builder;
        try (BufferedReader r = new BufferedReader(new InputStreamReader(stream))) {
            builder = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                builder.append(line);
            }
        }
        return builder.toString();
    }

    public String getAsJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
