package com.example.myapplication.model;

import java.util.List;

public class CurrentWeatherResponse {
    private Coord coord;
    private List<Weather> weather;
    private MainWeather main;
    private Sys sys;
    private int timezone;
    private String name;
    private int id;

    public CurrentWeatherResponse(Coord coord, List<Weather> weather, MainWeather main, Sys sys, int timezone, String name, int id) {
        this.coord = coord;
        this.weather = weather;
        this.main = main;
        this.sys = sys;
        this.timezone = timezone;
        this.name = name;
        this.id = id;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public MainWeather getMain() {
        return main;
    }

    public void setMain(MainWeather main) {
        this.main = main;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

