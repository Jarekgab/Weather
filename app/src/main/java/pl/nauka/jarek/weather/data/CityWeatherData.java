package pl.nauka.jarek.weather.data;

import java.util.ArrayList;
import java.util.List;

import pl.nauka.jarek.weather.model.CityWeather;

public class CityWeatherData {

    static final List<CityWeather> list = new ArrayList<>();

    public static void addCityWeather(CityWeather weather){
        list.clear();
        list.add(weather);
    }

    public static List<CityWeather> getList(){
        return list;

    }

}
