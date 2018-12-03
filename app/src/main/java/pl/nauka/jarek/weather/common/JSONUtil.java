package pl.nauka.jarek.weather.common;

import android.content.Context;
import android.os.AsyncTask;
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

import pl.nauka.jarek.weather.data.CityWeatherData;
import pl.nauka.jarek.weather.model.CityWeather;
import pl.nauka.jarek.weather.model.Clouds;
import pl.nauka.jarek.weather.model.Coord;
import pl.nauka.jarek.weather.model.Main;
import pl.nauka.jarek.weather.model.Sys;
import pl.nauka.jarek.weather.model.Weather;
import pl.nauka.jarek.weather.model.Wind;

public class JSONUtil {

    private static Clouds clouds;
    private static Coord coord;
    private static Main main;
    private static Sys sys;
    private static Weather weather;
    private static Wind wind;
    private static String base;
    private static Integer visibility;
    private static Integer dt;
    private static Integer id;
    private static String name;
    private static Integer cod;
    private static CityWeather city;

    private static RequestQueue requestQueue;

    public static void getUrlData(String jsonUrl, final Context context){

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

                    base = (String) response.get("base");
                    visibility = (Integer) response.get("visibility");
                    dt = (Integer) response.get("dt");
                    id = (Integer) response.get("id");
                    name = (String) response.get("name");
                    cod = (Integer) response.get("cod");

                    Gson gson = new Gson();
                    clouds = gson.fromJson(jsonClouds.toString(), Clouds.class);
                    coord = gson.fromJson(jsonCoord.toString(), Coord.class);
                    main = gson.fromJson(jsonMain.toString(), Main.class);
                    sys = gson.fromJson(jsonSys.toString(), Sys.class);
                    weather = gson.fromJson(jsonWeather.toString(), Weather.class);
                    wind = gson.fromJson(jsonWind.toString(), Wind.class);

                    city = new CityWeather(coord, weather, base, main, visibility, wind, clouds, dt, sys, id, name, cod);

                    CityWeatherData.addCityWeather(city);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Błąd połączenia", Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jar);
    }
}
