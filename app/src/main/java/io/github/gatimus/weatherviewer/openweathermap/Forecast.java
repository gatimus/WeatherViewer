package io.github.gatimus.weatherviewer.openweathermap;

public class Forecast {
    public String cod;
    public float message;
    public City city;
    public int cnt;
    public CurrentWeather[] list;
}
