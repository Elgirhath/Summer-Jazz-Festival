package com.example.festivalapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.festivalapp.database.entity.ConcertEntity;
import com.example.festivalapp.database.orm.ORMapper;

public class DBmanager {

    private DataBaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    ORMapper<ConcertEntity> mapper = new ORMapper<>(ConcertEntity.class);

    public DBmanager(Context c) {
        context = c;
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public DBmanager initialize() throws SQLException {
        dbHelper.onCreate(database);
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(ConcertEntity entity) {
        try {
            database.insert(DataBaseHelper.TABLE_NAME, null, mapper.toContentValues(entity));
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public ConcertEntity[] executeQuery(String sql) {
        Cursor cursor = database.rawQuery(sql, null);
        return mapper.toObjects(cursor);
    }

    public ConcertEntity[] fetch() {
        return executeQuery("select * from " + DataBaseHelper.TABLE_NAME);
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
