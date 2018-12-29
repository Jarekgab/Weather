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

import pl.nauka.jarek.weather.common.StringFromResourcesByName;
import pl.nauka.jarek.weather.data.CityWeatherData;
import pl.nauka.jarek.weather.data.WeatherIcon;
import pl.nauka.jarek.weather.model.current.CityWeather;

public class DetailFragmentActivity extends Fragment {

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

        //Pobieranie pogody dla wybranego miasta z listy
        CityWeather weather = CityWeatherData.getList().get(CityWeatherActivity.listPosition);

        String icon = weather.getWeather().getIcon();  //TODO zrobić mape wywolan
        int weatherIconFromResource = WeatherIcon.getWeatherIconFromResource(icon);
        holder.ivWeatherIcon.setImageResource(weatherIconFromResource);

        //primary_weather_info.xml
        holder.tvDate.setText(getCurrentlyDate());
        holder.tvTemperature.setText(String.valueOf(weather.getMain().getTemp()) + "°C");
        holder.tvHightTemperature.setText("Dzień " + String.valueOf(weather.getMain().getTempMax()) + "°C");
        holder.tvLowTemperature.setText("Noc " + String.valueOf(weather.getMain().getTempMin()) + "°C");

        String description = weather.getWeather().getDescription();
        //zmiana "description" na odpowiednia postac stringa aby moc pobrac z Resources, dalej tłumaczenie
        String translatedDescription = StringFromResourcesByName.getStringFromResourcesByName(description, getContext());
        holder.tvCityWeather.setText(translatedDescription);

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
        String monthString = "";

        if (monthInt == 1){monthString = "month_1";}
        if (monthInt == 2){monthString = "month_2";}
        if (monthInt == 3){monthString = "month_3";}
        if (monthInt == 4){monthString = "month_4";}
        if (monthInt == 5){monthString = "month_5";}
        if (monthInt == 6){monthString = "month_6";}
        if (monthInt == 7){monthString = "month_7";}
        if (monthInt == 8){monthString = "month_8";}
        if (monthInt == 9){monthString = "month_9";}
        if (monthInt == 10){monthString = "month_10";}
        if (monthInt == 11){monthString = "month_11";}
        if (monthInt == 12){monthString = "month_12";}

        //tłumaczenie nazw miesiecy
        monthString = StringFromResourcesByName.getStringFromResourcesByName(monthString, getContext());

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //TODO zmienic wyswietalnie minut. Dla 0-9 dodac "0" z przodu


        return monthString + " " + day + ", " + hour + ":" + minute;
    }
}
