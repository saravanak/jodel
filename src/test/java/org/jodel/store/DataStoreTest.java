/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jodel.store;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.util.List;
import org.jodel.domain.SampleBean;
import org.jodel.store.mongo.MongoDataStore;
import org.jodel.store.query.Filter;
import org.jodel.store.query.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author sathish_ku
 */
public class DataStoreTest {    
    
    private final DataStore dataStore;
    
    public DataStoreTest() {
        dataStore = new MongoDataStore();
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
    
    @Test
    public void testCreateObject() throws JsonMappingException {
        SampleBean sampleBean = new SampleBean();    
        sampleBean.setName("Saravana Kumar");
        sampleBean.setAge(32);
        sampleBean.setIsMale(true);
        sampleBean.setLongSalary(Long.MAX_VALUE);        
        sampleBean.setSalary(Double.NaN);        
        SampleBean bean = dataStore.create(SampleBean.class, sampleBean);
        System.out.println("bean name " + bean.getName());
        
    }
    
    @Test
    public void testReadObject() throws JsonMappingException {
    	
        SampleBean sampleBean = new SampleBean();
        sampleBean.setAge(32);
        sampleBean.setIsMale(true);
        sampleBean.setLongSalary(Long.MAX_VALUE);        
        sampleBean.setSalary(Double.NaN);
        
        SampleBean bean = dataStore.create(SampleBean.class, sampleBean);
        bean = dataStore.read(SampleBean.class, bean.getName());
        System.out.println("SampleBean  " + bean.getLongSalary());        
    }
    
    @Test
    public void testDeleteObject() throws JsonMappingException {
    	
        SampleBean sampleBean = new SampleBean();
        sampleBean.setAge(32);
        sampleBean.setIsMale(true);
        sampleBean.setLongSalary(Long.MAX_VALUE);        
        sampleBean.setSalary(Double.NaN);
        
        SampleBean bean = dataStore.create(SampleBean.class, sampleBean);
        Assert.assertTrue(dataStore.delete(SampleBean.class, bean.getName()));
    }
    
    @Test
    public void testUpdateObject() throws JsonMappingException {
    	
        SampleBean sampleBean = new SampleBean();
        sampleBean.setAge(32);
        sampleBean.setIsMale(true);
        sampleBean.setLongSalary(Long.MAX_VALUE);        
        sampleBean.setSalary(Double.NaN);
        
        
        SampleBean bean = dataStore.create(SampleBean.class, sampleBean);
        System.out.println(bean.getName());
        
        bean.setIsMale(false);
        dataStore.update(bean);
    }
    
    @Test
    public void testListObjects() throws JsonMappingException {
    	
        SampleBean sampleBean = new SampleBean();
        sampleBean.setAge(32);
        sampleBean.setIsMale(true);
        sampleBean.setLongSalary(Long.MAX_VALUE);        
        sampleBean.setSalary(Double.NaN);
        
        SampleBean bean = dataStore.create(SampleBean.class, sampleBean);
        List<SampleBean> beans = dataStore.list(SampleBean.class);
        System.out.println("SampleBeans  " + beans.size());        
    }
    
    @Test
    public void testListObjectWithQuery() throws JsonMappingException {
    	
        Query query = new Query();
        query.addFilter(new Filter<>("age", Filter.Operator.EQUALS, 32));
        query.addFilter(new Filter<>("name", Filter.Operator.EQUALS, "Sathish Kumar"));
        
        
        dataStore.delete(SampleBean.class,query);
        
        List<SampleBean> beans = dataStore.list(SampleBean.class,query);
        
        System.out.println("Total Count is " + beans.size());
        
        for (SampleBean sampleBean1 : beans) {
            System.out.println(sampleBean1.getAge());
            System.out.println(sampleBean1.getLongSalary());
            System.out.println(sampleBean1.getName());
            System.out.println(sampleBean1.getSalary());
            System.out.println("--------------------------------------------------------------------");
        }
       
    }
    

    
    
}
