package io.github.gatimus.weatherviewer.openweathermap;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class LatLngDeserialzer implements JsonDeserializer<LatLng>{
    @Override
    public LatLng deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Double lat = json.getAsJsonObject().get("lat").getAsDouble();
        Double lng = json.getAsJsonObject().get("lon").getAsDouble();
        return new LatLng(lat, lng);
    }
}
