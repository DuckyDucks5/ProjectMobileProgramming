package com.example.myapplication.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.R;
import com.example.myapplication.WeatherResponse;
import com.example.myapplication.api.APIClient;
import com.example.myapplication.api.APIService;
import com.example.myapplication.model.ForecastResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {

    private static final String ARG_CITY = "city";
    private static final String ARG_LAT = "lat";
    private static final String ARG_LON = "lon";


    public static WeatherFragment newInstance(String city, double lat, double lon) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        args.putDouble(ARG_LAT, lat);
        args.putDouble(ARG_LON, lon);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_weather, container, false);

        TextView tvCity = view.findViewById(R.id.tvCity);
        TextView tvTemp = view.findViewById(R.id.tvTemp);
        TextView tvDesc = view.findViewById(R.id.tvDesc);
        TextView tvWeather1 = view.findViewById(R.id.tvWeather1);
        TextView tvWeather2 = view.findViewById(R.id.tvWeather2);

        if(getArguments() != null){
            String city = getArguments().getString(ARG_CITY);
            double lat = getArguments().getDouble(ARG_LAT);
            double lon = getArguments().getDouble(ARG_LON);

            tvCity.setText(city);

            APIService api = APIClient.getClient().create(APIService.class);
            String API_KEY = BuildConfig.OPENWEATHER_API_KEY;

            api.getCurrentWeather(lat, lon, API_KEY, "metric").enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    if(response.isSuccessful() && response.body() != null){
                        WeatherResponse weather = response.body();
                        tvTemp.setText(weather.getMain().getTemp() + "°C");
                        tvDesc.setText(weather.getWeather().get(0).getDescription());
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponse> call, Throwable t) {
                    tvDesc.setText("Failed to load weather");
                }
            });

            api.getForecast(lat, lon, API_KEY, "metric").enqueue(new Callback<ForecastResponse>() {
                @Override
                public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                    if(response.isSuccessful() && response.body() != null){
                        ForecastResponse forecast = response.body();
                        tvWeather1.setText(forecast.getList().get(0)
                                .getWeather().get(0).getDescription());

                        tvWeather2.setText(forecast.getList().get(0)
                                .getWeather().get(0).getDescription());
                    }
                }

                @Override
                public void onFailure(Call<ForecastResponse> call, Throwable t) {

                }
            });

        }

        return view;

    }
}