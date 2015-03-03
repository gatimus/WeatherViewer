package io.github.gatimus.weatherviewer.openweathermap;

import com.google.android.gms.maps.model.LatLng;

public class City {
    public long id;
    public String name;
    public LatLng coord;
    public String country;
    public long population;

    @Override
    public String toString(){
        return name;
    }
}
