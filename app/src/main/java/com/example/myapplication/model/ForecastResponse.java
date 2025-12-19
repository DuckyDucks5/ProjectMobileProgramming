package com.example.myapplication.model;

import java.util.List;

public class ForecastResponse {
    private List<ForecastItem> list;

    public ForecastResponse(List<ForecastItem> list) {
        this.list = list;
    }

    public List<ForecastItem> getList() {
        return list;
    }

    public void setList(List<ForecastItem> list) {
        this.list = list;
    }
}
