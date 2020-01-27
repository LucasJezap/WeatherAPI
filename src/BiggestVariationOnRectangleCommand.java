import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * This class belongs to Command design patter. It's task is to find the city in which given parameter
 * will have biggest variations in next 5 days
 */
public class BiggestVariationOnRectangleCommand implements iWeatherCommand {
    /**
     * names - list of Name objects to which Gson parse <br>
     * result - result of the command after executing command <br>
     * plist - arraylist of ParameterList objects
     */
    private NameList names;
    private StringBuilder result = new StringBuilder();
    private ArrayList<ParameterList> plist = new ArrayList<>();
    /**
     * @param parameter parameter e.g. wind
     * @param left left coordinate e.g. 50
     * @param bottom bottom coordinate e.g. 40
     * @param right right coordinate e.g. 20
     * @param top top coordinate e.g. 30
     */
    public void executeCommand(String parameter, String left, String bottom, String right, String top) {
        String u = "https://api.openweathermap.org/data/2.5/box/city?bbox="+
                left + ","+ bottom + "," + right + "," + top + ",10&appid=99ee4197968efacdb5fbb1b28f73543e";
        String json = new GsonUtility().getJson(u);
        names = new Gson().fromJson(json, NameList.class);

        ParameterList list;
        for (Name name : names.list) {
            String v = "https://api.openweathermap.org/data/2.5/forecast?q=" +
                    name.name + "&appid=99ee4197968efacdb5fbb1b28f73543e";
            String json2 = new GsonUtility().getJson(v);
            list = new Gson().fromJson(json2,ParameterList.class);
            plist.add(list);
        }
        double maxDiff=0;
        String sName = "";
        for (ParameterList pl: plist) {
            double min = 1e6;
            double max = 0;
            if (pl == null) continue;
            for (Parameter pt: pl.list) {
                switch(parameter) {
                    case "temp": {
                        if(pt.main.temp>max)
                            max = pt.main.temp;
                        else if(pt.main.temp<min)
                            min = pt.main.temp;
                        break;
                    }
                    case "pressure": {
                        if(pt.main.pressure>max)
                            max = pt.main.pressure;
                        else if(pt.main.pressure<min)
                            min = pt.main.pressure;
                        break;
                    }
                    case "humidity": {
                        if(pt.main.humidity>max)
                            max = pt.main.humidity;
                        else if(pt.main.humidity<min)
                            min = pt.main.humidity;
                        break;
                    }
                    case "wind": {
                        if(pt.wind.speed>max)
                            max = pt.wind.speed;
                        else if(pt.wind.speed<min)
                            min = pt.wind.speed;
                        break;
                    }
                    case "clouds": {
                        if(pt.clouds.all>max)
                            max = pt.clouds.all;
                        else if(pt.clouds.all<min)
                            min = pt.clouds.all;
                        break;
                    }
                }
            }
            if(max-min>maxDiff) {
                maxDiff = max-min;
                sName = names.list.get(plist.indexOf(pl)).name;
            }
        }
        result.append("The biggest variations of the parameter " + parameter.toUpperCase() +
                " on the box from " + bottom + " to " + top + "°N and from " + left + " to " + right +
                "°E \n was in city " + sName + " and the difference between maximum and minimum was " + maxDiff);
    }

    public NameList getNames() {
        return names;
    }

    public void setNames(NameList names) {
        this.names = names;
    }

    public StringBuilder getResult() {
        return result;
    }

    public void setResult(StringBuilder result) {
        this.result = result;
    }

    public ArrayList<ParameterList> getPlist() {
        return plist;
    }

    public void setPlist(ArrayList<ParameterList> plist) {
        this.plist = plist;
    }

    /**
     * Class which represents list of Name objects
     */
    private static class NameList {
        public ArrayList<Name> list;
    }

    /**
     * Class which represents Name object
     */
    private static class Name {
        public String name;
    }

    /**
     * Class which represents list of Parameter objects
     */
    public static class ParameterList {
        public ArrayList<Parameter> list = new ArrayList<>();
    }

    /**
     * Class which represents Parameter object
     */
    public static class Parameter {
        /**
         * main - MainInfo object <br>
         * clouds - Clouds object <br>
         * wind - Wind object <br>
         */
        public MainInfo main;
        public Wind wind;
        public Clouds clouds;

        public static class MainInfo {
            public double temp;
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
