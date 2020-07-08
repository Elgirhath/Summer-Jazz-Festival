package ag.example1.festivalapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ag.example1.festivalapp.database.entity.ConcertEntity;
import ag.example1.festivalapp.database.orm.ORMapper;

public class DataBaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "CONCERTS";

    // Table columns
    public static final String _ID = "_id";
    public static final String ARTIST = "artist";
    public static final String DATE = "date";
    public static final String HOUR = "hour";
    public static final String PLACE = "place";
    public static final String LINK = "link";

    // Database Information
    static final String DB_NAME = "SUMMER_JAZZ_FESTIVAL.DB";

    // database version
    static final int DB_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private void createTable(SQLiteDatabase db) {
        ORMapper<ConcertEntity> mapper = new ORMapper<>(ConcertEntity.class);
        String[] values = mapper.toInsertableValues();

        StringBuilder valuesJoined = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            valuesJoined.append(values[i]);
            if (i < values.length - 1) {
                valuesJoined.append(", ");
            }
        }

        String query = "create table " + TABLE_NAME + "(" + valuesJoined + ");";
        db.execSQL(query);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dropTable(db, TABLE_NAME);
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db, TABLE_NAME);
        onCreate(db);
    }

    private void dropTable(SQLiteDatabase db, String tableName) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }
}
