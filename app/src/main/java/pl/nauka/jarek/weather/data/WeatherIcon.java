package pl.nauka.jarek.weather.data;

import pl.nauka.jarek.weather.R;

public class WeatherIcon {

    public static int getWeatherIconFromResource(String icon) {

        int resoult = 0;

        switch (icon) {
            case "01d":
                resoult = R.drawable.ic_01d;
                break;

            case "01n":
                resoult = R.drawable.ic_01n;
                break;

            case "02d":
                resoult = R.drawable.ic_02d;
                break;

            case "02n":
                resoult = R.drawable.ic_02n;
                break;

            case "03d":
                resoult = R.drawable.ic_03d;
                break;

            case "03n":
                resoult = R.drawable.ic_03n;
                break;

            case "04d":
                resoult = R.drawable.ic_04d;
                break;

            case "04n":
                resoult = R.drawable.ic_04n;
                break;

            case "09d":
                resoult = R.drawable.ic_09d;
                break;

            case "09n":
                resoult = R.drawable.ic_09n;
                break;

            case "10d":
                resoult = R.drawable.ic_10d;
                break;

            case "10n":
                resoult = R.drawable.ic_10n;
                break;

            case "11d":
                resoult = R.drawable.ic_11d;
                break;

            case "11n":
                resoult = R.drawable.ic_11n;
                break;

            case "13d":
                resoult = R.drawable.ic_13d;
                break;

            case "13n":
                resoult = R.drawable.ic_13n;
                break;

            case "50d":
                resoult = R.drawable.ic_50d;
                break;

            case "50n":
                resoult = R.drawable.ic_50n;
                break;
        }
        return resoult;
    }
}
