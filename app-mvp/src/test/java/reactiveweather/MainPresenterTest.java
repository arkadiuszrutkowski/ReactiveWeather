package reactiveweather;

import com.reactiveweather.BuildConfig;
import com.reactiveweather.ReactiveWeatherApplication;
import com.reactiveweather.data.weather.api.OpenWeatherApi;
import com.reactiveweather.data.weather.model.CurrentForecast;
import com.reactiveweather.ui.main.MainPresenter;
import com.reactiveweather.ui.main.MainView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainPresenterTest {
    private MainPresenter presenter = new MainPresenter();
    private MainView view;

    @Before
    public void setUp() throws Exception {
        view = mock(MainView.class);

        OpenWeatherApi api = mock(OpenWeatherApi.class);
        when(api.getCurrentWeather(anyString(), anyString(),anyString()))
                .thenReturn(Observable.just(new CurrentForecast()));

        ReactiveWeatherApplication application = (ReactiveWeatherApplication) RuntimeEnvironment.application;

        application.setSubscriberScheduler(Schedulers.immediate());
        application.setOpenWeatherApi(api);

        when(view.getContext()).thenReturn(application);
        presenter.attach(view);
    }

    @After
    public void tearDown() throws Exception {
        presenter.detach();
    }

    @Test
    public void testLoadCurrentWeather() throws Exception {
        presenter.showCurrentForecast("London", "metric");

        verify(view).updateCurrentForecast(any(CurrentForecast.class));
    }
}