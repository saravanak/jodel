/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jodel.store;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import java.io.IOException;
import java.util.Map;
import org.jodel.util.JSONUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author sathish_ku
 */
public class DataStoreTest {

    private final JSONUtil jSONUtil;
    private final DataStore dataStore;

    public DataStoreTest() {
        dataStore = new DataStore();
        jSONUtil = new JSONUtil();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getObject method, of class DataStore.
     *
     * @throws com.fasterxml.jackson.databind.JsonMappingException
     */
    @Test
    public void testGetObject() throws JsonMappingException, IOException {
        Map<String, String> jsonData = jSONUtil.getJsonStringObject("sample");

        JsonSchema jsonSchema = jSONUtil.getJsonSchema(jSONUtil.getJsonSchemaAsString("sample"));

        JsonSchema propSchema = jsonSchema.asObjectSchema().getProperties().get("name");

        ObjectMapper m = new ObjectMapper();
        Map<String, Object> props = m.convertValue(propSchema, Map.class);

        System.out.println("\nID");
        System.out.println("===================================");
        System.out.println(props.get("id"));

    }

}
