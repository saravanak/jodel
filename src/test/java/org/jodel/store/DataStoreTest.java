/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jodel.store;

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
import org.jodel.domain.SampleBean;
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
        
        System.out.println("\nBefore");
        System.out.println("===================================");
        System.out.println(jSONUtil.getAsJsonString(jsonData));
        
        Map<String, Object> validatedJsonData = dataStore.getObject(SampleBean.class, jsonData);
        
        System.out.println("\nAfter");
        System.out.println("===================================");
        System.out.println(jSONUtil.getAsJsonString(validatedJsonData));
    }

    
}
