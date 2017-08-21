package com.example.jubss.app_todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by seanj on 8/20/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todolist";
    private static final String TABLE_LIST = "tablelist";
    private static final String KEY_ID = "id";
    private static final String KEY_TASK = "taskname";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CONTACTS="CREATE TABLE " + TABLE_LIST + "("
                + KEY_ID +" INTEGER PRIMARY KEY,"
                + KEY_TASK +" TEXT " + ")";
        db.execSQL(CREATE_TABLE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);

        // Create tables again
        onCreate(db);
    }

    public boolean insertList(String taskname){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(KEY_TASK, taskname);
        db.insert(TABLE_LIST, null, content);

        return true;
    }

    public Cursor getAllItems(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_LIST;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + KEY_ID + " FROM " + TABLE_LIST +
                " WHERE " + KEY_TASK + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteList(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LIST, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }


}
