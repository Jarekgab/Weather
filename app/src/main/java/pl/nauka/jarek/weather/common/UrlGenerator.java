package pl.nauka.jarek.weather.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlGenerator {

    private static final String FIRST_URL_CURRENT_WEATHER = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String FIRST_URL_FORECAST_WEATHER = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private static final String SECOND_URL = "&units=metric&APPID=";
    private static final String KEY = "4b18af8ae81c911d81a965f0804b7845";

    public static String getCurrentUrl(String city){

        String url = null;
        try {
            url = FIRST_URL_CURRENT_WEATHER + URLEncoder.encode(city, "UTF-8") + SECOND_URL + KEY;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }

    public static String getForecastUrl(String city){

        String url = null;
        try {
            url = FIRST_URL_FORECAST_WEATHER + URLEncoder.encode(city,"UTF-8") + SECOND_URL + KEY;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }
}
