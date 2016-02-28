package com.reactiveweather.ui.main;

import com.reactiveweather.data.weather.model.CurrentForecast;
import com.reactiveweather.ui.base.BaseView;

public interface MainView extends BaseView {

    void updateCurrentForecast(CurrentForecast forecast);
}
