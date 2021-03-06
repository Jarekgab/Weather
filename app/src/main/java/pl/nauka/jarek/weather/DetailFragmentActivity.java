package pl.nauka.jarek.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import pl.nauka.jarek.weather.adapter.ForecastHourListAdapter;
import pl.nauka.jarek.weather.common.FormatDate;
import pl.nauka.jarek.weather.common.ListUtils;
import pl.nauka.jarek.weather.common.StringFromResourcesByName;
import pl.nauka.jarek.weather.data.CurrentCityWeatherData;
import pl.nauka.jarek.weather.data.ForecastCityWeatherData;
import pl.nauka.jarek.weather.data.WeatherIcon;
import pl.nauka.jarek.weather.model.current.CityWeather;
import pl.nauka.jarek.weather.model.forecast.List;

public class DetailFragmentActivity extends Fragment {

    private ForecastHourListAdapter hourAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_detail_fragment, container, false);

        class ViewHolder {

            //primary_weather_info.xml
            TextView tvDate;
            TextView tvTemperature;
            TextView tvHightTemperature;
            TextView tvLowTemperature;
            TextView tvCityWeather;
            ImageView ivWeatherIcon;

            //secondary_weather_info.xml
            TextView tvPressure;
            TextView tvCloudsAll;
            TextView tvHumidity;
            TextView tvWindSpeed;

            //list
            ListView lvForecastHour;
        }

        ViewHolder holder = null;
        holder = new ViewHolder();

        //primary_weather_info.xml
        holder.tvDate = view.findViewById(R.id.tv_date);
        holder.tvTemperature = view.findViewById(R.id.tv_temperature);
        holder.tvHightTemperature = view.findViewById(R.id.tv_hight_temperature);
        holder.tvLowTemperature = view.findViewById(R.id.tv_low_temperature);
        holder.tvCityWeather = view.findViewById(R.id.tv_city_weather);
        holder.ivWeatherIcon = view.findViewById(R.id.iv_weather_icon);

        //secondary_weather_info.xml
        holder.tvPressure = view.findViewById(R.id.tv_pressure);
        holder.tvCloudsAll = view.findViewById(R.id.tv_clouds_all);
        holder.tvHumidity = view.findViewById(R.id.tv_humidity);
        holder.tvWindSpeed = view.findViewById(R.id.tv_wind_speed);

        //list
        holder.lvForecastHour = view.findViewById(R.id.lv_forecast_hour);

        java.util.List<List> hourList = new ArrayList<>();
        hourList.add(ForecastCityWeatherData.getList().get(0));
        hourList.add(ForecastCityWeatherData.getList().get(1));
        hourList.add(ForecastCityWeatherData.getList().get(2));
        hourList.add(ForecastCityWeatherData.getList().get(3));
        hourList.add(ForecastCityWeatherData.getList().get(4));
        hourList.add(ForecastCityWeatherData.getList().get(5));
        hourList.add(ForecastCityWeatherData.getList().get(6));
        hourList.add(ForecastCityWeatherData.getList().get(7));
        hourList.add(ForecastCityWeatherData.getList().get(8));

        hourAdapter = new ForecastHourListAdapter(getContext(), hourList);
        holder.lvForecastHour.setAdapter(hourAdapter);
        ListUtils.setDynamicHeight(holder.lvForecastHour);

        //Pobieranie pogody dla wybranego miasta z listy
        CityWeather weather = CurrentCityWeatherData.getList().get(CityWeatherActivity.listPosition);

        //Pobieranie wartości temperatury w nocy
        String tomorrowDateInNight = FormatDate.getDataOn(1) + " 03:00:00";
        List weatherInNight = null;

        for (int i = 0; i < ForecastCityWeatherData.getList().size(); i++) {
            if (ForecastCityWeatherData.getList().get(i).getDtTxt().equals(tomorrowDateInNight) && weatherInNight == null){
                weatherInNight = ForecastCityWeatherData.getList().get(i);
            }
        }

        String icon = weather.getWeather().getIcon();
        int weatherIconFromResource = WeatherIcon.getWeatherIconFromResource(icon);
        holder.ivWeatherIcon.setImageResource(weatherIconFromResource);

        //primary_weather_info.xml

        //przypisanie aktualnej i sformatowanej daty
        holder.tvDate.setText(FormatDate.getCurrentlyDate(getContext()));

        //zaokraglenie temp
        short temp = (short) Math.round(weather.getMain().getTemp());
        short tempHight = (short) Math.round(weather.getMain().getTempMax());
        short tempLow = (short) Math.round(weatherInNight.getMain().getTempMin());

        holder.tvTemperature.setText(String.valueOf(temp) + "°C");
        holder.tvHightTemperature.setText(getResources().getText(R.string.day) + " " + String.valueOf(tempHight) + "°C");
        holder.tvLowTemperature.setText(getResources().getText(R.string.night) + " " + String.valueOf(tempLow) + "°C");

        String description = weather.getWeather().getDescription();
        //zmiana "description" na odpowiednia postac stringa aby moc pobrac z Resources, dalej tłumaczenie
        String translatedDescription = StringFromResourcesByName.getStringFromResourcesByName(description, getContext());
        holder.tvCityWeather.setText(translatedDescription);

        //secondary_weather_info.xml
        holder.tvPressure.setText(String.valueOf(weather.getMain().getPressure()) + " hPa");
        holder.tvCloudsAll.setText(String.valueOf(weather.getClouds().getAll()) + " %");
        holder.tvHumidity.setText(String.valueOf(weather.getMain().getHumidity()) + " %");
        holder.tvWindSpeed.setText(String.valueOf(weather.getWind().getSpeed()) + " m/s");

        return view;
    }
}
