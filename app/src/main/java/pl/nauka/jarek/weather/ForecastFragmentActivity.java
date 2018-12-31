package pl.nauka.jarek.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.nauka.jarek.weather.adapter.ForecastDayListAdapter;
import pl.nauka.jarek.weather.adapter.ForecastHourListAdapter;
import pl.nauka.jarek.weather.common.Connectivity;
import pl.nauka.jarek.weather.common.ForecastDataDownloader;
import pl.nauka.jarek.weather.common.ListUtils;
import pl.nauka.jarek.weather.common.UrlGenerator;
import pl.nauka.jarek.weather.data.CityWeatherData;
import pl.nauka.jarek.weather.model.forecast.ForecastCityWeather;
import pl.nauka.jarek.weather.model.forecast.List;

import static pl.nauka.jarek.weather.CityWeatherActivity.listPosition;

public class ForecastFragmentActivity extends Fragment {

    java.util.List<List> list = new ArrayList<>();
    ListView lvForecastHour;
    ListView lvForecastDay;

    private ForecastHourListAdapter hourAdapter;
    private ForecastDayListAdapter dayAdapter;

    //TODO zrobic zapisywanie danych

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forecast_fragment, container, false);

        lvForecastHour = view.findViewById(R.id.lv_forecast_hour);
        lvForecastDay = view.findViewById(R.id.lv_forecast_day);

        //Pobieranie nazwy miasta wybranej z listy
        String cityName = CityWeatherData.getList().get(listPosition).getName();
        final String url = UrlGenerator.getForecastUrl(cityName);

        if (Connectivity.isConnected(getContext())) {
            ForecastDataDownloader.getUrlData(url, getContext(), new ForecastDataDownloader.CityWeatherResponseCallback() {
                @Override
                public void onSuccess(ForecastCityWeather data) {
                    list = data.getList();

                    hourAdapter = new ForecastHourListAdapter(getContext(), list);
                    lvForecastHour.setAdapter(hourAdapter);

                    dayAdapter = new ForecastDayListAdapter(getContext(), list);
                    lvForecastDay.setAdapter(dayAdapter);

                    ListUtils.setDynamicHeight(lvForecastHour);
                    ListUtils.setDynamicHeight(lvForecastDay);

                    //TODO Zapisywanie pobranego pliku
                }

                @Override
                public void onError(Exception exception) {
                    exception.printStackTrace();
                }
            });
        } else if (!Connectivity.isConnected(getContext())) {
            Toast.makeText(getContext(), "Brak połączenia", Toast.LENGTH_LONG).show();

            //TODO Pobieranie zapisanego pliku
        }
        return view;
    }
}
