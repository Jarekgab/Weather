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
import java.util.List;

import pl.nauka.jarek.weather.R;
import pl.nauka.jarek.weather.common.StringFromResourcesByName;
import pl.nauka.jarek.weather.data.WeatherIcon;

public class ForecastDayListAdapter extends BaseAdapter {

    private Context context;
    private List<pl.nauka.jarek.weather.model.forecast.List> list;
    private int number = 8;

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

        holder.ivWeatherIcon = convertView.findViewById(R.id.iv_weather_icon);
        holder.tvDay = convertView.findViewById(R.id.tv_day);
        holder.tvDescription = convertView.findViewById(R.id.tv_description);
        holder.tvTemperature = convertView.findViewById(R.id.tv_temperature);

        pl.nauka.jarek.weather.model.forecast.List day = list.get(number);
        number = number + 8;

        //zabezpiecza przed przekroczeniem wielkosci listy
        if (number >= 40) {
            number = 8;
        }

        String icon = day.getWeather().getIcon();
        int weatherIconFromResource = WeatherIcon.getWeatherIconFromResource(icon);
        holder.ivWeatherIcon.setImageResource(weatherIconFromResource);

        short temp = (short) Math.round(day.getMain().getTemp());
        holder.tvTemperature.setText(String.valueOf(temp + "°C"));

        String dtTxt = String.valueOf(day.getDtTxt());
        String data = dtTxt.substring(0, 10);

        //Przypisanie dnia tygodnia jako String
        String dayOfMonth = getDayOfMonth(data);
        holder.tvDay.setText(dayOfMonth);

        String description = day.getWeather().getDescription();
        //zmiana "description" na odpowiednia postac stringa aby moc pobrac z Resources, dalej tłumaczenie
        String translatedDescription = StringFromResourcesByName.getStringFromResourcesByName(description, context);
        holder.tvDescription.setText(translatedDescription);

        convertView.setTag(holder);

        return convertView;

    }

    @NonNull
    private String getDayOfMonth(String data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date formatDate = null;
        try {
            formatDate = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatDate);
        int dayOfMonthInt = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfMonth = "";

        if (dayOfMonthInt == 1){dayOfMonth = context.getString(R.string.day_1);}
        if (dayOfMonthInt == 2){dayOfMonth = context.getString(R.string.day_2);}
        if (dayOfMonthInt == 3){dayOfMonth = context.getString(R.string.day_3);}
        if (dayOfMonthInt == 4){dayOfMonth = context.getString(R.string.day_4);}
        if (dayOfMonthInt == 5){dayOfMonth = context.getString(R.string.day_5);}
        if (dayOfMonthInt == 6){dayOfMonth = context.getString(R.string.day_6);}
        if (dayOfMonthInt == 7){dayOfMonth = context.getString(R.string.day_7);}
        return dayOfMonth;
    }
}
