package com.decodar.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.decodar.seeds.Seed;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by indik on 9/4/2016.
 */
public class dbmanager extends SQLiteOpenHelper {

    public static final String db_name = "seed";
    public static final String seed_table = "allseeds";
    public static final String reply_table = "replies";

    public static final String seed_id = "ID";
    public static final String seed_text = "text";
    public static final String seed_image = "image";
    public static final String seed_likes = "likes";
    public static final String seed_replies = "replies";
    public static final String seed_favourite = "isfav";

    public static final String reply_id = "ID";
    public static final String reply_text = "text";
    public static final String reply_seed_id = "seedid";

    public static int database_version = 1;


    //singleton
    private static dbmanager database = null;

    public static synchronized dbmanager getInstance(Context context){
        if(database == null){
            database = new dbmanager(context);
        }
        return database;
    }



    public dbmanager(Context context) {
        super(context, db_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating all seeds table
        db.execSQL("CREATE TABLE " + seed_table + "(" +
                seed_id + " text primary key ," +
                seed_text + " text ," +
                seed_image + " text ," +
                seed_likes + " text ," +
                seed_replies + " text, " +
                seed_favourite + " integer)");

        //creating all replies table
        db.execSQL("CREATE TABLE " + reply_table + "(" +
                reply_id + " text primary key ," +
                reply_text + " text ," +
                reply_seed_id + " text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + seed_table);
        db.execSQL("DROP TABLE IF EXISTS " + reply_table);
        onCreate(db);
    }


    //Insert a seed
    public boolean addSeed(String ID, String text, String image, String likes, String replies, int isFavourite){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(seed_id, ID);
        cv.put(seed_text, text);
        cv.put(seed_image, image);
        cv.put(seed_likes, likes);
        cv.put(seed_replies, replies);
        cv.put(seed_favourite, isFavourite);

        db.insert(seed_table, null, cv);
        return true;
    }

    //Insert a reply
    public boolean addReply(String ID, String text, String seedID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(reply_id, ID);
        cv.put(reply_text, text);
        cv.put(reply_seed_id, seedID);

        db.insert(reply_table, null, cv);
        return true;
    }


    //Save seed as favourite
    public boolean saveFavourite(String ID, int isFavourite){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(seed_favourite, isFavourite);

        db.update(seed_table, cv, "id = ", new String[] {ID}); //Update the favourite column with the corresponding ID

        return true;
    }

    //Get all the seeds
    public Cursor getAllSeeds(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + seed_table, null);

        return res;
    }

    //Get replies for given ID
    public Cursor getReplies(String seedID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + reply_table + " WHERE " + reply_seed_id + "=" + seedID, null);

        return res;
    }

    //Get all the favourites
    public Cursor getFavouriteSeeds(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + seed_table + " WHERE " + seed_favourite + "=1", null);

        return res;
    }

    //Delete
}
