package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.api.APIClient;
import com.example.myapplication.api.APIService;
import com.example.myapplication.api.APISingleton;
import com.example.myapplication.database.WeatherDAO;
import com.example.myapplication.database.WeatherRepository;
import com.example.myapplication.model.SavedCity;
import com.example.myapplication.view.WeatherPagerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {



    private ViewPager2 viewPager;
    private WeatherPagerAdapter adapter;
    private WeatherRepository repository;
    private List<SavedCity> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new WeatherRepository(this);

        viewPager = findViewById(R.id.viewPager);
        EditText searchEt = findViewById(R.id.searchCityEt);
        Button searchBtn = findViewById(R.id.searchBtn);

        loadSavedCity();

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
        adapter = new WeatherPagerAdapter(this, cities);
        viewPager.setAdapter(adapter);
    }

    private void searchAndAddCity(String city){
        APISingleton.getInstance().getWeatherByCity(
                city,
                BuildConfig.OPENWEATHER_API_KEY,
                "metric"
        ).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weather = response.body();

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
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}