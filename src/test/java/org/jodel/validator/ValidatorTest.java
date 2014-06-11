package org.jodel.validator;

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
 * @author praveen
 */
public class ValidatorTest {

    private final Validator validator;

    private final JSONUtil jSONUtil;

    public ValidatorTest() {
        validator = new Validator();
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

    @Test
    public void testGetObjectFromPOJO() throws IOException {
        System.out.println("\nPOJO Based");
        Map<String, Object> stringDataObject = jSONUtil.getJsonStringObject("sample");
        ValidatedObject validatedObject = validator.getObject(SampleBean.class, stringDataObject);
        System.out.println("Schema " + jSONUtil.getAsJsonString(validatedObject.getJsonSchema()));
        System.out.println("stringDataObject " + jSONUtil.getAsJsonString(stringDataObject));
        System.out.println("validatedObject " + jSONUtil.getAsJsonString(validatedObject.getDataObject()));
    }

    @Test
    public void testGetObjectFromString() throws IOException {
        System.out.println("\nString Based");
        Map<String, Object> stringDataObject = jSONUtil.getJsonStringObject("sample");
        ValidatedObject validatedObject = validator.getObject(jSONUtil.getJsonSchemaAsString("sample"), stringDataObject);
        System.out.println("Schema " + jSONUtil.getAsJsonString(validatedObject.getJsonSchema()));
        System.out.println("stringDataObject " + jSONUtil.getAsJsonString(stringDataObject));
        System.out.println("validatedObject " + jSONUtil.getAsJsonString(validatedObject.getDataObject()));
    }

}
