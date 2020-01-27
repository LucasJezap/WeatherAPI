import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ReliefMapCommandTest {

    @Test
    public void executeCommand() throws IOException {
        ReliefMapCommand reliefMapCommand = new ReliefMapCommand();
        reliefMapCommand.executeCommand("temp","1","1","1");
        assertNotEquals(null, reliefMapCommand.getResult());
    }
}