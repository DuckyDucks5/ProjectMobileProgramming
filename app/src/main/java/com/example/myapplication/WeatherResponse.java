package com.example.myapplication;

import com.example.myapplication.model.Sys;

import java.util.List;

public class WeatherResponse {
    private Coord coord;
    private Main main;
    private List<Weather> weather;
    private String name;


    public String getName() {
        return name;
    }

    public Coord getCoord(){
        return coord;
    }

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public class Main {
        private double temp;
        private int humidity;

        public double getTemp() {
            return temp;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    public class Weather {
        private String description;

        public String getDescription() {
            return description;
        }
    }

    public class Coord{
        private double lat;
        private double lon;

        public double getLon() {
            return lon;
        }

        public double getLat() {
            return lat;
        }
    }

}
