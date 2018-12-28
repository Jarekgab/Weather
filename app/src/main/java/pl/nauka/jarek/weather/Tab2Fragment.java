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
import pl.nauka.jarek.weather.model.forecast.ForecastCityWeather;


public class Tab2Fragment extends Fragment {

    Button forecast;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment, container, false);

        forecast = view.findViewById(R.id.b_forecast);

        forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForecastDataDownloader.getUrlData("http://api.openweathermap.org/data/2.5/forecast?q=Pozna%C5%84&units=metric&APPID=4b18af8ae81c911d81a965f0804b7845", getContext(), new ForecastDataDownloader.CityWeatherResponseCallback() {
                    @Override
                    public void onSuccess(ForecastCityWeather data) {
                        String hh = data.getList().get(0).getWeather().getDescription();
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
