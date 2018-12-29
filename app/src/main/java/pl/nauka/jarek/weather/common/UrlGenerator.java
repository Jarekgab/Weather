package pl.nauka.jarek.weather.common;

public class UrlGenerator {

    private static final String FIRST_URL_CURRENT_WEATHER = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String FIRST_URL_FORECAST_WEATHER = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private static final String SECOND_URL = "&units=metric&APPID=";
    private static final String KEY = "4b18af8ae81c911d81a965f0804b7845";

    public static String getCurrentUrl(String city){

        String url = FIRST_URL_CURRENT_WEATHER + city + SECOND_URL + KEY;

        return url;
    }

    public static String getForecastUrl(String city){

        String url = FIRST_URL_FORECAST_WEATHER + city + SECOND_URL + KEY;

        return url;
    }
}
