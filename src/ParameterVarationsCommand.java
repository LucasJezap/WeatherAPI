import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.LinkedHashSet;

/**
 * This class belongs to Command design patter. It's task is to print chart of given parameter values in next 5 days
 * for given cities every 3 hours
 */
public class ParameterVarationsCommand {
    /**
     * result - result of the command after executing command <br>
     * forecasts - linkedhashset of ParameterList objects
     */
    private LinkedHashSet<ParameterList> forecasts = new LinkedHashSet<>();
    private StringBuilder result = new StringBuilder();

    /**
     * @param cities - name of the cities
     * @param parameter - parameter e.g. wind
     * @param stime - start time in format YYYY-MM-DD'T'HH:MM:SS
     * @param etime - end time in format YYYY-MM-DD'T'HH:MM:SS
     */
    public void executeCommand(String cities, String parameter, long stime, long etime) {
        String[] cityList = cities.split("\\|");
        for (String city: cityList) {
            city = city.trim();
            String v = "https://api.openweathermap.org/data/2.5/forecast?q=" +
                    city + "&appid=99ee4197968efacdb5fbb1b28f73543e";
            String json2 = new GsonUtility().getJson(v);
            ParameterList list = new Gson().fromJson(json2, ParameterList.class);
            for (Parameter p: list.list)
                p.cityName = city;
            forecasts.add(list);
        }
        for (int i=0; i<40; i++) {
            for(ParameterList pl: forecasts) {
                int j = 0;
                for (Parameter p: pl.list) {
                    if (j < i) {
                        j += 1;
                        continue;
                    }
                    if (p.dt<stime || p.dt>etime) break;
                    addToResult(p,parameter);
                    break;
                }
            }
            result.append("\n");
        }
    }

    /**
     * @param p - given Parameter object
     * @param parameter - given parameter name
     * @param value - value of the parameter
     * @param start - helping value to plot chart
     */
    public void addChart(Parameter p, String parameter, double value, int start) {
        int first = start - ((int) value - start)*4;
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%40s"," "));
        for (int i=first; i<(int) value; i++)
            sb.append("-");
        sb.append("-\n");
        sb.append(String.format("%40s",unixToDate(p.dt) + "(" + p.cityName + ") "));
        for (int i=start; i<(int) value; i++)
            sb.append("|    ");
        if (parameter.equals("humidity") || parameter.equals("clouds"))
            sb.append("| " + parameter + " = " + new DecimalFormat("###.###").format(value*4) + "\n");
        else
            sb.append("| " + parameter + " = " + new DecimalFormat("###.###").format(value) + "\n");
        sb.append(String.format("%40s"," "));
        for (int i=first; i<(int) value; i++)
            sb.append("-");
        sb.append("-\n");
        result.append(sb.toString());
    }

    /**
     * @param p - given Parameter object
     * @param parameter - given parameter name
     */
    public void addToResult(Parameter p, String parameter) {
        switch (parameter) {
            case "temp": {
                addChart(p,parameter,p.main.temp-273.15,-20);
                break;
            }
            case "feels_like": {
                addChart(p,parameter,p.main.feels_like-273.15,-20);
                break;
            }
            case "pressure": {
                addChart(p,parameter,p.main.pressure,1010);
                break;
            }
            case "humidity": {
                addChart(p,parameter,p.main.humidity/4,0);
                break;
            }
            case "clouds": {
                addChart(p,parameter,p.clouds.all/4,0);
                break;
            }
            case "wind": {
                addChart(p,parameter,p.wind.speed*3.6,0);
                break;
            }
        }
    }

    /**
     * @param time time in Unix format
     * @return date
     */
    public String unixToDate(long time) {
        Instant instant = Instant.ofEpochSecond(time);
        return instant.toString().replaceAll("[TZ]"," ");
    }

    public StringBuilder getResult() {
        return result;
    }

    public void setResult(StringBuilder result) {
        this.result = result;
    }

    public LinkedHashSet<ParameterList> getForecasts() {
        return forecasts;
    }

    public void setForecasts(LinkedHashSet<ParameterList> forecasts) {
        this.forecasts = forecasts;
    }

    /**
     * Class which represents list of Parameter objects
     */
    public static class ParameterList {
        public LinkedHashSet<Parameter> list = new LinkedHashSet<>();
    }

    /**
     * Class which represents Parameter object
     */
    public static class Parameter {
        /**
         * cityName - name of the city <br>
         * dt - time <br>
         * main - MainInfo object <br>
         * clouds - Clouds object <br>
         * wind - Wind object <br>
         */
        public String cityName;
        public long dt;
        public MainInfo main;
        public Wind wind;
        public Clouds clouds;

        public static class MainInfo {
            public double temp;
            public double feels_like;
            public double pressure;
            public double humidity;
        }

        public class Wind {
            public double speed;
        }

        public class Clouds {
            public double all;
        }
    }
}
