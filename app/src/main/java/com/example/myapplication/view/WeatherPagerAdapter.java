package com.example.myapplication.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.model.SavedCity;

import java.util.List;

public class WeatherPagerAdapter extends FragmentStateAdapter {
    private List<SavedCity> cities;
    public WeatherPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<SavedCity> cities) {
        super(fragmentActivity);
        this.cities = cities;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        SavedCity city = cities.get(position);
        return WeatherFragment.newInstance(city.getCity(), city.getLat(), city.getLon());

    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
