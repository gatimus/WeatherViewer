package io.github.gatimus.weatherviewer.openweathermap;

import android.util.Log;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateDeserializer implements JsonDeserializer<Date> {
                                              //"2015-03-03 03:00:00"
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonPrimitive jsonPrimitive = (JsonPrimitive) json;
        Date date = null;
        if(jsonPrimitive.isString()){
            synchronized (sdf){
                try{
                    date = sdf.parse(jsonPrimitive.getAsString());
                } catch (ParseException e) {
                    Log.e(getClass().getSimpleName(), e.toString());
                }
            }
        } else if(jsonPrimitive.isNumber()){
            date = new Date(jsonPrimitive.getAsLong());
        }
        return date;
    }
}
