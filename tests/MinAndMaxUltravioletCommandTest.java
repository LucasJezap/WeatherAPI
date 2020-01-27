import org.junit.Test;

import static org.junit.Assert.*;

public class MinAndMaxUltravioletCommandTest {

    @Test
    public void executeCommand() {
        MinAndMaxUltravioletCommand minAndMaxUltravioletCommand = new MinAndMaxUltravioletCommand();
        minAndMaxUltravioletCommand.executeCommand("20 20 | 30 30");
        assertNotEquals(null, minAndMaxUltravioletCommand.getResult());
        assertNotEquals(null, minAndMaxUltravioletCommand.getLocations());
    }

    @Test
    public void locationsTest() {
        MinAndMaxUltravioletCommand minAndMaxUltravioletCommand = new MinAndMaxUltravioletCommand();
        minAndMaxUltravioletCommand.executeCommand("20 20 | 30 30");
        assertTrue(minAndMaxUltravioletCommand.getLocations().size()>0);
    }

    @Test
    public void uVParameterTest() {
        MinAndMaxUltravioletCommand minAndMaxUltravioletCommand = new MinAndMaxUltravioletCommand();
        minAndMaxUltravioletCommand.executeCommand("20 20 | 30 30");
        assertNotEquals(null, minAndMaxUltravioletCommand.getLocations().get(0)[0].value);
        assertNotEquals(null, minAndMaxUltravioletCommand.getLocations().get(0)[0].date_iso);
        assertNotEquals(null, minAndMaxUltravioletCommand.getLocations().get(0)[0].lat);
        assertNotEquals(null, minAndMaxUltravioletCommand.getLocations().get(0)[0].lon);
    }
}