package io.github.gatimus.weatherviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.github.gatimus.weatherviewer.openweathermap.CurrentWeather;
import io.github.gatimus.weatherviewer.openweathermap.OpenWeatherMap;


public class WeatherAdapter extends ArrayAdapter {

    private Context context;
    private List<CurrentWeather> forecast;

    public WeatherAdapter(Context context, List objects) {
        super(context, R.layout.weather_layout, objects);
        this.context = context;
        forecast = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.weather_layout, parent, false);

        TextView dt = (TextView) rowView.findViewById(R.id.dt);
        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);
        TextView main = (TextView) rowView.findViewById(R.id.main);
        TextView description = (TextView) rowView.findViewById(R.id.description);
        TextView temp = (TextView) rowView.findViewById(R.id.temp);

        dt.setText(forecast.get(position).dt.toString());
        OpenWeatherMap.loadIcon(context, forecast.get(position).weather[0].icon, icon);
        main.setText(forecast.get(position).weather[0].main);
        description.setText(forecast.get(position).weather[0].description);
        temp.setText(String.valueOf(forecast.get(position).main.temp)+"\u2109");

        return rowView;
    }

}
