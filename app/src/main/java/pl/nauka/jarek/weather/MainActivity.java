package pl.nauka.jarek.weather;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import pl.nauka.jarek.weather.model.CityWeather;
import pl.nauka.jarek.weather.model.Clouds;
import pl.nauka.jarek.weather.model.Coord;
import pl.nauka.jarek.weather.model.Main;
import pl.nauka.jarek.weather.model.Sys;
import pl.nauka.jarek.weather.model.Weather;
import pl.nauka.jarek.weather.model.Wind;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button jsonDataButton;

    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        requestQueue = Volley.newRequestQueue(this);                                   //kolejka do obsługi sieci, zamiast wielowatkosci


        final String jsonUrl = "http://api.openweathermap.org/data/2.5/weather?q=Pozna%C5%84&units=metric&APPID=4b18af8ae81c911d81a965f0804b7845";
        final List<CityWeather> cityWeatherList = new ArrayList<>();


        Button jsonDataButton = findViewById(R.id.jsonDataButton);
        final TextView resultTextView = findViewById(R.id.resultTextView);

        jsonDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultTextView.setText("");
                cityWeatherList.clear();
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

                                cityWeatherList.add(city);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        for (int i = 0; i < cityWeatherList.size(); i++) {
                            resultTextView.append(cityWeatherList.get(i).getName() + "   ");
                            resultTextView.append(cityWeatherList.get(i).getMain().getTemp() + " °C");
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {            //w przypadku błędu
                                resultTextView.setText("BŁĄD");
                            }
                        }
                );
                requestQueue.add(jar);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
