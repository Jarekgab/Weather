package pl.nauka.jarek.weather.common;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.nauka.jarek.weather.model.CityWeather;
import pl.nauka.jarek.weather.model.Clouds;
import pl.nauka.jarek.weather.model.Coord;
import pl.nauka.jarek.weather.model.Main;
import pl.nauka.jarek.weather.model.Sys;
import pl.nauka.jarek.weather.model.Weather;
import pl.nauka.jarek.weather.model.Wind;

public class DataDownloader {

    private static RequestQueue requestQueue;

    public interface CityWeatherResponseCallback {
        void onSuccess(CityWeather data);
        void onError(Exception exception);
    }

    public static void getUrlData(String jsonUrl, final Context context, final CityWeatherResponseCallback responseCallback){

        requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jar = new JsonObjectRequest(jsonUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonClouds = (JSONObject) response.getJSONObject("clouds");
                    JSONObject jsonCoord = (JSONObject) response.getJSONObject("coord");
                    JSONObject jsonMain = (JSONObject) response.getJSONObject("main");
                    JSONObject jsonSys = (JSONObject) response.getJSONObject("sys");

                    JSONArray jsonWeatherArray = (JSONArray) response.getJSONArray("weather");
                    JSONObject jsonWeather = (JSONObject) jsonWeatherArray.get(0);

                    JSONObject jsonWind = (JSONObject) response.getJSONObject("wind");

                    String base = (String) response.get("base");
                    Integer visibility = (Integer) response.get("visibility");
                    Integer dt = (Integer) response.get("dt");
                    Integer id = (Integer) response.get("id");
                    String name = (String) response.get("name");
                    Integer cod = (Integer) response.get("cod");

                    Gson gson = new Gson();
                    Clouds clouds = gson.fromJson(jsonClouds.toString(), Clouds.class);
                    Coord coord = gson.fromJson(jsonCoord.toString(), Coord.class);
                    Main main = gson.fromJson(jsonMain.toString(), Main.class);
                    Sys sys = gson.fromJson(jsonSys.toString(), Sys.class);
                    Weather weather = gson.fromJson(jsonWeather.toString(), Weather.class);
                    Wind wind = gson.fromJson(jsonWind.toString(), Wind.class);

                    CityWeather city = new CityWeather(coord, weather, base, main, visibility, wind, clouds, dt, sys, id, name, cod);

                    responseCallback.onSuccess(city);

                } catch (JSONException e) {
                    Toast.makeText(context, "Błąd pobierania danych", Toast.LENGTH_LONG).show();
                    responseCallback.onError(e);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Błędna nazwa", Toast.LENGTH_LONG).show();
                        responseCallback.onError(error);
                    }
                }
        );
        requestQueue.add(jar);
    }
}
