package com.reactiveweather.data.weather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentForecast {
    @SerializedName("name")
    public String city;

    @SerializedName("weather")
    public List<Weather> weatherList;

    public Main main;

    @SerializedName("sys")
    public Country country;
}
