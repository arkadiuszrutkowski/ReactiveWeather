package com.reactiveweather.ui.main;

import com.reactiveweather.BuildConfig;
import com.reactiveweather.ReactiveWeatherApplication;
import com.reactiveweather.data.weather.api.OpenWeatherApi;
import com.reactiveweather.data.weather.model.CurrentForecast;
import com.reactiveweather.ui.base.BasePresenter;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainPresenter extends BasePresenter<MainView> {
    private static final String TAG = MainPresenter.class.getSimpleName();

    public void showCurrentForecast(String city, String unit) {
        ReactiveWeatherApplication application = ReactiveWeatherApplication.get(getView().getContext());

        OpenWeatherApi api = application.getOpenWeatherApi();
        Call<CurrentForecast> call = api.getCurrentWeather(city, BuildConfig.WEATHER_APP_ID, unit);
        call.enqueue(new Callback<CurrentForecast>() {
            @Override
            public void onResponse(Response<CurrentForecast> response, Retrofit retrofit) {
                getView().updateCurrentForecast(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                getView().updateErrorMessage(t.getMessage());
            }
        });
    }
}
