package pl.nauka.jarek.weather.common;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pl.nauka.jarek.weather.R;

public class FormatDate {

    public static String getCurrentlyDate(Context context) {
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
        monthString = StringFromResourcesByName.getStringFromResourcesByName(monthString, context);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if (minute >= 0 && minute <= 9){
            return monthString + " " + day + ", " + hour + ":0" + minute;
        }else {
            return monthString + " " + day + ", " + hour + ":" + minute;
        }
    }

    public static String getFormatDate(String data, Context context) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date formatDate = null;
        try {
            formatDate = format.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatDate);

        //Dzien tygodnia String
        int dayOfWeekInt = calendar.get(Calendar.DAY_OF_WEEK);
        String dayOfWeek = "";

        if (dayOfWeekInt == 1) {dayOfWeek = context.getString(R.string.day_1);}
        if (dayOfWeekInt == 2) {dayOfWeek = context.getString(R.string.day_2);}
        if (dayOfWeekInt == 3) {dayOfWeek = context.getString(R.string.day_3);}
        if (dayOfWeekInt == 4) {dayOfWeek = context.getString(R.string.day_4);}
        if (dayOfWeekInt == 5) {dayOfWeek = context.getString(R.string.day_5);}
        if (dayOfWeekInt == 6) {dayOfWeek = context.getString(R.string.day_6);}
        if (dayOfWeekInt == 7) {dayOfWeek = context.getString(R.string.day_7);}

        //dzien
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //miesiac
        int monthInt = calendar.get(Calendar.MONTH) + 1;

        String monthString = "";

        if (monthInt == 1) {monthString = "month_1"; }
        if (monthInt == 2) {monthString = "month_2";}
        if (monthInt == 3) {monthString = "month_3";}
        if (monthInt == 4) {monthString = "month_4";}
        if (monthInt == 5) {monthString = "month_5";}
        if (monthInt == 6) {monthString = "month_6";}
        if (monthInt == 7) {monthString = "month_7";}
        if (monthInt == 8) {monthString = "month_8";}
        if (monthInt == 9) {monthString = "month_9";}
        if (monthInt == 10) {monthString = "month_10";}
        if (monthInt == 11) {monthString = "month_11";}
        if (monthInt == 12) {monthString = "month_12";}

        //tłumaczenie nazw miesiecy
        monthString = StringFromResourcesByName.getStringFromResourcesByName(monthString, context);

        return monthString + " " + day + ", " + dayOfWeek;
    }
}
