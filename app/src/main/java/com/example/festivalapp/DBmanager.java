package com.example.festivalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBmanager {

    private DataBaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBmanager(Context c) {
        context = c;
    }

    public DBmanager open() throws SQLException {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String artist, String date, String hour, String place, String link) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DataBaseHelper.ARTIST, artist);
        contentValue.put(DataBaseHelper.DATE, date);
        contentValue.put(DataBaseHelper.HOUR, hour);
        contentValue.put(DataBaseHelper.PLACE, place);
        contentValue.put(DataBaseHelper.LINK, link);
        database.insert(DataBaseHelper.PROGRAM, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DataBaseHelper._ID, DataBaseHelper.ARTIST, DataBaseHelper.DATE, DataBaseHelper.HOUR, DataBaseHelper.PLACE, DataBaseHelper.LINK};
        Cursor cursor = database.query(DataBaseHelper.PROGRAM, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.ARTIST, name);
        contentValues.put(DataBaseHelper.DATE, desc);
        int i = database.update(DataBaseHelper.PROGRAM, contentValues, DataBaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DataBaseHelper.PROGRAM, DataBaseHelper._ID + "=" + _id, null);
    }

}
