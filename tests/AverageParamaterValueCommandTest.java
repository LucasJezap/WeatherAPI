import org.junit.Test;

import static org.junit.Assert.*;

public class AverageParamaterValueCommandTest {

    @Test
    public void executeCommand() {
        AverageParamaterValueCommand averageParamaterValueCommand = new AverageParamaterValueCommand();
        averageParamaterValueCommand.executeCommand("Krakow", "wind", 1579514400, 1579687200);
        assertNotEquals(null, averageParamaterValueCommand.getResult());
        assertNotEquals(null, averageParamaterValueCommand.getList());
    }

    @Test
    public void forecastListTest() {
        AverageParamaterValueCommand averageParamaterValueCommand = new AverageParamaterValueCommand();
        averageParamaterValueCommand.executeCommand("Krakow", "wind", 1580205600, 1580292000);
        assertTrue(averageParamaterValueCommand.getList().list.size()>0);
    }

    @Test
    public void Forecast() {
        AverageParamaterValueCommand averageParamaterValueCommand = new AverageParamaterValueCommand();
        averageParamaterValueCommand.executeCommand("Krakow", "wind", 1580205600, 1580292000);
        assertNotEquals(averageParamaterValueCommand.getList().list.get(0).dt,null);
        assertNotEquals(averageParamaterValueCommand.getList().list.get(0).main.feels_like,null);
        assertNotEquals(averageParamaterValueCommand.getList().list.get(0).main.temp,null);
        assertNotEquals(averageParamaterValueCommand.getList().list.get(0).main.humidity,null);
        assertNotEquals(averageParamaterValueCommand.getList().list.get(0).main.pressure,null);
        assertNotEquals(averageParamaterValueCommand.getList().list.get(0).clouds.all,null);
        assertNotEquals(averageParamaterValueCommand.getList().list.get(0).wind.speed,null);
    }
}