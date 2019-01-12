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

import pl.nauka.jarek.weather.model.current.CityWeather;
import pl.nauka.jarek.weather.model.current.Clouds;
import pl.nauka.jarek.weather.model.current.Coord;
import pl.nauka.jarek.weather.model.current.Main;
import pl.nauka.jarek.weather.model.current.Sys;
import pl.nauka.jarek.weather.model.current.Weather;
import pl.nauka.jarek.weather.model.current.Wind;

public class CurrentDataDownloader {

    private static RequestQueue requestQueue;

    public interface CityWeatherResponseCallback {
        void onSuccess(CityWeather data);

        void onError(Exception exception);
    }

    public static void getUrlData(String jsonUrl, final Context context, final CurrentDataDownloader.CityWeatherResponseCallback responseCallback){
        requestQueue = Volley.newRequestQueue(context);

        CurrentRequest jar = new CurrentRequest(jsonUrl, null, new Response.Listener<CityWeather>() {
            @Override
            public void onResponse(CityWeather response) {
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


class CurrentRequest extends JsonRequest<CityWeather> {

    public CurrentRequest(String url, @Nullable String requestBody, Response.Listener<CityWeather> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.GET, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<CityWeather> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(
                            response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

            JSONObject jsonObject = new JSONObject(jsonString);
            CityWeather cityWeather = parseJsonObject(jsonObject);

            return Response.success(cityWeather, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @NonNull
    private CityWeather parseJsonObject(JSONObject response) throws JSONException {

        Gson gson = new Gson();
        JSONObject jsonCoord = (JSONObject) response.getJSONObject("coord");
        Coord coord = gson.fromJson(jsonCoord.toString(), Coord.class);

        JSONArray jsonWeatherArray = (JSONArray) response.getJSONArray("weather");
        JSONObject jsonWeather = (JSONObject) jsonWeatherArray.get(0);
        Weather weather = gson.fromJson(jsonWeather.toString(), Weather.class);

        String base = (String) response.get("base");

        Main main;
        try {
            JSONObject jsonMain = (JSONObject) response.getJSONObject("main");
            main = gson.fromJson(jsonMain.toString(), Main.class);

        } catch (JSONException e) {
            main = new Main();
        }

        Integer visibility;
        try {
            visibility = (Integer) response.get("visibility");
        } catch (JSONException e) {
            visibility = 0;
        }

        JSONObject jsonWind = (JSONObject) response.getJSONObject("wind");
        Wind wind = gson.fromJson(jsonWind.toString(), Wind.class);

        JSONObject jsonClouds = (JSONObject) response.getJSONObject("clouds");
        Clouds clouds = gson.fromJson(jsonClouds.toString(), Clouds.class);

        Integer dt = (Integer) response.get("dt");

        Sys sys;
        try {
            JSONObject jsonSys = (JSONObject) response.getJSONObject("sys");
            sys = gson.fromJson(jsonSys.toString(), Sys.class);

        } catch (JSONException e) {
            sys = new Sys();
        }

        Integer id = (Integer) response.get("id");
        String name = (String) response.get("name");
        Integer cod = (Integer) response.get("cod");


        CityWeather city = new CityWeather(coord, weather, base, main, visibility, wind, clouds, dt, sys, id, name, cod);

        return new CityWeather(coord, weather, base, main, visibility, wind, clouds, dt, sys, id, name, cod);
    }
}