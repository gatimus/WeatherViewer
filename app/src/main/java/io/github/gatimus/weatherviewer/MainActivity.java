package io.github.gatimus.weatherviewer;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import io.github.gatimus.weatherviewer.openweathermap.CurrentWeather;
import io.github.gatimus.weatherviewer.openweathermap.Forecast;
import io.github.gatimus.weatherviewer.openweathermap.OpenWeatherMap;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private Location location;
    private OpenWeatherMap.OpenWeatherMapInterface weatherInterface;
    private ListView listView;
    private List<CurrentWeather> weathers;
    private WeatherAdapter weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(android.R.id.list);
        listView.setEmptyView(findViewById(android.R.id.empty));
        weathers = new ArrayList<CurrentWeather>();
        weatherAdapter = new WeatherAdapter(getApplicationContext(), weathers);

        listView.setAdapter(weatherAdapter);
        weatherAdapter.setNotifyOnChange(true);
        weatherInterface = OpenWeatherMap.getOpenWeatherMapInterface();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        weatherInterface.getWeather(location.getLatitude(), location.getLongitude(), OpenWeatherMap.UNITS_IMPERIAL, new Callback<CurrentWeather>() {
            @Override
            public void success(CurrentWeather currentWeather, Response response) {
                setTitle(currentWeather.name);

                TextView dt = (TextView) findViewById(R.id.dt);
                ImageView icon = (ImageView) findViewById(R.id.icon);
                TextView main = (TextView) findViewById(R.id.main);
                TextView description = (TextView) findViewById(R.id.description);
                TextView temp = (TextView) findViewById(R.id.temp);

                dt.setText("Now");
                OpenWeatherMap.loadIcon(getApplicationContext(),currentWeather.weather[0].icon,icon);
                main.setText(currentWeather.weather[0].main);
                description.setText(currentWeather.weather[0].description);
                temp.setText(String.valueOf(currentWeather.main.temp)+"\u2109");

                weatherInterface.getForecast(currentWeather.id, OpenWeatherMap.UNITS_IMPERIAL ,new Callback<Forecast>() {
                    @Override
                    public void success(Forecast forecast, Response response) {
                        for(CurrentWeather weather : forecast.list){
                            weathers.add(weather);
                            //weatherAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e(getClass().getSimpleName(), error.getResponse().getReason());
                    }
                });
            }
            @Override
            public void failure(RetrofitError error) {
                Log.e(getClass().getSimpleName(), error.getResponse().getReason());
            }
        });

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(getClass().getSimpleName(), "connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(getClass().getSimpleName(), connectionResult.toString());
    }

}
