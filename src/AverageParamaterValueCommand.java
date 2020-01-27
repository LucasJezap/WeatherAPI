import com.google.gson.Gson;
import java.util.ArrayList;


/**
 * This class belongs to Command design patter. It's task is to calculate average value of given parameter in given
 * city between given time range (5 next days and everything found in Cache)
 */
public class AverageParamaterValueCommand implements iWeatherCommand {
    /**
     * list - list of Forecast objects to which Gson parse <br>
     * result - result of the command after executing command
     */
    private ForecastList list = new ForecastList();
    private StringBuilder result = new StringBuilder();

    /**
     * @param cityName - name of the city
     * @param parameter - parameter e.g. wind
     * @param stime - start time in format YYYY-MM-DD'T'HH:MM:SS
     * @param etime - end time in format YYYY-MM-DD'T'HH:MM:SS
     */
    public void executeCommand(String cityName, String parameter, long stime, long etime) {
        String u = "https://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&appid=99ee4197968efacdb5fbb1b28f73543e";
        String json = new GsonUtility().getJson(u);
        ForecastList tmpList = new Gson().fromJson(json, ForecastList.class);
        list.list = new ArrayList<>();
        for (int i=0; i < tmpList.list.size(); i++) {
            if(tmpList.list.get(i).dt>stime && tmpList.list.get(i).dt<etime)
                list.list.add(tmpList.list.get(i));
        }
        ArrayList<String> inCache = new Cache().searchForecastThroughCache(cityName);
        for(String x: inCache) {
            ForecastList f = new Gson().fromJson(x,ForecastList.class);
            for (Forecast fc: f.list)
                if(fc.dt>stime && fc.dt<etime)
                    list.list.add(fc);
        }
        double avg = 0;
        switch(parameter) {
            case "temp": {
                for (Forecast f: list.list) avg += f.main.temp;
                avg /= list.list.size();
                avg -= 273.15;
                result.append("Average Temperature (in °C) : " + avg + "\n");
                break;
            }
            case "feels_like": {
                for (Forecast f: list.list) avg += f.main.feels_like;
                avg /= list.list.size();
                avg -= 273.15;
                result.append("Average Feels_Like Temperature (in °C) : " + avg + "\n");
                break;
            }
            case "pressure": {
                for (Forecast f: list.list) avg += f.main.pressure;
                avg /= list.list.size();
                result.append("Average Pressure (in hPa) : " + avg + "\n");
                break;
            }
            case "humidity": {
                for (Forecast f: list.list) avg += f.main.humidity;
                avg /= list.list.size();
                result.append("Average Humidity (in %) : " + avg + "\n");
                break;
            }
            case "clouds": {
                for (Forecast f: list.list) avg += f.clouds.all;
                avg /= list.list.size();
                result.append("Average Clouds (in %) : " + avg + "\n");
                break;
            }
            case "wind": {
                for (Forecast f: list.list) avg += f.wind.speed;
                avg /= list.list.size();
                avg *= 3.6;
                result.append("Average Wind speed (in km/h) : " + avg + "\n");
                break;
            }
            default:
                throw new IllegalArgumentException("Wrong parameter! You can't check the " + parameter + " average " +
                        "value");
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
         * clouds - Clouds object <br>
         * wind - Wind object <br>
         */
        public long dt;
        public Main main;
        public Clouds clouds;
        public Wind wind;

        public static class Main {
            public double temp;
            public double feels_like;
            public double pressure;
            public double humidity;
        }

        public static class Clouds {
            public double all;
        }

        public static class Wind {
            public double speed;
        }

    }
}
