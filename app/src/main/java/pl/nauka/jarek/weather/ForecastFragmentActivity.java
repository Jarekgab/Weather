package pl.nauka.jarek.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import pl.nauka.jarek.weather.common.ForecastDataDownloader;
import pl.nauka.jarek.weather.common.UrlGenerator;
import pl.nauka.jarek.weather.data.CityWeatherData;
import pl.nauka.jarek.weather.model.current.CityWeather;
import pl.nauka.jarek.weather.model.forecast.ForecastCityWeather;

import static pl.nauka.jarek.weather.CityWeatherActivity.listPosition;


public class ForecastFragmentActivity extends Fragment {

    Button forecast;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment, container, false);

        forecast = view.findViewById(R.id.b_forecast);

        //Pobieranie nazwy miasta wybranej z listy
        String cityName = CityWeatherData.getList().get(listPosition).getName();
        final String url = UrlGenerator.getForecastUrl(cityName);

        forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForecastDataDownloader.getUrlData(url, getContext(), new ForecastDataDownloader.CityWeatherResponseCallback() {
                    @Override
                    public void onSuccess(ForecastCityWeather data) {
                        Toast.makeText(getContext(), "Miasto : " + data.getCity().getName(), Toast.LENGTH_LONG).show();
                        Toast.makeText(getContext(), "Opis: " + data.getList().get(0).getWeather().getDescription(), Toast.LENGTH_LONG).show();
//                        String hh = data.getList().get(0).getWeather().getDescription();
                    }

                    @Override
                    public void onError(Exception exception) {
                        Toast.makeText(getContext(), "NIE DZIAŁA", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        return view;
    }
}
