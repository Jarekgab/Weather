package pl.nauka.jarek.weather;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;

import pl.nauka.jarek.weather.adapter.SectionsPageAdapter;
import pl.nauka.jarek.weather.data.CurrentCityWeatherData;

public class CityWeatherActivity extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    static public int listPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        obtainExtras();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(CurrentCityWeatherData.getList().get(listPosition).getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPages(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void obtainExtras() {
        listPosition = (int) getIntent().getExtras().getSerializable(MainActivity.LIST_WEATHER_POSITION);
    }

    private void setupViewPages(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new DetailFragmentActivity(), String.valueOf(getResources().getText(R.string.today)));
        adapter.addFragment(new TomorrowFragmentActivity(), String.valueOf(getResources().getText(R.string.tomorrow)));
        adapter.addFragment(new FourDaysFragmentActivity(), String.valueOf(getResources().getText(R.string.four_days)));
        viewPager.setAdapter(adapter);
    }

}
