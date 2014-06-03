/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jodel.store;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
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
        
        System.out.println("\nAfter POJO");
        System.out.println("===================================");
        System.out.println(jSONUtil.getAsJsonString(validatedJsonData));
        
        Map<String, Object> validatedJsonData2 = dataStore.getObject(jSONUtil.getJsonSchemaAsString("sample"), jsonData);
        
        System.out.println("\nAfter String");
        System.out.println("===================================");
        System.out.println(jSONUtil.getAsJsonString(validatedJsonData2));
    }

    
}
