package com.example.hodujjajko;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class ForecastService {
    private static final String APP_URL="https://api.darksky.net/";
    private static final String APP_KEY="4bb70eb1af30f6cae6a787084f25fd5a";

    public void LoadForecastDate(Callback<Forecast> callback, double latitude, double longitude){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ForecastAPI service = retrofit.create(ForecastAPI.class);
        Call<Forecast> call = service.getForecast(APP_KEY, Double.toString(latitude), Double.toString(longitude));
        call.enqueue(callback);
    }

}

