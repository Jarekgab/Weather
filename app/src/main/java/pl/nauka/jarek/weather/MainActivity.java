package pl.nauka.jarek.weather;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import pl.nauka.jarek.weather.adapter.WeatherListAdapter;
import pl.nauka.jarek.weather.common.DataDownloader;
import pl.nauka.jarek.weather.common.UrlGenerator;
import pl.nauka.jarek.weather.data.CityWeatherData;
import pl.nauka.jarek.weather.model.CityWeather;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Context context;

    Button jsonDataButton;
    ProgressBar progressBar;
    ListView lvList;

    EditText etCitySearch;
    public String url;
    public String city;
    public List<String> cityNameList;
    private WeatherListAdapter adapter;


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

        context = this;

        jsonDataButton = findViewById(R.id.jsonDataButton);

        etCitySearch = findViewById(R.id.et_city_search);
        lvList = findViewById(R.id.lv_list);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        cityNameList = new ArrayList<>();

        jsonDataButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.bringToFront();
                progressBar.setVisibility(View.VISIBLE);

                city = etCitySearch.getText().toString();
                cityNameList.add(city);                //dodanie do listy z szukanymi nazwami miast

                url = UrlGenerator.getUrl(city);

                DataDownloader.getUrlData(url, context, new DataDownloader.CityWeatherResponseCallback() {
                    @Override
                    public void onSuccess(CityWeather data) {
                        CityWeatherData.addCityWeather(data);
                        adapter = new WeatherListAdapter(context, CityWeatherData.getList());
                        lvList.setAdapter(adapter);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception exception) {
                        exception.printStackTrace();
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(context, "Błąd pobierania danych", Toast.LENGTH_LONG);
                    }
                });
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
        if (id == R.id.action_refresh) {

//            city = null;
//            CityWeatherData.clearCityWeather();
//
//            for (int i = 0; i <= cityNameList.size()-1; i++) {
//
//
////                try {
////                    Thread.sleep(500);
//////                    progressBar.setVisibility(View.VISIBLE);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//                city = cityNameList.get(i);
//                url = UrlGenerator.getUrl(city);
//                new MyAsyncTask().execute(url);      //Czekanie na pobranie i zapisanie danych
//            }
            return true;
        }

        if (id == R.id.action_add) {
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

