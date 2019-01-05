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
import pl.nauka.jarek.weather.adapter.ForecastHourListAdapter;
import pl.nauka.jarek.weather.common.FormatDate;
import pl.nauka.jarek.weather.common.ListUtils;
import pl.nauka.jarek.weather.common.StringFromResourcesByName;
import pl.nauka.jarek.weather.data.CityWeatherData;
import pl.nauka.jarek.weather.data.WeatherIcon;
import pl.nauka.jarek.weather.model.current.CityWeather;

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

        hourAdapter = new ForecastHourListAdapter(getContext(), CityWeatherActivity.list);
        holder.lvForecastHour.setAdapter(hourAdapter);
        ListUtils.setDynamicHeight(holder.lvForecastHour);


        //Pobieranie pogody dla wybranego miasta z listy
        CityWeather weather = CityWeatherData.getList().get(CityWeatherActivity.listPosition);

        String icon = weather.getWeather().getIcon();
        int weatherIconFromResource = WeatherIcon.getWeatherIconFromResource(icon);
        holder.ivWeatherIcon.setImageResource(weatherIconFromResource);

        //primary_weather_info.xml

        //przypisanie aktualnej i sformatowanej daty
        holder.tvDate.setText(FormatDate.getCurrentlyDate(getContext()));

        //zaokraglenie temp
        short temp = (short) Math.round(weather.getMain().getTemp());
        short tempHight = (short) Math.round(weather.getMain().getTempMax());
        short tempLow = (short) Math.round(weather.getMain().getTempMin());

        holder.tvTemperature.setText(String.valueOf(temp) + "°C");
        holder.tvHightTemperature.setText("Dzień " + String.valueOf(tempHight) + "°C");
        holder.tvLowTemperature.setText("Noc " + String.valueOf(tempLow) + "°C");

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
