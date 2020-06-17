package com.example.festivalapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String PROGRAM = "COUNTRIES";

    // Table columns
    public static final String _ID = "_id";
    public static final String ARTIST = "artist";
    public static final String DATE = "date";
    public static final String HOUR = "hour";
    public static final String PLACE = "place";
    public static final String LINK = "link";

    // Database Information
    static final String DB_NAME = "JOURNALDEV_COUNTRIES.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + PROGRAM + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ARTIST + " TEXT NOT NULL, " + DATE + " TEXT, " + HOUR + " TEXT, " + PLACE + " TEXT, " + LINK + " TEXT);";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PROGRAM);
        onCreate(db);
    }
}
