/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jodel.store;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jodel.store.query.Query;
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
        List<Map<String, Object>> listOfMap = list(validator.getJsonSchema(clazz));
        if (listOfMap != null) {
            List<T> listOfObject = new ArrayList<>(listOfMap.size());
            for (Map<String, Object> map : listOfMap) {
                listOfObject.add(validator.getObjectOfType(clazz, map));
            }
            return listOfObject;
        }
        return null;
    }

    /**
     * reads an Object with given name
     *
     * @param <T> Type of the Object
     * @param clazz Class of the Object to be read
     * @param query
     * @return object with given name
     */
    public <T> List<T> list(Class<T> clazz, Query query) {
        List<Map<String, Object>> listOfMap = list(validator.getJsonSchema(clazz), query);
        if (listOfMap != null) {
            List<T> listOfObject = new ArrayList<>(listOfMap.size());
            for (Map<String, Object> map : listOfMap) {
                listOfObject.add(validator.getObjectOfType(clazz, map));
            }
            return listOfObject;
        }
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
    public <T> List<T> list(Class<T> clazz, int pageNumber, int pageSize) {
        return null;
    }

    /**
     * @param dataObject
     * @return
     */
    public boolean update(Object dataObject) {
        ValidatedObject validatedObject = validator.getObject(dataObject);
        return update(validatedObject.getJsonSchema(), validatedObject.getDataObject());
    }

    /**
     * deletes the Object with given name
     *
     * @param clazz - Class of the Object
     * @param name - name of the Object
     * @return successFlag
     */
    public boolean delete(Class clazz, String name) {
        return delete(validator.getJsonSchema(clazz), name);
    }

    /**
     * deletes the Objects with given query
     *
     * @param clazz - Class of the Object
     * @param query - query for the Object
     * @return successFlag
     */
    public boolean delete(Class clazz, Query query) {
        return delete(validator.getJsonSchema(clazz), query);
    }

    /**
     * gets Id field of a schema
     *
     * @param jsonSchema
     * @return
     */
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
     * @param query
     * @return
     */
    public abstract boolean delete(JsonSchema jsonSchema,
            Query query);

    /**
     *
     * @param jsonSchema
     * @param validatedDataObject
     * @return
     */
    public abstract boolean update(JsonSchema jsonSchema,
            Map<String, Object> validatedDataObject);

    /**
     *
     * @param jsonSchema
     * @return
     */
    public abstract List<Map<String, Object>> list(JsonSchema jsonSchema);

    /**
     *
     * @param jsonSchema
     * @param query
     * @return
     */
    public abstract List<Map<String, Object>> list(JsonSchema jsonSchema,
            Query query);

}
