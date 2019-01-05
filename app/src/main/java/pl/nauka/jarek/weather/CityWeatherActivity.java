package pl.nauka.jarek.weather;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.widget.Toast;
import java.util.ArrayList;
import pl.nauka.jarek.weather.adapter.SectionsPageAdapter;
import pl.nauka.jarek.weather.common.Connectivity;
import pl.nauka.jarek.weather.common.ForecastDataDownloader;
import pl.nauka.jarek.weather.common.UrlGenerator;
import pl.nauka.jarek.weather.data.CityWeatherData;
import pl.nauka.jarek.weather.model.forecast.ForecastCityWeather;
import pl.nauka.jarek.weather.model.forecast.List;

public class CityWeatherActivity extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    static public int listPosition;
    static public java.util.List<List> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        obtainExtras();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(CityWeatherData.getList().get(listPosition).getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Pobieranie nazwy miasta wybranej z listy
        String cityName = CityWeatherData.getList().get(listPosition).getName();
        final String url = UrlGenerator.getForecastUrl(cityName);

        if (Connectivity.isConnected(this)) {
            ForecastDataDownloader.getUrlData(url, this, new ForecastDataDownloader.CityWeatherResponseCallback() {
                @Override
                public void onSuccess(ForecastCityWeather data) {
                    list = data.getList();

                    mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
                    mViewPager = (ViewPager) findViewById(R.id.container);
                    setupViewPages(mViewPager);

                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(mViewPager);

                    //TODO Zapisywanie pobranego pliku
                }

                @Override
                public void onError(Exception exception) {
                    exception.printStackTrace();
                }
            });
        } else if (!Connectivity.isConnected(this)) {
            Toast.makeText(this, "Brak połączenia", Toast.LENGTH_LONG).show();

            //TODO Pobieranie zapisanego pliku
        }
    }

    private void obtainExtras() {
        listPosition = (int) getIntent().getExtras().getSerializable(MainActivity.LIST_WEATHER_POSITION);
    }

    private void setupViewPages(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new DetailFragmentActivity(), "DZISIAJ");
        adapter.addFragment(new TomorrowFragmentActivity(), "JUTRO");
        adapter.addFragment(new FourDaysFragmentActivity(), "4 DNI");
        viewPager.setAdapter(adapter);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_city_weather, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
