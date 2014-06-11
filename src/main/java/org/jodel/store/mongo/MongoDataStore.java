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
import java.util.Map;
import java.util.Set;
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

    private void getCollectionNames() {
        Set<String> colls = db.getCollectionNames();
        for (String s : colls) {
            System.out.println(s);
        }
    }

    @Override
    public Map<String, Object> create(Map<String, Object> validatedDataObject, JsonSchema jsonSchema) {
        String idField = getIdField(jsonSchema);
        Object idFieldValue = validatedDataObject.get(idField);
        validatedDataObject.remove(idField);
        validatedDataObject.put(ID_FIELD, idFieldValue);
        DBObject dBObject =  new BasicDBObject(validatedDataObject);
        db.getCollection(jsonSchema.getId()).insert(dBObject);
        validatedDataObject.remove(ID_FIELD);
        validatedDataObject.put(idField, dBObject.get(ID_FIELD).toString());
        return validatedDataObject;        
    }


    @Override
    public Map<String, Object> read(JsonSchema jsonSchema, String idValue) {
        DBObject query =  new BasicDBObject(ID_FIELD, new ObjectId(idValue));
        Map<String, Object> dataMap = db.getCollection(jsonSchema.getId()).findOne(query).toMap();
        String idField = getIdField(jsonSchema);
        Object idFieldValue = dataMap.get(ID_FIELD);
        dataMap.remove(ID_FIELD);
        dataMap.put(idField, idFieldValue.toString());
        return dataMap;
    }

}
