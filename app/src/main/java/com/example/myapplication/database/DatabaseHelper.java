package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weatherapp.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_SAVED_CITY = "saved_city";

    public static final String COL_ID = "id";
    public static final String COL_CITY = "city";
    public static final String COL_LAT = "lat";
    public static final String COL_LON = "lon";
    public static final String COL_IS_PRIMARY = "is_primary";
    public static final String COL_LAST_UPDATED = "last_updated";

    private static final String CREATE_TABLE_SAVED_CITY =
            "CREATE TABLE " + TABLE_SAVED_CITY + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_CITY + " TEXT NOT NULL, " +
                    COL_LAT + " REAL, " +
                    COL_LON + " REAL, " +
                    COL_IS_PRIMARY + " INTEGER DEFAULT 0, " +
                    COL_LAST_UPDATED + " INTEGER" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SAVED_CITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_CITY);
        onCreate(db);
    }
}
