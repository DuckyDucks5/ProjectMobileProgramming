package com.example.myapplication.api;

import com.example.myapplication.WeatherResponse;
import com.example.myapplication.model.CurrentWeatherResponse;
import com.example.myapplication.model.ForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("data/2.5/weather")
    Call<CurrentWeatherResponse> getWeatherByCity(
        @Query("q") String city,
        @Query("appid") String apiKey,
        @Query("units") String units
    );

    // Cuaca saat ini berdasarkan KOORDINAT
    @GET("data/2.5/weather")
    Call<CurrentWeatherResponse> getCurrentWeather(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String apiKey,
            @Query("units") String units
    );

    // Forecast 5 hari (3 jam interval)
    @GET("data/2.5/forecast")
    Call<ForecastResponse> getForecast(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String apiKey,
            @Query("units") String units
    );

}
