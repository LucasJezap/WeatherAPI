import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * This class belongs to Command design patter. It's task is to find the coordinates of the place with maximum and
 * minimum values of UV radiation within next 8 days based on given coordinates of number of places
 */
public class MinAndMaxUltravioletCommand implements iWeatherCommand {
    /**
     * locations - ArrayList of UVParameter objects array to which Gson parse <br>
     * result - result of the command after executing command
     */
    private ArrayList<UVParameter[]> locations = new ArrayList<>();
    private StringBuilder result = new StringBuilder();

    /**
     * @param coord coordinates of places the programme is to search through
     */
    public void executeCommand(String coord) {
        String[] coords = coord.split("\\|");
        for (String c: coords) {
            String lat = c.trim().split(" ")[0];
            String lon = c.trim().split(" ")[1];
            String u = "https://api.openweathermap.org/data/2.5/uvi/forecast?lat=" + lat +
                    "&lon=" + lon + "&appid=99ee4197968efacdb5fbb1b28f73543e";
            String json = new GsonUtility().getJson(u);
            UVParameter[] list = new Gson().fromJson(json, UVParameter[].class);
            locations.add(list);
        }
        double min = 1e6;
        double max = -1;
        String minLat = "";
        String minLon = "";
        String maxLat = "";
        String maxLon = "";
        String maxDate = "";
        String minDate = "";
        for (UVParameter[] p: locations) {
            for(UVParameter up: p) {
                if(up.value>max) {
                    max=up.value;
                    maxLat = up.lat;
                    maxLon = up.lon;
                    maxDate = up.date_iso;
                }
                else if (up.value<min) {
                    min=up.value;
                    minLat = up.lat;
                    minLon = up.lon;
                    minDate = up.date_iso;
                }
            }
        }
        minDate = minDate.replaceAll("[TZ]"," ");
        maxDate = maxDate.replaceAll("[TZ]"," ");
        result.append("The most powerful UV radiation (in next 8 days) will be on " + maxDate + " at latitude " + maxLat +
                " and longitude " + maxLon + " with the value of " + max + " \n and the least powerful UV " +
                " radiation will be on " + minDate + " at latitude " + minLat + " and longitude " + minLon +
                " with the value of " + min);
    }

    public ArrayList<UVParameter[]> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<UVParameter[]> locations) {
        this.locations = locations;
    }

    public StringBuilder getResult() {
        return result;
    }

    public void setResult(StringBuilder result) {
        this.result = result;
    }

    /**
     * Class which represents UVParameter object
     */
    public static class UVParameter {
        /**
         * lat - latitude <br>
         * lon - longitude <br>
         * date_iso - date <br>
         * value - value
         */
        public String lat;
        public String lon;
        public String date_iso;
        public double value;
    }
}
