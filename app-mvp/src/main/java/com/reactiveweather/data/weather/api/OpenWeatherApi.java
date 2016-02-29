package com.reactiveweather.data.weather.api;

import com.reactiveweather.data.weather.model.CurrentForecast;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

public interface OpenWeatherApi {
    String ENDPOINT = "http://api.openweathermap.org/data/2.5/";

    @GET("weather")
    Call<CurrentForecast> getCurrentWeather(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String unit);

    class Factory {
        public static OpenWeatherApi create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ENDPOINT)
                    .build();

            return retrofit.create(OpenWeatherApi.class);
        }
    }
}
