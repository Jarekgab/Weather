package pl.nauka.jarek.weather.data;

import java.util.ArrayList;
import java.util.List;
import pl.nauka.jarek.weather.model.current.CityWeather;

public class CityWeatherData {

    static List<CityWeather> list = new ArrayList<>();

    public static void changeCityWeather(List<CityWeather> newList) {
        list = newList;
    }

    public static void addCityWeather(CityWeather weather) {
        list.add(weather);
    }

    public static List<CityWeather> getList() {
        return list;
    }

    public static void clearCityWeather() {
        list.clear();
    }

    public static void setCityWeather(int index, CityWeather element) {
        list.set(index, element);
    }

    public static void deleteCityWeather(int position) {
        list.remove(position);
    }

}
