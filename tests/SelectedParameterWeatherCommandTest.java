import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SelectedParameterWeatherCommandTest {

    @Test
    public void executeCommand() throws IOException {
        SelectedParameterWeatherCommand selectedParameterWeatherCommand = new SelectedParameterWeatherCommand();
        selectedParameterWeatherCommand.executeCommand("Krakow","wind",1580292000);
        assertNotEquals(null, selectedParameterWeatherCommand.getResult());
        assertNotEquals(null, selectedParameterWeatherCommand.getList());
    }

    @Test
    public void forecastListTest() throws IOException {
        SelectedParameterWeatherCommand selectedParameterWeatherCommand = new SelectedParameterWeatherCommand();
        selectedParameterWeatherCommand.executeCommand("Krakow","wind",1580292000);
        assertTrue(selectedParameterWeatherCommand.getList().list.size()>0);
    }

    @Test
    public void uVParameterTest() throws IOException {
        SelectedParameterWeatherCommand selectedParameterWeatherCommand = new SelectedParameterWeatherCommand();
        selectedParameterWeatherCommand.executeCommand("Krakow","wind",1580292000);
        assertNotEquals(null, selectedParameterWeatherCommand.getList().list.get(0).dt);
        assertNotEquals(null, selectedParameterWeatherCommand.getList().list.get(0).main.feels_like);
        assertNotEquals(null, selectedParameterWeatherCommand.getList().list.get(0).main.temp);
        assertNotEquals(null, selectedParameterWeatherCommand.getList().list.get(0).main.humidity);
        assertNotEquals(null, selectedParameterWeatherCommand.getList().list.get(0).main.pressure);
        assertNotEquals(null, selectedParameterWeatherCommand.getList().list.get(0).weather.get(0).description);
        assertNotEquals(null, selectedParameterWeatherCommand.getList().list.get(0).weather.get(0).main);
        assertNotEquals(null, selectedParameterWeatherCommand.getList().list.get(0).wind.speed);
        assertNotEquals(null, selectedParameterWeatherCommand.getList().list.get(0).clouds.all);
    }
}