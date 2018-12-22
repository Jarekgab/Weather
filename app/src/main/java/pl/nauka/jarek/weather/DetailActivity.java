package pl.nauka.jarek.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import pl.nauka.jarek.weather.data.CityWeatherData;
import pl.nauka.jarek.weather.data.WeatherIcon;
import pl.nauka.jarek.weather.model.CityWeather;

public class DetailActivity extends Fragment {

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
            TextView tvVisibility;
            TextView tvWindSpeed;
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
        holder.tvVisibility = view.findViewById(R.id.tv_visibility);
        holder.tvWindSpeed = view.findViewById(R.id.tv_wind_speed);

        CityWeather weather = CityWeatherData.getList().get(CityWeatherActivity.listPosition);

        String icon = weather.getWeather().getIcon();  //TODO zrobić mape wywolan
        int weatherIconFromResource = WeatherIcon.getWeatherIconFromResource(icon);
        holder.ivWeatherIcon.setImageResource(weatherIconFromResource);

        //primary_weather_info.xml
        holder.tvDate.setText(getCurrentlyDate());
        holder.tvTemperature.setText(String.valueOf(weather.getMain().getTemp()) + "°C");
        holder.tvHightTemperature.setText("Dzień " + String.valueOf(weather.getMain().getTempMax()) + "°C");
        holder.tvLowTemperature.setText("Noc " + String.valueOf(weather.getMain().getTempMin()) + "°C");
        holder.tvCityWeather.setText(weather.getWeather().getDescription());

        //secondary_weather_info.xml
        holder.tvPressure.setText(String.valueOf(weather.getMain().getPressure()) + " hPa");
        holder.tvCloudsAll.setText(String.valueOf(weather.getClouds().getAll()) + " %");
        holder.tvHumidity.setText(String.valueOf(weather.getMain().getHumidity()) + " %");
        holder.tvVisibility.setText(String.valueOf(weather.getVisibility()) +  " m");
        holder.tvWindSpeed.setText(String.valueOf(weather.getWind().getSpeed()) + " m/s");

        return view;
    }

    private String getCurrentlyDate() {
        Calendar calendar = Calendar.getInstance();

        int monthInt = calendar.get(Calendar.MONTH) + 1;
        String monthStringPL = "";

        if (monthInt == 1){monthStringPL = "Styczeń";}
        if (monthInt == 2){monthStringPL = "Luty";}
        if (monthInt == 3){monthStringPL = "Marzec";}
        if (monthInt == 4){monthStringPL = "Kwiecień";}
        if (monthInt == 5){monthStringPL = "Maj";}
        if (monthInt == 6){monthStringPL = "Czerwiec";}
        if (monthInt == 7){monthStringPL = "Lipiec";}
        if (monthInt == 8){monthStringPL = "Sierpień";}
        if (monthInt == 9){monthStringPL = "Wrzesień";}
        if (monthInt == 10){monthStringPL = "Październik";}
        if (monthInt == 11){monthStringPL = "Listopad";}
        if (monthInt == 12){monthStringPL = "Grudzień";}

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return monthStringPL + " " + day + ", " + hour + ":" + minute;
    }
}
