import org.junit.Test;

import static org.junit.Assert.*;

public class ParameterVarationsCommandTest {

    @Test
    public void executeCommand() {
        ParameterVarationsCommand parameterVarationsCommand = new ParameterVarationsCommand();
        parameterVarationsCommand.executeCommand("Krakow | Rybnik","wind",1579683600,1579856400);
        assertNotEquals(null, parameterVarationsCommand.getResult());
        assertNotEquals(null, parameterVarationsCommand.getForecasts());
    }

    @Test
    public void addChart() {
        ParameterVarationsCommand parameterVarationsCommand = new ParameterVarationsCommand();
        int size = parameterVarationsCommand.getResult().capacity();
        parameterVarationsCommand.executeCommand("Krakow | Rybnik","wind",1579683600,1579856400);
        assertNotEquals(size,parameterVarationsCommand.getResult().capacity());
    }

    @Test
    public void addToResult() {
        ParameterVarationsCommand parameterVarationsCommand = new ParameterVarationsCommand();
        int size = parameterVarationsCommand.getResult().capacity();
        parameterVarationsCommand.executeCommand("Krakow | Rybnik","wind",1579683600,1579856400);
        assertNotEquals(size,parameterVarationsCommand.getResult().capacity());
    }

    @Test
    public void unixToDate() {
        ParameterVarationsCommand parameterVarationsCommand = new ParameterVarationsCommand();
        assertTrue("2020-01-24 09:00:00 ".equals(parameterVarationsCommand.unixToDate(1579856400)));
        assertFalse("2020-01-24 10:00:00 ".equals(parameterVarationsCommand.unixToDate(1579856400)));
    }
}