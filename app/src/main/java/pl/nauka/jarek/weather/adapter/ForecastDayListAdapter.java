package pl.nauka.jarek.weather.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import pl.nauka.jarek.weather.R;
import pl.nauka.jarek.weather.common.StringFromResourcesByName;
import pl.nauka.jarek.weather.data.WeatherIcon;

public class ForecastDayListAdapter extends BaseAdapter {

    private Context context;
    private java.util.List<pl.nauka.jarek.weather.model.forecast.List> list;

    public ForecastDayListAdapter(Context context, java.util.List<pl.nauka.jarek.weather.model.forecast.List> list) {
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

        holder.ivWeatherIcon = convertView.findViewById(R.id.iv_weather_icon);
        holder.tvDay = convertView.findViewById(R.id.tv_day);
        holder.tvDescription = convertView.findViewById(R.id.tv_description);
        holder.tvTemperature = convertView.findViewById(R.id.tv_temperature);

        pl.nauka.jarek.weather.model.forecast.List day = list.get(position);

        String icon = day.getWeather().getIcon();
        int weatherIconFromResource = WeatherIcon.getWeatherIconFromResource(icon);
        holder.ivWeatherIcon.setImageResource(weatherIconFromResource);

        short temp = (short) Math.round(day.getMain().getTemp());
        holder.tvTemperature.setText(String.valueOf(temp + "°C"));

        String dtTxt = String.valueOf(day.getDtTxt());
        String data = dtTxt.substring(0, 10);

        //Przypisanie dnia tygodnia jako String
        String dayOfWeek = getDayOfWeek(data);
        holder.tvDay.setText(dayOfWeek);

        String description = day.getWeather().getDescription();
        //zmiana "description" na odpowiednia postac stringa aby moc pobrac z Resources, dalej tłumaczenie
        String translatedDescription = StringFromResourcesByName.getStringFromResourcesByName(description, context);
        holder.tvDescription.setText(translatedDescription);

        convertView.setTag(holder);

        return convertView;

    }

    @NonNull
    private String getDayOfWeek(String data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date formatDate = null;
        try {
            formatDate = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatDate);
        int dayOfWeekInt = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfWeek = "";

        if (dayOfWeekInt == 1){dayOfWeek = context.getString(R.string.day_1);}
        if (dayOfWeekInt == 2){dayOfWeek = context.getString(R.string.day_2);}
        if (dayOfWeekInt == 3){dayOfWeek = context.getString(R.string.day_3);}
        if (dayOfWeekInt == 4){dayOfWeek = context.getString(R.string.day_4);}
        if (dayOfWeekInt == 5){dayOfWeek = context.getString(R.string.day_5);}
        if (dayOfWeekInt == 6){dayOfWeek = context.getString(R.string.day_6);}
        if (dayOfWeekInt == 7){dayOfWeek = context.getString(R.string.day_7);}
        return dayOfWeek;
    }
}
