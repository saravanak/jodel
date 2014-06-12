/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jodel.store;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import java.util.List;
import java.util.Map;
import org.jodel.validator.ValidatedObject;
import org.jodel.validator.Validator;

/**
 *
 * @author sathish_ku
 */
public abstract class DataStore {

    protected final Validator validator;

    public DataStore() {
        validator = new Validator();
    }

    /**
     * Creates a new object
     *
     * @param <T> Type of the Object
     * @param clazz Class of the Object to be created
     * @param object Object to be created
     * @return created object
     */
    public <T> T create(Class<T> clazz, Object object) {
        ValidatedObject validatedObject = validator.getObject(object);
        return validator.getObjectOfType(clazz, create(validatedObject.getJsonSchema(), validatedObject.getDataObject()));
    }

    /**
     * reads an Object with given name
     *
     * @param <T> Type of the Object
     * @param clazz Class of the Object to be read
     * @param name name of the Object
     * @return object with given name
     */
    public <T> T read(Class<T> clazz, String name) {
        return validator.getObjectOfType(clazz, read(validator.getJsonSchema(clazz), name));
    }

    /**
     * reads an Object with given name
     *
     * @param <T> Type of the Object
     * @param clazz Class of the Object to be read
     * @return object with given name
     */
    public <T> List<T> list(Class<T> clazz) {
        return null;
    }

    /**
     * reads an Object with given name
     *
     * @param <T> Type of the Object
     * @param clazz Class of the Object to be read
     * @param pageNumber
     * @param pageSize
     * @return object with given name
     */
    public <T> List<T> list(Class<T> clazz,int pageNumber,int pageSize) {
        return null;
    }

    /**
     * @param dataObject
     * @return
     * @throws JsonMappingException
     */
    public boolean update(Object dataObject) throws JsonMappingException {
        ValidatedObject validatedObject = validator.getObject(dataObject);
        return update(validatedObject.getJsonSchema(), validatedObject.getDataObject());
    }

    public boolean delete(Class clazz, String name) {
        return delete(validator.getJsonSchema(clazz), name);
    }

    protected String getIdField(JsonSchema jsonSchema) {
        String idField = null;
        String idValue;

        Map<String, JsonSchema> properties = jsonSchema.asObjectSchema().getProperties();
        for (Map.Entry<String, JsonSchema> property : properties.entrySet()) {
            idValue = property.getValue().getId();
            if (idValue != null && idValue.trim().equals("true")) {
                idField = property.getKey();
                break;
            }
        }
        return idField;
    }

    /**
     * ALL ABSTRACT METHODS WILL COME HERE
     */
    /**
     *
     * @param validatedDataObject
     * @param jsonSchema
     * @return
     */
    public abstract Map<String, Object> create(
            JsonSchema jsonSchema,
            Map<String, Object> validatedDataObject);

    /**
     *
     * @param jsonSchema
     * @param idValue
     * @return
     */
    public abstract Map<String, Object> read(
            JsonSchema jsonSchema,
            String idValue);

    /**
     *
     * @param jsonSchema
     * @param idValue
     * @return
     */
    public abstract boolean delete(JsonSchema jsonSchema,
            String idValue);

    /**
     *
     * @param jsonSchema
     * @param validatedDataObject
     * @return
     */
    public abstract boolean update(JsonSchema jsonSchema,
            Map<String, Object> validatedDataObject);

}
