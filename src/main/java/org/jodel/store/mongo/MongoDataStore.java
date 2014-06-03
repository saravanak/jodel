/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jodel.store.mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sathish_ku
 */
public class MongoDataStore {

    private DB db;

    public MongoDataStore() {
        try {
            MongoClient mongoClient = new MongoClient();
            db = mongoClient.getDB("zols");
        } catch (UnknownHostException ex) {
            Logger.getLogger(MongoDataStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getCollectionNames()  {
        Set<String> colls = db.getCollectionNames();
        for (String s : colls) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        MongoDataStore mongoDataStore = new MongoDataStore();
        mongoDataStore.getCollectionNames();

    }
}
