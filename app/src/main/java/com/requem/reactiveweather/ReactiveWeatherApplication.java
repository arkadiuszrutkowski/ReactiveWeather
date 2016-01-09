package com.requem.reactiveweather;

import android.app.Application;
import android.content.Context;

import com.requem.reactiveweather.data.weather.api.OpenWeatherApi;

public class ReactiveWeatherApplication extends Application {
    private OpenWeatherApi mOpenWeatherApi;

    public static ReactiveWeatherApplication get(Context context) {
        return (ReactiveWeatherApplication) context.getApplicationContext();
    }

    public OpenWeatherApi getOpenWeatherApi() {
        if (mOpenWeatherApi == null) mOpenWeatherApi = OpenWeatherApi.Factory.create();
        return mOpenWeatherApi;
    }
}
