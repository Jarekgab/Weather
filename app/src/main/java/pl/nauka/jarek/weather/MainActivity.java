package pl.nauka.jarek.weather;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import pl.nauka.jarek.weather.adapter.WeatherListAdapter;
import pl.nauka.jarek.weather.common.Connectivity;
import pl.nauka.jarek.weather.common.DataDownloader;
import pl.nauka.jarek.weather.common.SharedPreferencesSaver;
import pl.nauka.jarek.weather.common.UrlGenerator;
import pl.nauka.jarek.weather.data.CityWeatherData;
import pl.nauka.jarek.weather.model.CityWeather;
import ru.whalemare.sheetmenu.SheetMenu;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Context context;

    ListView lvList;
    SwipeRefreshLayout swipeLayout;

    EditText etCitySearch;
    public String url;
    public String city;
    public List<String> cityNameList;
    public List<CityWeather> newList;
    private WeatherListAdapter adapter;

    //TODO dodać zapisywanie danych


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

        etCitySearch = findViewById(R.id.et_add_city);
        lvList = findViewById(R.id.lv_list);
        swipeLayout = findViewById(R.id.swipe_container);


        //Przywracanie zapisanych list

        newList = new ArrayList<>();
        newList = SharedPreferencesSaver.loadList(getPreferences(MODE_PRIVATE));
        List<String> newCityNameList = SharedPreferencesSaver.loadCityNameList(getPreferences(MODE_PRIVATE));

        if (newList != null) {
            CityWeatherData.changeCityWeather(newList);
        }

        if (newCityNameList != null) {
            cityNameList = newCityNameList;
        }

        adapter = new WeatherListAdapter(context, CityWeatherData.getList());
        lvList.setAdapter(adapter);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, CityWeatherActivity.class);
                startActivity(intent);
            }
        });


        lvList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showBottomMenu(position);
                return true;
            }
        });

            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    if (Connectivity.isConnected(context)){
                        city = null;
                        url = null;

                        for (int i = 0; i < cityNameList.size(); i++) {
                            swipeLayout.setRefreshing(true);
                            city = cityNameList.get(i);
                            url = UrlGenerator.getUrl(city);

                            final int index = i;
                            refreshCWD(index);
                        }

                    }else if (!Connectivity.isConnected(context)){
                        Toast.makeText(context, "Brak połączenia", Toast.LENGTH_LONG).show();
                    }

                    if (CityWeatherData.getList().isEmpty()){
                        setRefreshingDelaySwipeLayout(0);
                    }else if (!CityWeatherData.getList().isEmpty()){
                        swipeLayout.setRefreshing(false);
                    }
                }
            });

            // Kolory animacji

            swipeLayout.setColorSchemeColors(
                    //Inne kolory
//                getResources().getColor(android.R.color.holo_blue_bright),
//                getResources().getColor(android.R.color.holo_green_light),
//                getResources().getColor(android.R.color.holo_orange_light),
                    getResources().getColor(android.R.color.holo_red_light)
            );
        }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferencesSaver.saveTo(CityWeatherData.getList(), cityNameList, getPreferences(MODE_PRIVATE));       //Zapisywanie list
    }

    private void setRefreshingDelaySwipeLayout(int delay) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Stop animacji po czasie delay
                swipeLayout.setRefreshing(false);
            }
        }, delay); // Opóźnienie (delay) w ms
    }


    private void showBottomMenu(final int position) {
        SheetMenu.with(context)
                .setTitle("Wybierz opcje:")
                .setMenu(R.menu.bottom_menu)
                .setClick(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.action_ref) {

                            if (Connectivity.isConnected(context)){
                                city = null;
                                url = null;

                                swipeLayout.setRefreshing(true);
                                city = cityNameList.get(position);
                                url = UrlGenerator.getUrl(city);

                                refreshCWD(position);

                            }else if(!Connectivity.isConnected(context)){
                                Toast.makeText(context, "Brak połączenia", Toast.LENGTH_LONG).show();
                            }

                        } else if (item.getItemId() == R.id.action_del) {
                            swipeLayout.setRefreshing(true);
                            CityWeatherData.deleteCityWeather(position);
                            cityNameList.remove(position);
                            adapter = new WeatherListAdapter(context, CityWeatherData.getList());
                            lvList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            setRefreshingDelaySwipeLayout(1000);
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
                setRefreshingDelaySwipeLayout(1000);
            }

            @Override
            public void onError(Exception exception) {
                exception.printStackTrace();
                setRefreshingDelaySwipeLayout(1000);
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

        if (id == R.id.action_add) {

            showBottomDialog();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_item);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        final EditText etAddCity = (EditText) dialog.findViewById(R.id.et_add_city);
        Button bAddCity = dialog.findViewById(R.id.b_add_city);

        bAddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                city = etAddCity.getText().toString();

                if (!cityNameList.contains(city) && Connectivity.isConnected(context)){          //Nie dodawaj tych samych miast i gdy nie ma internetu
                    swipeLayout.setRefreshing(true);
                    url = UrlGenerator.getUrl(city);
                    dialog.dismiss();

                    DataDownloader.getUrlData(url, context, new DataDownloader.CityWeatherResponseCallback() {
                        @Override
                        public void onSuccess(CityWeather data) {
                            cityNameList.add(city);                //dodanie do listy z szukanymi nazwami miast
                            CityWeatherData.addCityWeather(data);   //dodawanie do listy
                            adapter = new WeatherListAdapter(context, CityWeatherData.getList());
                            lvList.setAdapter(adapter);
                            setRefreshingDelaySwipeLayout(500);
                        }

                        @Override
                        public void onError(Exception exception) {
                            exception.printStackTrace();
                            setRefreshingDelaySwipeLayout(500);
                        }
                    });

                }else if (cityNameList.contains(city) && Connectivity.isConnected(context)){
                    dialog.dismiss();
                    Toast.makeText(context, "Miasto o podanej nazwie jest już na liście", Toast.LENGTH_LONG).show();

                }else if (!Connectivity.isConnected(context)){
                    dialog.dismiss();
                    Toast.makeText(context, "Brak połączenia", Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
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

