package com.reactiveweather.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.reactiveweather.R;
import com.reactiveweather.data.weather.model.CurrentForecast;
import com.reactiveweather.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.edit_text_city)
    EditText cityEditText;

    @Bind(R.id.text_city_name)
    TextView cityNameTextView;

    @Bind(R.id.temperature_text_view)
    TextView temperatureTextView;

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
    public void updateCurrentForecast(CurrentForecast forecast) {
        Log.d(TAG, forecast.toString());
        cityNameTextView.setText(getString(R.string.text_city_country, forecast.city, forecast.country.country));
        temperatureTextView.setText(getString(R.string.temperature, (int) forecast.main.temp));
        Glide.with(MainActivity.this)
                .load("http://openweathermap.org/img/w/" + forecast.weatherList.get(0).icon + ".png")
                .into((ImageView) findViewById(R.id.image_weather_icon));
    }

    @Override
    public void updateErrorMessage(String message) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Oops!")
                .setMessage("Something went wrong...")
                .show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected MainPresenter newPresenter() {
        return new MainPresenter();
    }

    @Override
    protected MainView getPresenterView() {
        return this;
    }
}
