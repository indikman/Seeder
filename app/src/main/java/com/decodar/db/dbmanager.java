package com.decodar.db;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.util.Log;
import com.couchbase.lite.util.StreamUtils;
import com.decodar.seeds.Seed;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by indik on 9/4/2016.
 */
public class dbmanager {

    private String DBName = "DatabaseName";
    final String TAG = "CouchbaseEvents";

    private Manager manager = null;
    private Database database = null;

    public void initiateDB(AndroidContext context, String databaseName){


        try {
            manager = this.getManagerInstance(context);
            database = this.getDatabaseInstance();
        }catch(Exception e){
            Log.e(TAG, "Error initiating database", e);
            //return;
        }
    }


    //--------------Singleton Usage of DB Manager------------------------
    public Database getDatabaseInstance() throws CouchbaseLiteException{
        if((this.database == null) & (this.manager != null)){
            this.database = manager.getDatabase(DBName);
        }
        return  database;
    }

    public Manager getManagerInstance(AndroidContext context) throws IOException{
        if(this.manager == null)
        {
            manager = new Manager(context, Manager.DEFAULT_OPTIONS);
        }
        return manager;
    }

    //-------------Database Actions ------------------------------------

    public String createDocument(Database database, Seed seed) throws CouchbaseLiteException {      //Adding a new Seed
        Document document = this.getDatabaseInstance().createDocument();
        String documentID = document.getId();

        //Adding a new Hashmap to store data
        Map<String, Object> seedMap = new HashMap<String, Object>();

        //Adding new seed values
        seedMap.put("ID", seed.getID());
        seedMap.put("text", seed.getSeed_text());
        seedMap.put("image", seed.getSeed_image());
        seedMap.put("likes", seed.getNo_of_likes());
        seedMap.put("replies", seed.getNo_of_replies());

        try {
            document.putProperties(seedMap);
        }catch (CouchbaseLiteException e){
            Log.e(TAG, "Error inserting new seed", 0);
        }

        return documentID;
    }

    public Document retrieveDocument(String docID) throws CouchbaseLiteException {
        Document retrievedDocument = getDatabaseInstance().getDocument(docID);
        return retrievedDocument;
    }
}
