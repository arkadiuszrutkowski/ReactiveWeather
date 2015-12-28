package com.requem.reactiveweather.data.weather.api;

import com.requem.reactiveweather.BuildConfig;
import com.requem.reactiveweather.data.weather.model.WeatherForecast;

import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface OpenWeatherApi {
    String ENDPOINT = "api.openweathermap.org/data/2.5/";
    String API_KEY = BuildConfig.WEATHER_APP_ID;

    @GET("weather?q={city}&APPID=" + API_KEY)
    Observable<WeatherForecast> getCurrrentWeather(@Path("city") String city);

    @GET("weather?q={city},{code}&APPID=" + API_KEY)
    Observable<WeatherForecast> getCurrrentWeather(@Path("city") String city, @Path("code") String countryCode);

    class Factory {
        public static OpenWeatherApi create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .build();

            return retrofit.create(OpenWeatherApi.class);
        }
    }
}
