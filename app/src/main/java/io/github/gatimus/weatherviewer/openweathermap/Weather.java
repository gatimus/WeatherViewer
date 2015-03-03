package io.github.gatimus.weatherviewer.openweathermap;

public class Weather {
    public int id;
    public String main;
    public String description;
    public String icon;

    @Override
    public String toString(){
        return main;
    }
}
