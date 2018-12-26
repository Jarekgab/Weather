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
import pl.nauka.jarek.weather.common.StringFromResourcesByName;
import pl.nauka.jarek.weather.data.WeatherIcon;
import pl.nauka.jarek.weather.model.current.CityWeather;

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
            int weatherIconFromResource = WeatherIcon.getWeatherIconFromResource(icon);
            holder.ivWeatherIcon.setImageResource(weatherIconFromResource);

            holder.tvCityName.setText(cityWeatherPos.getName());

            String description = cityWeatherPos.getWeather().getDescription();
            //zmiana "description" na odpowiednia postac stringa aby moc pobrac z Resources, dalej tłumaczenie
            String translatedDescription = StringFromResourcesByName.getStringFromResourcesByName(description, context);
            holder.tvCityWeather.setText(translatedDescription);

            holder.tvHightTemperature.setText(String.valueOf(cityWeatherPos.getMain().getTempMax()) + "°C");
            holder.tvLowTemperature.setText(String.valueOf(cityWeatherPos.getMain().getTempMin()) + "°C");

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}
