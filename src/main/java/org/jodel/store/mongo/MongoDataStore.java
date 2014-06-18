/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jodel.store.mongo;

import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.types.ObjectId;
import org.jodel.store.DataStore;

/**
 *
 * @author sathish_ku
 */
public class MongoDataStore extends DataStore {

    private static final String ID_FIELD = "_id";

    private DB db;

    public MongoDataStore() {
        try {
            MongoClient mongoClient = new MongoClient();
            db = mongoClient.getDB("zols");
        } catch (UnknownHostException ex) {
            Logger.getLogger(MongoDataStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @Override
    public Map<String, Object> create(JsonSchema jsonSchema, Map<String, Object> dataObject) {
        String idField = getIdField(jsonSchema);
        Object idFieldValue = dataObject.remove(idField);
        if(idFieldValue != null) {
            dataObject.put(ID_FIELD, new ObjectId(idFieldValue.toString()));
        }        
        DBObject dBObject = new BasicDBObject(dataObject);
        db.getCollection(jsonSchema.getId()).insert(dBObject);
        dataObject.remove(ID_FIELD);
        dataObject.put(idField, dBObject.get(ID_FIELD).toString());
        return dataObject;
    }

    @Override
    public Map<String, Object> read(JsonSchema jsonSchema, String idValue) {        
        DBObject query = new BasicDBObject(ID_FIELD, new ObjectId(idValue));          
        return getDataMap(db.getCollection(jsonSchema.getId()).findOne(query),getIdField(jsonSchema));
    }

    @Override
    public boolean delete(JsonSchema jsonSchema, String idValue) {
        DBObject query = new BasicDBObject(ID_FIELD, new ObjectId(idValue));
        db.getCollection(jsonSchema.getId()).remove(query);
        return true;
    }

    @Override
    public boolean update(JsonSchema jsonSchema, Map<String, Object> validatedDataObject) {
        String idField = getIdField(jsonSchema);
        Object idFieldValue = validatedDataObject.get(idField);
        validatedDataObject.remove(idField);
        DBObject query = new BasicDBObject(ID_FIELD, new ObjectId(idFieldValue.toString()));
        DBObject dBObject = new BasicDBObject(validatedDataObject);
        db.getCollection(jsonSchema.getId()).findAndModify(query, (dBObject));
        return true;
    }

    @Override
    public List<Map<String, Object>> list(JsonSchema jsonSchema) {
        String idField = getIdField(jsonSchema);
        List<DBObject> dBObjects = db.getCollection(jsonSchema.getId()).find().toArray();
        if(dBObjects != null) {            
            List<Map<String, Object>> listOfMap = new ArrayList<>(dBObjects.size());
            for (DBObject dBObject : dBObjects) {
                listOfMap.add(getDataMap(dBObject,idField));
            }
            return listOfMap;
        }
        return null;
    }
    
    private Map<String, Object> getDataMap(DBObject dBObject,String idFieldName) {
        Map<String, Object> dataMap = dBObject.toMap();
        dataMap.put(idFieldName, dataMap.remove(ID_FIELD).toString());
        return dataMap;
    }

}
