package com.reactiveweather.ui.main;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reactiveweather.BuildConfig;
import com.reactiveweather.R;
import com.reactiveweather.ReactiveWeatherApplication;
import com.reactiveweather.data.weather.api.OpenWeatherApi;
import com.reactiveweather.data.weather.model.CurrentForecast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.edit_text_city)
    EditText cityEditText;

    @Bind(R.id.text_city_name)
    TextView cityNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @SuppressWarnings("unused")
    @OnEditorAction(R.id.edit_text_city)
    public boolean onEditorActionCityEditText(TextView textView, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String unit = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("PREFERENCE_UNIT", "metric");
            searchCurrentWeather(cityEditText.getText().toString(), unit);
            return true;
        }
        return false;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_search)
    public void onClickSearchButton() {
        String unit = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("PREFERENCE_UNIT", "metric");
        searchCurrentWeather(cityEditText.getText().toString(), unit);
    }

    private void searchCurrentWeather(String city, String unit) {
        OpenWeatherApi api = ReactiveWeatherApplication.get(this).getOpenWeatherApi();
        api.getCurrentWeather(city, BuildConfig.WEATHER_APP_ID, unit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CurrentForecast>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "error!", e);
                    }

                    @Override
                    public void onNext(CurrentForecast weather) {
                        Log.d(TAG, weather.toString());
                        cityNameTextView.setText(getString(R.string.text_city_country, weather.city, weather.country.country));
                        Glide.with(MainActivity.this)
                                .load("http://openweathermap.org/img/w/" + weather.weatherList.get(0).icon + ".png")
                                .into((ImageView) findViewById(R.id.image_weather_icon));
                    }
                });
    }
}
