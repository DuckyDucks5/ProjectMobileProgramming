package com.example.myapplication.model;

public class MainWeather {
    private double temp;
    private double feels_like;
    private int humidity;

    public MainWeather(double temp, double feels_like, int humidity) {
        this.temp = temp;
        this.feels_like = feels_like;
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = feels_like;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
