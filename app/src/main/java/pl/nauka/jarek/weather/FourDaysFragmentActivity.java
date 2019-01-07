package pl.nauka.jarek.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import pl.nauka.jarek.weather.adapter.ForecastDayListAdapter;
import pl.nauka.jarek.weather.common.ListUtils;
import pl.nauka.jarek.weather.data.ForecastCityWeatherData;

public class FourDaysFragmentActivity extends Fragment {

    ListView lvForecastDay;
    private ForecastDayListAdapter dayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_four_days_fragment, container, false);

        lvForecastDay = view.findViewById(R.id.lv_forecast_day);

        dayAdapter = new ForecastDayListAdapter(getContext(), ForecastCityWeatherData.getList());
        lvForecastDay.setAdapter(dayAdapter);

        ListUtils.setDynamicHeight(lvForecastDay);

        return view;
    }
}
