package io.github.gatimus.weatherviewer.openweathermap;

import com.google.android.gms.maps.model.LatLng;
import java.util.Date;

public class CurrentWeather {
    public LatLng coord;
    public Sys sys;
    public Weather[] weather;
    public String base;
    public Main main;
    public Wind wind;
    public Rain rain;
    public Snow snow;
    public Clouds clouds;
    public Date dt;
    public int id;
    public String name;
    public int cod;
    public Date dt_txt;

    @Override
    public String toString(){
        return name;
    }
}
