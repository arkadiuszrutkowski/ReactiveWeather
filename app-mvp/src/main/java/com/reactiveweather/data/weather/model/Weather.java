package com.reactiveweather.data.weather.model;

public class Weather {
    public String main;

    public String icon;

    public String description;

    @Override
    public String toString() {
        return "Weather{" +
                "main='" + main + '\'' +
                ", icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
