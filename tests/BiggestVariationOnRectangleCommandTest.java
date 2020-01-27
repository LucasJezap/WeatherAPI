import org.junit.Test;

import static org.junit.Assert.*;

public class BiggestVariationOnRectangleCommandTest {

    @Test
    public void executeCommand() {
        BiggestVariationOnRectangleCommand biggestVariationOnRectangleCommand = new BiggestVariationOnRectangleCommand();
        biggestVariationOnRectangleCommand.executeCommand("wind","40","40","43","43");
        assertNotEquals(null,biggestVariationOnRectangleCommand.getResult());
        assertNotEquals(null,biggestVariationOnRectangleCommand.getNames());
    }

    @Test
    public void parameterListTest() {
        BiggestVariationOnRectangleCommand biggestVariationOnRectangleCommand = new BiggestVariationOnRectangleCommand();
        biggestVariationOnRectangleCommand.executeCommand("wind","40","40","43","43");
        assertTrue(biggestVariationOnRectangleCommand.getPlist().size()>0);
    }

    @Test
    public void parameterTest() {
        BiggestVariationOnRectangleCommand biggestVariationOnRectangleCommand = new BiggestVariationOnRectangleCommand();
        biggestVariationOnRectangleCommand.executeCommand("wind","40","40","43","43");
        assertNotEquals(null,biggestVariationOnRectangleCommand.getPlist().get(0).list.get(0).main.humidity);
        assertNotEquals(null,biggestVariationOnRectangleCommand.getPlist().get(0).list.get(0).main.pressure);
        assertNotEquals(null,biggestVariationOnRectangleCommand.getPlist().get(0).list.get(0).main.temp);
        assertNotEquals(null,biggestVariationOnRectangleCommand.getPlist().get(0).list.get(0).clouds.all);
        assertNotEquals(null,biggestVariationOnRectangleCommand.getPlist().get(0).list.get(0).wind.speed);
    }
}