package com.reactiveweather.data.weather.api;

public enum OpenWeatherUnit {
    UNIT_METRIC {
        @Override
        public String toString() {
            return "metric";
        }
    },
    UNIT_IMPERIAL {
        @Override
        public String toString() {
            return "imperial";
        }
    }
}
