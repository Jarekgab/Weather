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
import android.widget.AdapterView;
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
import ru.whalemare.sheetmenu.SheetMenu;

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
        cityNameList = new ArrayList<>();

        jsonDataButton = findViewById(R.id.jsonDataButton);
        etCitySearch = findViewById(R.id.et_city_search);
        lvList = findViewById(R.id.lv_list);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

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
                        CityWeatherData.addCityWeather(data);   //dodawanie do listy
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

        lvList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showBottomMenu(position);
                return false;
            }
        });
    }

    private void showBottomMenu(final int position) {
        SheetMenu.with(context)
                .setTitle("Wybierz opcje:")
                .setMenu(R.menu.bottom_menu)
                .setClick(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.action_ref) {
                            city = null;
                            url = null;

                            progressBar.setVisibility(View.VISIBLE);
                            city = cityNameList.get(position);
                            url = UrlGenerator.getUrl(city);

                            refreshCWD(position);

                        } else if (item.getItemId() == R.id.action_del) {
                            CityWeatherData.deleteCityWeather(position);
                            cityNameList.remove(position);
                            adapter = new WeatherListAdapter(context, CityWeatherData.getList());
                            lvList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                        return true;
                    }
                }).show();
    }

    private void refreshCWD(final int index) {
        DataDownloader.getUrlData(url, context, new DataDownloader.CityWeatherResponseCallback() {
            @Override
            public void onSuccess(CityWeather data) {
                CityWeatherData.setCityWeather(index, data);        //nadpisywanie listy
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

        int id = item.getItemId();

        if (id == R.id.action_refresh) {

            city = null;
            url = null;

            for (int i = 0; i < cityNameList.size(); i++) {

                progressBar.setVisibility(View.VISIBLE);
                city = cityNameList.get(i);
                url = UrlGenerator.getUrl(city);

                final int index = i;

                refreshCWD(index);
            }

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

