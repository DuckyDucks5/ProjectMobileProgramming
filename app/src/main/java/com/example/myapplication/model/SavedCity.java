package com.example.myapplication.model;

import android.database.Cursor;

public class SavedCity {
    private int id;
    private String city;
    private double lat;
    private double lon;
    private int isPrimary;
    private long lastUpdated;

    public static SavedCity fromCursor(Cursor c) {
        SavedCity city = new SavedCity();

        city.id = c.getInt(c.getColumnIndexOrThrow("id"));
        city.city = c.getString(c.getColumnIndexOrThrow("city"));
        city.lat = c.getDouble(c.getColumnIndexOrThrow("lat"));
        city.lon = c.getDouble(c.getColumnIndexOrThrow("lon"));
        city.isPrimary = c.getInt(c.getColumnIndexOrThrow("is_primary"));

        return city;
    }

    public SavedCity() {
    }

    public SavedCity(int id, String city, double lat, double lon, int isPrimary, long lastUpdated) {
        this.id = id;
        this.city = city;
        this.lat = lat;
        this.lon = lon;
        this.isPrimary = isPrimary;
        this.lastUpdated = lastUpdated;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(int isPrimary) {
        this.isPrimary = isPrimary;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
