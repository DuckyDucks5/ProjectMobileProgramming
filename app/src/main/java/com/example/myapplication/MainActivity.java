package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.api.APIClient;
import com.example.myapplication.api.APIService;
import com.example.myapplication.api.APISingleton;
import com.example.myapplication.database.WeatherDAO;
import com.example.myapplication.database.WeatherRepository;
import com.example.myapplication.model.CurrentWeatherResponse;
import com.example.myapplication.model.SavedCity;
import com.example.myapplication.view.WeatherPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {



    private ViewPager2 viewPager;
    private WeatherPagerAdapter adapter;
    private WeatherRepository repository;
    private List<SavedCity> cities;
    private LinearLayout dotIndicator;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new WeatherRepository(this);

        viewPager = findViewById(R.id.viewPager);
        dotIndicator = findViewById(R.id.dotIndicator);
        EditText searchEt = findViewById(R.id.searchCityEtCompact);
        ImageButton searchBtn = findViewById(R.id.searchBtnEtCompact);

        loadSavedCity();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPage = position;
                setupDots(cities.size());
            }
        });

        searchBtn.setOnClickListener(v ->{
            String city = searchEt.getText().toString().trim();
            if(!city.isEmpty()){
                searchAndAddCity(city);
                searchEt.setText("");
                Toast.makeText(this, "Search: " + city, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadSavedCity(){
        cities = repository.getSavedCity();
        if (cities == null) {
            cities = new ArrayList<>(); // inisialisasi list kosong
        }
        adapter = new WeatherPagerAdapter(this, cities);
        viewPager.setAdapter(adapter);

        setupDots(cities.size());
    }

    private void searchAndAddCity(String city){
        APISingleton.getInstance().getWeatherByCity(
                city,
                BuildConfig.OPENWEATHER_API_KEY,
                "metric"
        ).enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CurrentWeatherResponse weather = response.body();

                    // Dapat koordinat dari response
                    String city = weather.getName();
                    double lat = weather.getCoord().getLat();
                    double lon = weather.getCoord().getLon();

                    // Simpan ke database (isPrimary = 0 karena bukan kota utama)
                    repository.insertCity(city, lat, lon, 0);

                    // Buat object SavedCity baru
                    SavedCity newCity = new SavedCity();
                    newCity.setCity(city);
                    newCity.setLat(lat);
                    newCity.setLon(lon);
                    newCity.setIsPrimary(0);

                    // Tambahkan ke list
                    cities.add(newCity);

                    // Update adapter
                    adapter.notifyItemInserted(cities.size() - 1);

                    // Update dot indicator
                    setupDots(cities.size());

                    // Pindah ke halaman kota baru
                    viewPager.setCurrentItem(cities.size() - 1, true);


                    Toast.makeText(MainActivity.this,
                            city + " berhasil ditambahkan",
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this,
                            "Kota tidak ditemukan",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDots(int count) {
        dotIndicator.removeAllViews();

        for (int i = 0; i < count; i++) {
            View dot = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    16, 16 // width & height dot
            );
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);

            if (i == currentPage) {
                dot.setBackgroundResource(R.drawable.dot_active);
            } else {
                dot.setBackgroundResource(R.drawable.dot_inactive);
            }

            dotIndicator.addView(dot);
        }
    }

}