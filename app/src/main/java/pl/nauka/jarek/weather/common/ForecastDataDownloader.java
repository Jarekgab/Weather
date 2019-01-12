package pl.nauka.jarek.weather.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import pl.nauka.jarek.weather.model.forecast.City;
import pl.nauka.jarek.weather.model.forecast.Clouds;
import pl.nauka.jarek.weather.model.forecast.ForecastCityWeather;
import pl.nauka.jarek.weather.model.forecast.Main;
import pl.nauka.jarek.weather.model.forecast.Rain;
import pl.nauka.jarek.weather.model.forecast.Sys;
import pl.nauka.jarek.weather.model.forecast.Weather;
import pl.nauka.jarek.weather.model.forecast.Wind;

public class ForecastDataDownloader {

    private static RequestQueue requestQueue;

    public interface CityWeatherResponseCallback {
        void onSuccess(ForecastCityWeather data);
        void onError(Exception exception);
    }

    public static void getUrlData(String jsonUrl, final Context context, final CityWeatherResponseCallback responseCallback){
        requestQueue = Volley.newRequestQueue(context);

        ForecastRequest jar = new ForecastRequest(jsonUrl, null, new Response.Listener<ForecastCityWeather>() {
            @Override
            public void onResponse(ForecastCityWeather response) {
                responseCallback.onSuccess(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("JG", error.getLocalizedMessage(), error);
                        Toast.makeText(context, "Błąd pobierania danych", Toast.LENGTH_LONG).show();
                        responseCallback.onError(error);
                    }
                }
        );
        requestQueue.add(jar);
    }
}

class ForecastRequest extends JsonRequest<ForecastCityWeather> {

    public ForecastRequest(String url, @Nullable String requestBody, Response.Listener<ForecastCityWeather> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.GET , url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<ForecastCityWeather> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(
                            response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

            JSONObject jsonObject = new JSONObject(jsonString);
            ForecastCityWeather cityWeather = parseJsonObject(jsonObject);

            return Response.success(cityWeather, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @NonNull
    private ForecastCityWeather parseJsonObject(JSONObject response) throws JSONException {
        final List<pl.nauka.jarek.weather.model.forecast.List> forecastList = new ArrayList<>();

        //Stale: cod, message, cnt
        Gson gson = new Gson();
        String cod = (String) response.get("cod");
        Double message = (Double) response.get("message");
        Integer cnt = (Integer) response.get("cnt");

        //Wyznaczenie długosci listy
        JSONArray JSONlist = (JSONArray) response.get("list");

        for (int i = 0; i < JSONlist.length(); i++) {

            //Stala dt
            JSONObject allList = (JSONObject) response.getJSONArray("list").get(i);
            Integer dt = (Integer) allList.getInt("dt");

            //Klasa main
            Main main;
            try{
                Object objectMain = allList.get("main");
                main = gson.fromJson(objectMain.toString(), Main.class);
            } catch (JSONException e) {
                main = new Main();
            }

            //Klasa weather
            JSONObject objectWeather = (JSONObject) allList.getJSONArray("weather").get(0);
            Weather weather = gson.fromJson(objectWeather.toString(), Weather.class);

            //Klasa clouds
            Object objectClouds = allList.get("clouds");
            Clouds clouds = gson.fromJson(objectClouds.toString(), Clouds.class);

            //Klasa wind
            Object objectWind = allList.get("wind");
            Wind wind = gson.fromJson(objectWind.toString(), Wind.class);

            //Klasa rain
            Rain rain;
            try {
                Object objectRain = allList.get("rain");
                rain = gson.fromJson(objectRain.toString(), Rain.class);

            } catch (JSONException e) {
                rain = new Rain();
            }

            //Klasa sys
            Object objectSys = allList.get("sys");
            Sys sys = gson.fromJson(objectSys.toString(), Sys.class);

            //Stala dtTxt
            String dtTxt = (String) allList.getString("dt_txt");

            pl.nauka.jarek.weather.model.forecast.List list = new pl.nauka.jarek.weather.model.forecast.List(dt, main, weather, clouds, wind, rain, sys, dtTxt);
            forecastList.add(list);
        }

        //Klasa city
        Object objectCity = (Object) response.get("city");
        City city = gson.fromJson(objectCity.toString(), City.class);

        return new ForecastCityWeather(cod, message, cnt, forecastList, city);
    }
}