package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.model.SavedCity;

import java.util.ArrayList;
import java.util.List;

public class WeatherDAO {
    private DatabaseHelper dbHelper;

    public WeatherDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertCity(String city, double lat, double lon, int isPrimary){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if(isPrimary == 1){
            db.execSQL(
                    "UPDATE " + DatabaseHelper.TABLE_SAVED_CITY +
                            " SET " + DatabaseHelper.COL_IS_PRIMARY + " = 0"
            );
        }

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_CITY, city);
        values.put(DatabaseHelper.COL_LAT, lat);
        values.put(DatabaseHelper.COL_LON, lon);
        values.put(DatabaseHelper.COL_IS_PRIMARY, isPrimary);
        values.put(DatabaseHelper.COL_LAST_UPDATED, System.currentTimeMillis());

        long id = db.insert(DatabaseHelper.TABLE_SAVED_CITY, null, values);
        db.close();
        return id;
    }

    public boolean hasCity(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM saved_city", null
        );
        cursor.moveToFirst();
        boolean result = cursor.getInt(0) > 0;
        cursor.close();
        db.close();
        return result;
    }

    public SavedCity getPrimaryCity(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM saved_city WHERE is_primary = 1 LIMIT 1", null);
        if(cursor.moveToFirst()){
            SavedCity city = SavedCity.fromCursor(cursor);
            cursor.close();
            db.close();
            return city;
        }
        return null;
    }

    public List<SavedCity> getAllCities(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<SavedCity> city = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM saved_city ORDER BY is_primary DESC", null);

        while(cursor.moveToNext()){
            city.add(SavedCity.fromCursor(cursor));
        }
        cursor.close();
        db.close();
        return city;

    }


}
