package io.github.gatimus.weatherviewer.openweathermap;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import io.github.gatimus.weatherviewer.BuildConfig;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Query;

public class OpenWeatherMap {

    public static final String UNITS_METRIC = "metric";
    public static final String UNITS_IMPERIAL = "imperial";
    public static final String ACCURACY_HIGH = "accurate";
    public static final String ACCURACY_LOW = "like";

    public static interface OpenWeatherMapInterface {
        @GET("/data/2.5/weather")
        void getWeather(@Query("lat") float lat, @Query("lon") float lon, @Query("units") String units, Callback<CurrentWeather> cb);
        @GET("/data/2.5/weather")
        void getWeather(@Query("q") String city, @Query("units") String units, Callback<CurrentWeather> cb);
        @GET("/data/2.5/weather")
        void getWeather(@Query("id") int id, @Query("units") String units, Callback<CurrentWeather> cb);

        @GET("/data/2.5/forecast")
        void getForecast(@Query("lat") float lat, @Query("lon") float lon, @Query("units") String units, Callback<CurrentWeather> cb);
        @GET("/data/2.5/forecast")
        void getForecast(@Query("q") String city, @Query("units") String units, Callback<CurrentWeather> cb);
        @GET("/data/2.5/forecast")
        void getForecast(@Query("id") int id, @Query("units") String units, Callback<CurrentWeather> cb);

        @GET("/data/2.5/forecast/daily")
        void getDailyForecast();

        @GET("data/2.5/find")
        void find();
    }


    public static OpenWeatherMapInterface getOpenWeatherMapInterface() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapter(LatLng.class, new LatLngDeserialzer())
                .create();

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("x-api-key", "42d383998fc9e04f4385a0c5f19944f8");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.openweathermap.org")
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .build();

        if(BuildConfig.DEBUG){
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        return restAdapter.create(OpenWeatherMapInterface.class);
    }

}
