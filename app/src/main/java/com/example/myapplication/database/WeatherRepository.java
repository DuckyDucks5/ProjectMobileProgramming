package com.example.myapplication.database;

import com.example.myapplication.MainActivity;
import com.example.myapplication.api.APIClient;
import com.example.myapplication.api.APIService;
import com.example.myapplication.model.SavedCity;

import java.util.List;

public class WeatherRepository {
    private APIService api;
    private WeatherDAO dao;

    public WeatherRepository(MainActivity context) {
        api = APIClient.getClient().create(APIService.class);
        dao = new WeatherDAO(context);
    }

    public List<SavedCity> getSavedCity(){
        return dao.getAllCities();
    }
    public SavedCity getPrimaryCity(){
        return dao.getPrimaryCity();
    }

    public void insertCity(String city, double lat, double lon, int isPrimary){
        dao.insertCity(city, lat, lon, isPrimary);
    }


}
