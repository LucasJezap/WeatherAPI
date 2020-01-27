import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main class of the WeatherAPI programme. Here user chooses functionality to use and defines its entry parameters
 * suitable for the functionality.<br> There's also help method and unix time converted in this class which are used to
 * guide user and parse dates so that programme understands it.
 */
public class Facade {
    /**
     * A static method which outputs guides on using the programme correctly. It's thrown on every bad input situation
     * and also on no input at all.
     */
    public static void help() {
        System.out.println("Welcome to the WeatherAPI programme made by Lukas Jezap. Looks like you've made " +
                        "a mistake with providing the right parameters for the programme.\nThe programme offers 7 " +
                        "functionalities:\n\nCurrentWeather - returns information about current weather in selected " +
                        "city, the call should look like\nCurrentWeather [CityName]\n" +
                        "SelectedParameterWeather - returns information about selected parameter in selected city, " +
                        "the call should look like\n\nSelectedParameterWeather [CityName] [Parameter] [date]\n" +
                        "The parameter is to be chosen from the following: temp, feels_like, pressure, humidity," +
                        " main, description, wind, clouds\nThe date should be in format YYYY-MM-DD'T'HH:MM:SS " +
                        "(the T is without ') and it should be in the next 5 days\n\nAverageParameterValue - " +
                        "returns information about average parameter value in given date range (looks also into cache)" +
                        "\nThe call should be like:\nAverageParameterValue [CityName] [Parameter] [StartDate] [EndDate]\n" +
                        "The parameter is to be chosen from the following: temp, feels_like, pressure, humidity," +
                        "wind, clouds\nThe date should be in format YYYY-MM-DD'T'HH:MM:SS " +
                        "(the T is without ')\n\nBiggestVariationOnRectangle - " +
                        "returns information about the place within the chosen rectangle in which selected parameter " +
                        "changed the most. The call:\nBiggestVariationOnRectangle [Parameter] [Left Coordinate] " +
                        "[Bottom Coordinate] [Right Coordinate] [Top Coordinate]\nThe parameter is to be chosen from the " +
                        "following: temp, pressure, humidity, wind, clouds\nCoordinates should be for example " +
                        "25 25 27 27 (note that the area must be at most 25)\n\nMinAndMaxUltraviolet - returns information" +
                        " about minimal and maximal ultraviolet radiation in chosen coordinates. The call:\n" +
                        "MinAndMaxUltraviolet [Coordinates]\nThe Coordinates should be divided by \"|\" for example" +
                        " 20 30 | 40 20 | 25 25\n\nReliefMapWeather - returns weather map 1.0 for given parameters. The call" +
                        " should look like:\nReliefMapWeather [map_type] [zoom] [xtile] [ytile]\nThe map_type is to be chosen" +
                        " from the following: clouds, precipitation, pressure, wind, temp, zoom should be integer and" +
                        " xtile should be integer and ytile must be higher than 0\n\nParameterVariations - returns" +
                        " chart with value of given parameter for every given city for every 3 hours. The call:\n" +
                        "ParameterVariations [Cities] [Parameter] [StartDate] [EndDate]\nThe cities should be divided by " +
                        "\'|\' for example \'Rybnik | Krakow' \nParameter is to be chosen from the following: " +
                        "temp, feels_like, pressure, humidity, clouds, wind\nThe date should be in format YYYY-MM-DD'T'HH:MM:SS" +
                        " (the T is without ')\n\n");
    }

    /**
     * @param timestamp date in format YYYY-MM-DD'T'HH:MM:SS
     * @return time converted to unix time which is number of seconds counted from 1st January 1970
     */
    public static long unixTimeConverted(String timestamp) {
        timestamp = timestamp + ".000-0000";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date dt = sdf.parse(timestamp);
            long epoch = dt.getTime();
            return ((epoch / 1000));
        }
            catch (ParseException e) {
                e.printStackTrace();
        }
        return -1;
    }

    /**
     * @param args enter arguments defining programme's behaviour
     * @throws IOException when given arguments were wrong
     */
    public static void main(String[] args) throws IOException {
        new Cache().updateCache();
        if (args.length == 0) {
            help();
            throw new IOException("NO PARAMETERS PROVIDED!");
        }
        switch (args[0]) {
            case "CurrentWeather": {
                CurrentWeatherCommand currentWeatherCommand = new CurrentWeatherCommand();
                currentWeatherCommand.executeCommand(args[1]);
                System.out.println(currentWeatherCommand.getResult());
                break;
            }
            case "SelectedParameterWeather": {
                SelectedParameterWeatherCommand selectedParameterWeatherCommand = new SelectedParameterWeatherCommand();
                selectedParameterWeatherCommand.executeCommand(args[1], args[2].toLowerCase(), unixTimeConverted(args[3]));
                System.out.println(selectedParameterWeatherCommand.getResult());
                break;
            }
            case "BiggestVariationOnRectangle": {
                BiggestVariationOnRectangleCommand biggestVariationOnRectangleCommand = new BiggestVariationOnRectangleCommand();
                biggestVariationOnRectangleCommand.executeCommand(args[1],args[2],args[3],args[4],args[5]);
                System.out.println(biggestVariationOnRectangleCommand.getResult());
                break;
            }
            case "MinAndMaxUltraviolet": {
                MinAndMaxUltravioletCommand minAndMaxUltravioletCommand = new MinAndMaxUltravioletCommand();
                minAndMaxUltravioletCommand.executeCommand(args[1]);
                System.out.println(minAndMaxUltravioletCommand.getResult());
                break;
            }
            case "AverageParameterValue": {
                AverageParamaterValueCommand averageParamaterValueCommand = new AverageParamaterValueCommand();
                averageParamaterValueCommand.executeCommand(args[1],args[2],unixTimeConverted(args[3]),unixTimeConverted(args[4]));
                System.out.println(averageParamaterValueCommand.getResult());
                break;
            }
            case "ReliefMapWeather": {
                ReliefMapCommand reliefMapCommand = new ReliefMapCommand();
                reliefMapCommand.executeCommand(args[1],args[2],args[3],args[4]);
                System.out.println(reliefMapCommand.getResult());
                break;
            }
            case "ParameterVariations": {
                ParameterVarationsCommand parameterVarationsCommand = new ParameterVarationsCommand();
                parameterVarationsCommand.executeCommand(args[1],args[2],unixTimeConverted(args[3]),unixTimeConverted(args[4]));
                System.out.println(parameterVarationsCommand.getResult());
                break;
            }
            default: {
                help();
                throw new IOException("The functionality name was provided incorrectly!");
            }
        }
    }

}
