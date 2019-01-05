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
import pl.nauka.jarek.weather.common.FormatDate;
import pl.nauka.jarek.weather.common.StringFromResourcesByName;
import pl.nauka.jarek.weather.data.WeatherIcon;

public class TomorrowFragmentActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tomorrow_fragment, container, false);

        class ViewHolder {

            //primary_weather_info.xml

            TextView tvDate;
            TextView tvTemperature;
            TextView tvHightTemperature;
            TextView tvLowTemperature;
            TextView tvCityWeather;
            ImageView ivWeatherIcon;

            //secondary_weather_info.xml

            TextView tvHeading;
            TextView tvPressure;
            TextView tvCloudsAll;
            TextView tvHumidity;
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
        holder.tvHeading = view.findViewById(R.id.tv_secondary_heading);
        holder.tvPressure = view.findViewById(R.id.tv_pressure);
        holder.tvPressure = view.findViewById(R.id.tv_pressure);
        holder.tvCloudsAll = view.findViewById(R.id.tv_clouds_all);
        holder.tvHumidity = view.findViewById(R.id.tv_humidity);
        holder.tvWindSpeed = view.findViewById(R.id.tv_wind_speed);

        //pobiranie danych pogodowych z listy na nastepny dzien na ta sama godzine
        pl.nauka.jarek.weather.model.forecast.List tomorrow = CityWeatherActivity.list.get(8);

        String icon = tomorrow.getWeather().getIcon();
        int weatherIconFromResource = WeatherIcon.getWeatherIconFromResource(icon);
        holder.ivWeatherIcon.setImageResource(weatherIconFromResource);

        //primary_weather_info.xml
        String dtTxt = String.valueOf(tomorrow.getDtTxt());
        String data = dtTxt.substring(0, 10);

        //formatowanie daty
        String dayOfWeek = FormatDate.getFormatDate(data, getContext());
        holder.tvDate.setText(dayOfWeek);

        //zaokraglenie temp
        short temp = (short) Math.round(tomorrow.getMain().getTemp());
        short tempHight = (short) Math.round(tomorrow.getMain().getTempMax());
        short tempLow = (short) Math.round(tomorrow.getMain().getTempMin());

        holder.tvTemperature.setText(String.valueOf(temp) + "°C");
        holder.tvHightTemperature.setText("Dzień " + String.valueOf(tempHight) + "°C");
        holder.tvLowTemperature.setText("Noc " + String.valueOf(tempLow) + "°C");

        String description = tomorrow.getWeather().getDescription();
        //zmiana "description" na odpowiednia postac stringa aby moc pobrac z Resources, dalej tłumaczenie
        String translatedDescription = StringFromResourcesByName.getStringFromResourcesByName(description, getContext());
        holder.tvCityWeather.setText(translatedDescription);

        //secondary_weather_info.xml
        holder.tvHeading.setText("PROGNOZOWANE DANE");
        holder.tvPressure.setText(String.valueOf(tomorrow.getMain().getPressure() + " hPa"));
        holder.tvCloudsAll.setText(String.valueOf(tomorrow.getClouds().getAll()) + " %");
        holder.tvHumidity.setText(String.valueOf(tomorrow.getMain().getHumidity()) + " %");
        holder.tvWindSpeed.setText(String.valueOf(tomorrow.getWind().getSpeed()) + " m/s");

        return view;
    }
}
