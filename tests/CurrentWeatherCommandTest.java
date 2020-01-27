import org.junit.Test;

import static org.junit.Assert.*;

public class CurrentWeatherCommandTest {

    @Test
    public void executeCommand() {
        CurrentWeatherCommand currentWeatherCommand = new CurrentWeatherCommand();
        currentWeatherCommand.executeCommand("Krakow");
        assertNotEquals(null, currentWeatherCommand.getResult());
        assertNotEquals(null, currentWeatherCommand.getCurrentWeather());
    }

    @Test
    public void currentWeather() {
        CurrentWeatherCommand currentWeatherCommand = new CurrentWeatherCommand();
        currentWeatherCommand.executeCommand("Krakow");
        assertNotEquals(null,currentWeatherCommand.getCurrentWeather().main.temp);
        assertNotEquals(null,currentWeatherCommand.getCurrentWeather().main.humidity);
        assertNotEquals(null,currentWeatherCommand.getCurrentWeather().main.pressure);
        assertNotEquals(null,currentWeatherCommand.getCurrentWeather().weather.get(0).main);
        assertNotEquals(null,currentWeatherCommand.getCurrentWeather().weather.get(0).description);
        assertNotEquals(null,currentWeatherCommand.getCurrentWeather().wind.speed);
        assertNotEquals(null,currentWeatherCommand.getCurrentWeather().clouds.all);
    }
}