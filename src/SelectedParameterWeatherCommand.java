import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class belongs to Command design patter. It's task is to find the value of given parameter for given city in
 * given time (next 5 days)
 */
public class SelectedParameterWeatherCommand implements iWeatherCommand {
    /**
     * list - list of Forecast objects to which Gson parse <br>
     * result - result of the command after executing command
     */
    private ForecastList list;
    private StringBuilder result = new StringBuilder();

    /**
     * @param cityName name of the city
     * @param parameter parameter e.g. wind
     * @param time - time in format YYYY-MM-DD'T'HH:MM:SS
     * @throws IOException when parameters were wrong
     */
    public void executeCommand(String cityName, String parameter, long time) throws IOException {
        String u = "https://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&appid=99ee4197968efacdb5fbb1b28f73543e";
        String json = new GsonUtility().getJson(u);
        list = new Gson().fromJson(json, ForecastList.class);
        int index;
        for (index=0; index < list.list.size() && list.list.get(index).dt<time; index++);
        if (index == list.list.size() || (index == 0 && list.list.get(0).dt>time))
            throw new IOException("Provided time was not in next 5 days!!");
        Forecast rightHour = list.list.get(index);
        rightHour.main.temp -= 273.15;
        rightHour.main.feels_like -= 273.15;
        rightHour.wind.speed *= 3.6;

        switch(parameter) {
            case "temp": {
                result.append("Temperature (in °C) : " + rightHour.main.temp + "\n");
                break;
            }
            case "feels_like": {
                result.append("Temperature (in °C) : " + rightHour.main.feels_like + "\n");
                break;
            }
            case "pressure": {
                result.append("Pressure (in hPa) : " + rightHour.main.pressure + "\n");
                break;
            }
            case "humidity": {
                result.append("Humidity (in %) : " + rightHour.main.humidity + "\n");
                break;
            }
            case "main": {
                result.append("Main : " + rightHour.weather.get(0).main+ "\n");
                break;
            }
            case "description": {
                result.append("Description : " + rightHour.weather.get(0).description + "\n");
                break;
            }
            case "clouds": {
                result.append("Clouds (in %) : " + rightHour.clouds.all + "\n");
                break;
            }
            case "wind": {
                result.append("Wind speed (in km/h) : " + rightHour.wind.speed + "\n");
                break;
            }
            default:
                throw new IllegalArgumentException("Wrong parameter! You can't check the " + parameter + " value");
        }
    }

    public ForecastList getList() {
        return list;
    }

    public void setList(ForecastList list) {
        this.list = list;
    }

    public StringBuilder getResult() {
        return result;
    }

    public void setResult(StringBuilder result) {
        this.result = result;
    }

    /**
     * Class which represents list of Forecast objects
     */
    public static class ForecastList {
        public ArrayList<Forecast> list;
    }

    /**
     * Class which represents Forecast object
     */
    public static class Forecast {
        /**
         * dt - date time in Unix Format <br>
         * main - Main object <br>
         * weather - ArrayList of Weather objects <br>
         * clouds - Clouds object <br>
         * wind - Wind object <br>
         */
        public long dt;
        public Main main;
        public ArrayList<Weather> weather;
        public Clouds clouds;
        public Wind wind;

        public static class Main {
            public double temp;
            public double feels_like;
            public String pressure;
            public String humidity;
        }

        public static class Weather {
            public String main;
            public String description;
        }

        public static class Clouds {
            public String all;
        }

        public static class Wind {
            public double speed;
        }

    }
}

