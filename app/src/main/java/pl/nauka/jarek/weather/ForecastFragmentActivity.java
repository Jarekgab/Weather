package pl.nauka.jarek.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pl.nauka.jarek.weather.adapter.ForecastDayListAdapter;
import pl.nauka.jarek.weather.adapter.WeatherListAdapter;
import pl.nauka.jarek.weather.common.ForecastDataDownloader;
import pl.nauka.jarek.weather.common.UrlGenerator;
import pl.nauka.jarek.weather.data.CityWeatherData;
import pl.nauka.jarek.weather.model.forecast.ForecastCityWeather;
import pl.nauka.jarek.weather.model.forecast.List;

import static pl.nauka.jarek.weather.CityWeatherActivity.listPosition;

public class ForecastFragmentActivity extends Fragment {

    java.util.List<List> list = new ArrayList<>();
    ListView lvForecastDay;
    ListView lvForecastDay2;

    private ForecastDayListAdapter dayAdapter;
    private ForecastDayListAdapter dayAdapter2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forecast_fragment, container, false);

        lvForecastDay = view.findViewById(R.id.lv_forecast_day);
        lvForecastDay2 = view.findViewById(R.id.lv_forecast_day2);

        //Pobieranie nazwy miasta wybranej z listy
        String cityName = CityWeatherData.getList().get(listPosition).getName();
        final String url = UrlGenerator.getForecastUrl(cityName);

                ForecastDataDownloader.getUrlData(url, getContext(), new ForecastDataDownloader.CityWeatherResponseCallback() {
                    @Override
                    public void onSuccess(ForecastCityWeather data) {

                        list = data.getList();

                        dayAdapter = new ForecastDayListAdapter(getContext(), list);
                        lvForecastDay.setAdapter(dayAdapter);

                        dayAdapter2 = new ForecastDayListAdapter(getContext(), list);
                        lvForecastDay2.setAdapter(dayAdapter2);

                        ListUtils.setDynamicHeight(lvForecastDay);
                        ListUtils.setDynamicHeight(lvForecastDay2);





//                        Toast.makeText(getContext(), "Miasto : " + data.getCity().getName(), Toast.LENGTH_LONG).show();
//                        Toast.makeText(getContext(), "Opis: " + data.getList().get(0).getWeather().getDescription(), Toast.LENGTH_LONG).show();
//                        String hh = data.getList().get(0).getWeather().getDescription();
                    }

                    @Override
                    public void onError(Exception exception) {
                        Toast.makeText(getContext(), "NIE DZIAŁA", Toast.LENGTH_LONG).show();
                    }
                });

        return view;
    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
}}
