package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = BuildConfig.OPENWEATHER_API_KEY;


    EditText cityInput;
    Button searchBtn;
    TextView resultText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        cityInput = findViewById(R.id.cityInput);
        searchBtn = findViewById(R.id.searchBtn);
        resultText = findViewById(R.id.resultText);

        searchBtn.setOnClickListener(v -> {
            String city = cityInput.getText().toString();
            if (city.isEmpty()) {
                Toast.makeText(this, "Masukkan nama kota", Toast.LENGTH_SHORT).show();
            } else {
                getWeather(city);
            }
        });
    }

    private void getWeather(String city) {
        APIService apiService = APIClient.getClient().create(APIService.class);
        Call<WeatherResponse> call = apiService.getWeatherByCity(city, API_KEY, "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    WeatherResponse data = response.body();

                    String text =
                            "Kota: " + data.getName() + "\n" +
                                    "Suhu: " + data.getMain().getTemp() + "`C\n" +
                                    "Cuaca: " + data.getWeather().get(0).getDescription() + "\n" +
                                    "Lat coord: " + data.getCoord().getLat() + "\n" +
                                    "Lon coord: " + data.getCoord().getLon();

                    resultText.setText(text);
                }
                else{
                    Toast.makeText(MainActivity.this, "Kota tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });

    }
}