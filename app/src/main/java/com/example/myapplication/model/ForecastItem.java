package com.example.myapplication.model;

import java.util.List;

public class ForecastItem {
    private MainWeather main;
    private List<Weather> weather;
    private String dt_txt;
    private long dt;

    public void setDt(long dt) {
        this.dt = dt;
    }

    public long getDt() {
        return dt;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    private double temp;

    public ForecastItem(String dt_txt, List<Weather> weather, MainWeather main) {
        this.dt_txt = dt_txt;
        this.weather = weather;
        this.main = main;
    }

    public MainWeather getMain() {
        return main;
    }

    public void setMain(MainWeather main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}
