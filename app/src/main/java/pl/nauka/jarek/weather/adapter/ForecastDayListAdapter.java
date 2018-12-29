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

public class ForecastDayListAdapter extends BaseAdapter {

    Context context;
    List<pl.nauka.jarek.weather.model.forecast.List> list;

    public ForecastDayListAdapter(Context context, List<pl.nauka.jarek.weather.model.forecast.List> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 4;
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
        TextView tvDay;
        TextView tvDescription;
        TextView tvTemperature;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.forecast_day_list_item, null);
            holder = new ViewHolder();

            holder.tvDay = convertView.findViewById(R.id.tv_day);
            holder.tvDescription = convertView.findViewById(R.id.tv_description);
            holder.tvTemperature = convertView.findViewById(R.id.tv_temperature);

//            holder.tvCityName = convertView.findViewById(R.id.tv_city_name);
//            holder.tvCityWeather = convertView.findViewById(R.id.tv_city_weather);
//            holder.tvHightTemperature = convertView.findViewById(R.id.tv_hight_temperature);
//            holder.tvLowTemperature = convertView.findViewById(R.id.tv_low_temperature);
//
            pl.nauka.jarek.weather.model.forecast.List day = list.get(position);
//
//            String icon = cityWeatherPos.getWeather().getIcon();
//            int weatherIconFromResource = WeatherIcon.getWeatherIconFromResource(icon);
//            holder.ivWeatherIcon.setImageResource(weatherIconFromResource);
//
            holder.tvTemperature.setText(String.valueOf(day.getMain().getTemp()) + "°C");
            holder.tvDay.setText(String.valueOf(day.getDtTxt()));
//
//            String description = cityWeatherPos.getWeather().getDescription();
//            //zmiana "description" na odpowiednia postac stringa aby moc pobrac z Resources, dalej tłumaczenie
//            String translatedDescription = StringFromResourcesByName.getStringFromResourcesByName(description, context);
//            holder.tvCityWeather.setText(translatedDescription);
//
//            holder.tvHightTemperature.setText(String.valueOf(cityWeatherPos.getMain().getTempMax()) + "°C");
//            holder.tvLowTemperature.setText(String.valueOf(cityWeatherPos.getMain().getTempMin()) + "°C");
//
//            convertView.setTag(holder);

        return convertView;

    }
}
