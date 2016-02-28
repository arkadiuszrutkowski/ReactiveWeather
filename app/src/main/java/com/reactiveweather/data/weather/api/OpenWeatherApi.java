package com.reactiveweather.data.weather.api;

import com.reactiveweather.data.weather.model.CurrentForecast;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface OpenWeatherApi {
    String ENDPOINT = "http://api.openweathermap.org/data/2.5/";

    @GET("weather")
    Observable<CurrentForecast> getCurrentWeather(@Query("q") String city, @Query("appid") String apiKey, @Query("units") String unit);

    class Factory {
        public static OpenWeatherApi create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ENDPOINT)
                    .build();

            return retrofit.create(OpenWeatherApi.class);
        }
    }
}
