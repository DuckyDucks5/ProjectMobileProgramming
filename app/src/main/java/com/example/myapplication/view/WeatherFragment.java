package com.example.myapplication.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.R;
import com.example.myapplication.WeatherResponse;
import com.example.myapplication.api.APIClient;
import com.example.myapplication.api.APIService;
import com.example.myapplication.model.CurrentWeatherResponse;
import com.example.myapplication.model.ForecastItem;
import com.example.myapplication.model.ForecastResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        TextView tvWeather3 = view.findViewById(R.id.tvWeather3);
        TextView tvWeather4 = view.findViewById(R.id.tvWeather4);
        TextView tvWeather5 = view.findViewById(R.id.tvWeather5);
        TextView tvWeather6 = view.findViewById(R.id.tvWeather6);

        TextView tempWeather1 = view.findViewById(R.id.tempWeather1);
        TextView tempWeather2 = view.findViewById(R.id.tempWeather2);
        TextView tempWeather3 = view.findViewById(R.id.tempWeather3);
        TextView tempWeather4 = view.findViewById(R.id.tempWeather4);
        TextView tempWeather5 = view.findViewById(R.id.tempWeather5);
        TextView tempWeather6 = view.findViewById(R.id.tempWeather6);

        TextView hourWeather1 = view.findViewById(R.id.hourWeather1);
        TextView hourWeather2 = view.findViewById(R.id.hourWeather2);
        TextView hourWeather3 = view.findViewById(R.id.hourWeather3);
        TextView hourWeather4 = view.findViewById(R.id.hourWeather4);
        TextView hourWeather5 = view.findViewById(R.id.hourWeather5);
        TextView hourWeather6 = view.findViewById(R.id.hourWeather6);

        TextView dateDay1 = view.findViewById(R.id.dateDay1);
        TextView weatherDay1 = view.findViewById(R.id.weatherDay1);
        TextView tempDay1 = view.findViewById(R.id.tempDay1);

        TextView dateDay2 = view.findViewById(R.id.dateDay2);
        TextView weatherDay2 = view.findViewById(R.id.weatherDay2);
        TextView tempDay2 = view.findViewById(R.id.tempDay2);

        TextView dateDay3 = view.findViewById(R.id.dateDay3);
        TextView weatherDay3 = view.findViewById(R.id.weatherDay3);
        TextView tempDay3 = view.findViewById(R.id.tempDay3);

        TextView dateDay4 = view.findViewById(R.id.dateDay4);
        TextView weatherDay4 = view.findViewById(R.id.weatherDay4);
        TextView tempDay4 = view.findViewById(R.id.tempDay4);

        TextView dateDay5 = view.findViewById(R.id.dateDay5);
        TextView weatherDay5 = view.findViewById(R.id.weatherDay5);
        TextView tempDay5 = view.findViewById(R.id.tempDay5);

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

            api.getForecast(lat, lon, API_KEY, "metric")
                .enqueue(new Callback<ForecastResponse>() {
                    @Override
                    public void onResponse(Call<ForecastResponse> call,
                                           Response<ForecastResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            List<ForecastItem> list = response.body().getList();

                            int startIndex = 2;
                            int limit = Math.min(startIndex + 6, list.size());

                            for (int i = startIndex; i < limit; i++) {
                                ForecastItem item = list.get(i);

                                String hour = item.getDt_txt().substring(11, 13);
                                String desc = item.getWeather().get(0).getMain();
                                String temp = item.getMain().getTemp() + "°C";


                                int displayIndex = i - startIndex; // 0..5

                                switch (displayIndex) {
                                    case 0:
                                        hourWeather1.setText(hour);
                                        tvWeather1.setText(desc);
                                        tempWeather1.setText(temp);
                                        break;
                                    case 1:
                                        hourWeather2.setText(hour);
                                        tvWeather2.setText(desc);
                                        tempWeather2.setText(temp);
                                        break;
                                    case 2:
                                        hourWeather3.setText(hour);
                                        tvWeather3.setText(desc);
                                        tempWeather3.setText(temp);
                                        break;
                                    case 3:
                                        hourWeather4.setText(hour);
                                        tvWeather4.setText(desc);
                                        tempWeather4.setText(temp);
                                        break;
                                    case 4:
                                        hourWeather5.setText(hour);
                                        tvWeather5.setText(desc);
                                        tempWeather5.setText(temp);
                                        break;
                                    case 5:
                                        hourWeather6.setText(hour);
                                        tvWeather6.setText(desc);
                                        tempWeather6.setText(temp);
                                        break;
                                }
                            }
                            String todayDate = list.get(0).getDt_txt().substring(0, 10);

                            Map<String, ForecastItem> dailyForecast = new LinkedHashMap<>();

                            for (ForecastItem item : list) {

                                // yyyy-MM-dd
                                String date = item.getDt_txt().substring(0, 10);
                                String time = item.getDt_txt().substring(11, 19);

                                if (date.equals(todayDate)) {
                                    continue;
                                }

                                if (!time.equals("06:00:00")) {
                                    continue;
                                }

                                // Simpan hanya 1 data per hari
                                if (!dailyForecast.containsKey(date)) {
                                    dailyForecast.put(date, item);
                                }
                            }

                            int dayIndex = 0;

                            for (ForecastItem item : dailyForecast.values()) {

                                if (dayIndex == 5) break;

                                String day = item.getDt_txt().substring(8,10);
                                String month = item.getDt_txt().substring(5,7);
                                String desc = item.getWeather().get(0).getMain(); // Cuaca
                                String temp = item.getMain().getTemp() + "°C";    // Suhu

                                switch (dayIndex) {
                                    case 0:
                                        dateDay1.setText(day + "/" + month);
                                        weatherDay1.setText(desc);
                                        tempDay1.setText(temp);
                                        break;
                                    case 1:
                                        dateDay2.setText(day + "/" + month);
                                        weatherDay2.setText(desc);
                                        tempDay2.setText(temp);
                                        break;
                                    case 2:
                                        dateDay3.setText(day + "/" + month);
                                        weatherDay3.setText(desc);
                                        tempDay3.setText(temp);
                                        break;
                                    case 3:
                                        dateDay4.setText(day + "/" + month);
                                        weatherDay4.setText(desc);
                                        tempDay4.setText(temp);
                                        break;
                                    case 4:
                                        dateDay5.setText(day + "/" + month);
                                        weatherDay5.setText(desc);
                                        tempDay5.setText(temp);
                                        break;
                                }

                                dayIndex++;
                            }






                        }
                    }

                    @Override
                    public void onFailure(Call<ForecastResponse> call, Throwable t) {
                        Log.e("FORECAST_ERROR", t.getMessage());
                    }
                });



        }

        return view;

    }
}