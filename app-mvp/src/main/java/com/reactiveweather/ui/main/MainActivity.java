package com.reactiveweather.ui.main;

import android.content.Context;
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
import com.reactiveweather.R;
import com.reactiveweather.ReactiveWeatherApplication;
import com.reactiveweather.data.weather.model.CurrentForecast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class MainActivity extends AppCompatActivity implements MainView {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MainPresenter presenter;

    @Bind(R.id.edit_text_city)
    EditText cityEditText;

    @Bind(R.id.text_city_name)
    TextView cityNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            presenter = ReactiveWeatherApplication.get(this).getPresenterCache().restore(MainActivity.class);
        } else {
            presenter = new MainPresenter();
        }
        presenter.attach(this);
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @SuppressWarnings("unused")
    @OnEditorAction(R.id.edit_text_city)
    public boolean onEditorActionCityEditText(TextView textView, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String unit = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("PREFERENCE_UNIT", "metric");
            presenter.showCurrentForecast(cityEditText.getText().toString(), unit);
            return true;
        }
        return false;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_search)
    public void onClickSearchButton() {
        String unit = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getString("PREFERENCE_UNIT", "metric");
        presenter.showCurrentForecast(cityEditText.getText().toString(), unit);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ReactiveWeatherApplication.get(this).getPresenterCache().save(MainActivity.class, presenter);
    }

    @Override
    public void updateCurrentForecast(CurrentForecast forecast) {
        Log.d(TAG, forecast.toString());
        cityNameTextView.setText(getString(R.string.text_city_country, forecast.city, forecast.country.country));
        Glide.with(MainActivity.this)
                .load("http://openweathermap.org/img/w/" + forecast.weatherList.get(0).icon + ".png")
                .into((ImageView) findViewById(R.id.image_weather_icon));
    }

    @Override
    public Context getContext() {
        return this;
    }
}
