package com.example.myapplication.api;

public class APISingleton {

    private static APIService apiService;

    public APISingleton() {
    }

    public static APIService getInstance(){
        if(apiService == null){
            apiService = APIClient.getClient().create(APIService.class);
        }
        return apiService;
    }

}
