package pl.nauka.jarek.weather.data;

import java.util.ArrayList;

public class ForecastCityWeatherData {

    static java.util.List<pl.nauka.jarek.weather.model.forecast.List> list = new ArrayList<>();

    public static void setList(java.util.List<pl.nauka.jarek.weather.model.forecast.List> newList) {
        list = null;
        list = newList;
    }

    public static java.util.List<pl.nauka.jarek.weather.model.forecast.List> getList() {
        return list;
    }
}


