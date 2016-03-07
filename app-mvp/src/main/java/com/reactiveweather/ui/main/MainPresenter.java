package com.reactiveweather.ui.main;

import com.reactiveweather.BuildConfig;
import com.reactiveweather.ReactiveWeatherApplication;
import com.reactiveweather.data.weather.api.OpenWeatherApi;
import com.reactiveweather.data.weather.model.CurrentForecast;
import com.reactiveweather.ui.base.BasePresenter;

import rx.Observer;

public class MainPresenter extends BasePresenter<MainView> {
    private static final String TAG = MainPresenter.class.getSimpleName();

    public void showCurrentForecast(String city, String unit) {
        ReactiveWeatherApplication application = ReactiveWeatherApplication.get(getView().getContext());

        OpenWeatherApi api = application.getOpenWeatherApi();
        api.getCurrentWeather(city, BuildConfig.WEATHER_APP_ID, unit)
                .subscribeOn(ReactiveWeatherApplication.get(getView().getContext()).getSubscriberScheduler())
                .observeOn(ReactiveWeatherApplication.get(getView().getContext()).getObserverScheduler())
                .subscribe(new Observer<CurrentForecast>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().updateErrorMessage(e.getMessage());
                    }

                    @Override
                    public void onNext(CurrentForecast currentForecast) {
                        getView().updateCurrentForecast(currentForecast);
                    }
                });
    }
}
