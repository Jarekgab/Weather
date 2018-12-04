package pl.nauka.jarek.weather;

import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.List;
import butterknife.ButterKnife;
import pl.nauka.jarek.weather.common.JSONUtil;
import pl.nauka.jarek.weather.data.CityWeatherData;
import pl.nauka.jarek.weather.model.CityWeather;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button jsonDataButton;

    Context context;
    private List<CityWeather> list;
    private TextView resultTextView;
    private ProgressBar progressBar;


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

        Button jsonDataButton = findViewById(R.id.jsonDataButton);
        resultTextView = findViewById(R.id.resultTextView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        jsonDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultTextView.setText("");
                progressBar.setVisibility(View.VISIBLE);
                new MyAsyncTask().execute();      //Czekanie na pobranie i zapisanie danych
                }

        });
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, List<CityWeather>> {


        @Override
        protected List<CityWeather> doInBackground(Void... params) {
            JSONUtil.getUrlData("http://api.openweathermap.org/data/2.5/weather?q=Pozna%C5%84&units=metric&APPID=4b18af8ae81c911d81a965f0804b7845", context);

            try {
                Thread.sleep(500);

                //TODO Watek jest opozniany o 0,5s.
                // Zmienic, zeby czekal za dodaniem pogody do listy (czas oczekiwania dynamiczny)

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return CityWeatherData.getList();
        }

        @Override
        protected void onPostExecute(List<CityWeather> result) {

            super.onPostExecute(result);
            progressBar.setVisibility(View.INVISIBLE);

            if (result.size() > 0) {

                resultTextView.setText(result.get(0).getName() + "   " + result.get(0).getMain().getTemp() + " °C");
            }
        }
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

