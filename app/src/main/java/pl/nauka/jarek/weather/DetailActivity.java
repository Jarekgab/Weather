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

import pl.nauka.jarek.weather.data.CityWeatherData;
import pl.nauka.jarek.weather.model.CityWeather;

public class DetailActivity extends Fragment {
//    private static String TAG = "DetailActivity";

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

            //TODO secondary_weather_info.xml

        }

        ViewHolder holder = null;
        holder = new ViewHolder();

        holder.tvDate = view.findViewById(R.id.tv_date);
        holder.tvTemperature = view.findViewById(R.id.tv_temperature);
        holder.tvHightTemperature = view.findViewById(R.id.tv_hight_temperature);
        holder.tvLowTemperature = view.findViewById(R.id.tv_low_temperature);
        holder.tvCityWeather = view.findViewById(R.id.tv_city_weather);
        holder.ivWeatherIcon = view.findViewById(R.id.iv_weather_icon);

        CityWeather weather = CityWeatherData.getList().get(CityWeatherActivity.listPosition);

        String icon = weather.getWeather().getIcon();  //TODO zrobić mape wywolan

        switch (icon) {
            case "01d":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_01d);
                break;

            case "01n":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_01n);
                break;

            case "02d":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_02d);
                break;

            case "02n":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_02n);
                break;

            case "03d":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_03d);
                break;

            case "03n":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_03n);
                break;

            case "04d":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_04d);
                break;

            case "04n":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_04n);
                break;

            case "09d":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_09d);
                break;

            case "09n":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_09n);
                break;

            case "10d":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_10d);
                break;

            case "10n":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_10n);
                break;

            case "11d":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_11d);
                break;

            case "11n":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_11n);
                break;

            case "13d":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_13d);
                break;

            case "13n":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_13n);
                break;

            case "50d":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_50d);
                break;

            case "50n":
                holder.ivWeatherIcon.setImageResource(R.drawable.ic_50n);
                break;
        }

//        holder.tvDate.setText(TODO dodać date);
        holder.tvTemperature.setText(String.valueOf(weather.getMain().getTemp()) + "°C");
        holder.tvHightTemperature.setText(String.valueOf(weather.getMain().getTempMax()) + "°C");
        holder.tvLowTemperature.setText(String.valueOf(weather.getMain().getTempMin()) + "°C");
        holder.tvCityWeather.setText(weather.getWeather().getDescription());

        return view;
    }
}
