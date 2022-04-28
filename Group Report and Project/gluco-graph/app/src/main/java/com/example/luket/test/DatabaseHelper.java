package com.example.luket.test;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Luket on 30/12/2017.
 */


//work in progress

public class DatabaseHelper extends SQLiteOpenHelper {

//TODO allow users to name the database at start up ... Luke-Glucose.db

    public static final String DATABASE_NAME = "UserGlucose.db";
    public static final String TABLE_NAME = "UserGlucoseTable";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "BLOOD_GLUCOSE"; // working title
    public static final String COL_3 = "TIME";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,BLOOD_GLUCOSE REAL, TIME DATETIME DEFAULT CURRENT_TIMESTAMP)"); // Rename "Time" to "DateTime"
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean insertData(String BLOOD_GLUCOSE) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,BLOOD_GLUCOSE);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME); //delete all rows in a table
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ= 0  where name= 'UserGlucoseTable'"); //still working title
        db.close();
    }
}
