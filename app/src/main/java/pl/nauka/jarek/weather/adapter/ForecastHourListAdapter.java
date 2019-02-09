package pl.nauka.jarek.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import pl.nauka.jarek.weather.R;
import pl.nauka.jarek.weather.common.StringFromResourcesByName;
import pl.nauka.jarek.weather.data.WeatherIcon;

public class ForecastHourListAdapter extends BaseAdapter {

    private Context context;
    private java.util.List<pl.nauka.jarek.weather.model.forecast.List> list;

    public ForecastHourListAdapter(Context context, java.util.List<pl.nauka.jarek.weather.model.forecast.List> list) {
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
        TextView tvHour;
        TextView tvDescription;
        TextView tvTemperature;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = mInflater.inflate(R.layout.forecast_hour_list_item, null);
        holder = new ViewHolder();

        holder.ivWeatherIcon = convertView.findViewById(R.id.iv_weather_icon);
        holder.tvHour = convertView.findViewById(R.id.tv_hour);
        holder.tvDescription = convertView.findViewById(R.id.tv_description);
        holder.tvTemperature = convertView.findViewById(R.id.tv_temperature);

        pl.nauka.jarek.weather.model.forecast.List day = list.get(position);

        String icon = day.getWeather().getIcon();
        int weatherIconFromResource = WeatherIcon.getWeatherIconFromResource(icon);
        holder.ivWeatherIcon.setImageResource(weatherIconFromResource);

        short temp = (short) Math.round(day.getMain().getTemp());
        holder.tvTemperature.setText(String.valueOf(temp + "°C"));

        String dtTxt = String.valueOf(day.getDtTxt());
        String hour = dtTxt.substring(11, 16);
        holder.tvHour.setText(hour);

        String description = day.getWeather().getDescription();
        //zmiana "description" na odpowiednia postac stringa aby moc pobrac z Resources, dalej tłumaczenie
        String translatedDescription = StringFromResourcesByName.getStringFromResourcesByName(description, context);
        holder.tvDescription.setText(translatedDescription);

        convertView.setTag(holder);

        return convertView;

    }
}
