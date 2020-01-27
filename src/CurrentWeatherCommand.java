import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * This class belongs to Command design patter. It's task is to find current weather for given city
 */
public class CurrentWeatherCommand implements iWeatherCommand {
    /**
     * currentWeather - currentWeather object used for Gson parsing <br>
     * result - result of the command after executing command
     */
    private CurrentWeather currentWeather;
    private StringBuilder result = new StringBuilder();

    /**
     * @param cityName name of the city
     */
    public void executeCommand(String cityName) {
        String u = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=99ee4197968efacdb5fbb1b28f73543e";
        String json = new GsonUtility().getJson(u);
        currentWeather = new Gson().fromJson(json, CurrentWeather.class);
        currentWeather.main.temp -= 273.15;
        currentWeather.wind.speed *= (3.6);

        result.append("City : " + cityName + "\n");
        result.append("Main : " + currentWeather.weather.get(0).main + "\n");
        result.append("Description : " + currentWeather.weather.get(0).description + "\n");
        result.append("Pressure (in hPa) : " + currentWeather.main.pressure + "\n");
        result.append("Humidity (in %) : " + currentWeather.main.humidity + "\n");
        result.append("Temperature (in °C) : " + currentWeather.main.temp + "\n");
        result.append("Wind speed (in km/h) : " + currentWeather.wind.speed + "\n");
        result.append("Clouds (in %) : " + currentWeather.clouds.all + "\n");
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public StringBuilder getResult() {
        return result;
    }

    public void setResult(StringBuilder result) {
        this.result = result;
    }

    /**
     * Class which represents CurrentWeather object
     */
    public static class CurrentWeather {
        /**
         * weather - ArrayList of Weather objects <br>
         * main - MainInfo object <br>
         * clouds - Clouds object <br>
         * wind - Wind object <br>
         */
        public ArrayList<Weather> weather;
        public MainInfo main;
        public Wind wind;
        public Clouds clouds;

        public static class Weather {
            public String main;
            public String description;
        }

        public static class MainInfo {
            public double temp;
            public String pressure;
            public String humidity;
        }

        public class Wind {
            public double speed;
        }

        public class Clouds {
            public String all;
        }
    }
}
