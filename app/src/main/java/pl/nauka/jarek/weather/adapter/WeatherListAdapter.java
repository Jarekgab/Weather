package pl.nauka.jarek.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.nauka.jarek.weather.R;
import pl.nauka.jarek.weather.model.CityWeather;

public class WeatherListAdapter extends BaseAdapter {

    Context context;
    List<CityWeather> list;

    public WeatherListAdapter(Context context, List<CityWeather> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.indexOf(getItem(position));
    }

    private class ViewHolder {

        ImageView ivWeatherIcon;
        TextView tvCityName;
        TextView tvCityWeather;
        TextView tvHightTemperature;
        TextView tvLowTemperature;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.ivWeatherIcon = convertView.findViewById(R.id.iv_weather_icon);
            holder.tvCityName = convertView.findViewById(R.id.tv_city_name);
            holder.tvCityWeather = convertView.findViewById(R.id.tv_city_weather);
            holder.tvHightTemperature = convertView.findViewById(R.id.tv_hight_temperature);
            holder.tvLowTemperature = convertView.findViewById(R.id.tv_low_temperature);

            CityWeather cityWeatherPos = list.get(position);

            String icon = cityWeatherPos.getWeather().getIcon();  //TODO zrobić mape wywolan

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

            holder.tvCityName.setText(cityWeatherPos.getName());
            holder.tvCityWeather.setText(cityWeatherPos.getWeather().getDescription());
            holder.tvHightTemperature.setText(String.valueOf(cityWeatherPos.getMain().getTempMax()));
            holder.tvLowTemperature.setText(String.valueOf(cityWeatherPos.getMain().getTempMin()));

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}
