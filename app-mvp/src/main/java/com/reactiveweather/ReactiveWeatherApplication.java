package com.reactiveweather;

import android.app.Application;
import android.content.Context;

import com.reactiveweather.data.weather.api.OpenWeatherApi;
import com.reactiveweather.ui.PresenterCache;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReactiveWeatherApplication extends Application {
    private OpenWeatherApi openWeatherApi;
    private Scheduler subscriberScheduler;
    private Scheduler observerScheduler;

    public static ReactiveWeatherApplication get(Context context) {
        return (ReactiveWeatherApplication) context.getApplicationContext();
    }

    public OpenWeatherApi getOpenWeatherApi() {
        if (openWeatherApi == null) openWeatherApi = OpenWeatherApi.Factory.create();
        return openWeatherApi;
    }

    public void setOpenWeatherApi(OpenWeatherApi openWeatherApi) {
        this.openWeatherApi = openWeatherApi;
    }

    public Scheduler getSubscriberScheduler() {
        if (subscriberScheduler == null) subscriberScheduler = Schedulers.io();
        return subscriberScheduler;
    }

    public void setSubscriberScheduler(Scheduler subscriberScheduler) {
        this.subscriberScheduler = subscriberScheduler;
    }

    public Scheduler getObserverScheduler() {
        if (observerScheduler == null) observerScheduler = AndroidSchedulers.mainThread();
        return observerScheduler;
    }

    public void setObserverScheduler(Scheduler observerScheduler) {
        this.observerScheduler = observerScheduler;
    }
}
