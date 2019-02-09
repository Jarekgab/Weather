package pl.nauka.jarek.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;
import pl.nauka.jarek.weather.adapter.ForecastDayListAdapter;
import pl.nauka.jarek.weather.common.FormatDate;
import pl.nauka.jarek.weather.common.ListUtils;
import pl.nauka.jarek.weather.data.ForecastCityWeatherData;
import pl.nauka.jarek.weather.model.forecast.List;

public class FourDaysFragmentActivity extends Fragment {

    ListView lvForecastDay;
    private ForecastDayListAdapter dayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_four_days_fragment, container, false);

        lvForecastDay = view.findViewById(R.id.lv_forecast_day);


        //pobieranie danych pogodowych z listy na nastepne dni
        java.util.List<List> fourDaysList = new ArrayList<>();
        String dayOne = FormatDate.getDataOn(1) + " 15:00:00";
        String dayTwo = FormatDate.getDataOn(2) + " 15:00:00";
        String dayThree = FormatDate.getDataOn(3) + " 15:00:00";
        String dayFour = FormatDate.getDataOn(4) + " 15:00:00";

        for (int i = 0; i < ForecastCityWeatherData.getList().size(); i++) {
            if (ForecastCityWeatherData.getList().get(i).getDtTxt().equals(dayOne)){
                fourDaysList.add(ForecastCityWeatherData.getList().get(i));
            }
            else if (ForecastCityWeatherData.getList().get(i).getDtTxt().equals(dayTwo)){
                fourDaysList.add(ForecastCityWeatherData.getList().get(i));
            }
            else if (ForecastCityWeatherData.getList().get(i).getDtTxt().equals(dayThree)){
                fourDaysList.add(ForecastCityWeatherData.getList().get(i));
            }
            else if (ForecastCityWeatherData.getList().get(i).getDtTxt().equals(dayFour)){
                fourDaysList.add(ForecastCityWeatherData.getList().get(i));
            }
        }

        dayAdapter = new ForecastDayListAdapter(getContext(), fourDaysList);
        lvForecastDay.setAdapter(dayAdapter);

        ListUtils.setDynamicHeight(lvForecastDay);

        return view;
    }
}
