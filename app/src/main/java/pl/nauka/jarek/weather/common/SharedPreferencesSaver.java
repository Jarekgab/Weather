package pl.nauka.jarek.weather.common;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import pl.nauka.jarek.weather.model.CityWeather;

public class SharedPreferencesSaver {

    private static final String LIST_KEY = "LIST_KEY";
    private static final String CITY_NAME_LIST_KEY = "CITY_NAME_LIST_KEY";

    public static void saveTo(List<CityWeather> list, List<String> cityNameList, SharedPreferences sp){

        Gson gson = new Gson();
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(LIST_KEY, gson.toJson(list));           //Gson zamienia na String
        editor.putString(CITY_NAME_LIST_KEY, gson.toJson(cityNameList));     //Gson zamienia na String
        editor.apply();                                                     //zapis równoległy bez blokady wątku
    }

    public static List<CityWeather> loadList(SharedPreferences sp){

        String newListString = sp.getString(LIST_KEY, null);     //String z SpinnerList
        Gson gson = new Gson();

        return gson.fromJson(newListString, new TypeToken<List<CityWeather>>() {}.getType());
        //zamiana Stringa na Liste
    }

    public static List<String> loadCityNameList(SharedPreferences sp){

        String newCityNameListString = sp.getString(CITY_NAME_LIST_KEY, null);     //String z spinnerItems
        Gson gson = new Gson();

        return gson.fromJson(newCityNameListString, new TypeToken<List<String>>() {}.getType());
        //zamiana Stringa na Liste
    }
}
