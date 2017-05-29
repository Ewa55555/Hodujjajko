package com.example.hodujjajko;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ForecastAPI {
    @GET("forecast/{key}/{latitude},{longitude}")
    public Call<Forecast> getForecast(
            @Path("key") String key,
            @Path("latitude") String latitude,
            @Path("longitude") String longitude
    );

}