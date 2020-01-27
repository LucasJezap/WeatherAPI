import org.junit.Test;

import javax.imageio.IIOException;
import java.io.IOException;

public class IntegrationTests {

    @Test
    public void endToEnd() throws Exception {
        String args[] = {"AverageParameterValue","Krakow","wind","2020-01-28T10:00:00","2020-01-29T10:00:00"};
        Facade.main(args);
        args = new String[]{"BiggestVariationOnRectangle", "wind", "40", "40", "43", "43"};
        Facade.main(args);
        args = new String[]{"CurrentWeather","Krakow"};
        Facade.main(args);
        args = new String[]{"MinAndMaxUltraviolet","20 20 | 30 30"};
        Facade.main(args);
        args = new String[]{"SelectedParameterWeather","Krakow","wind","2020-01-28T10:00:00"};
        Facade.main(args);
        args = new String[]{"ReliefMapWeather","temp","1","1","1"};
        Facade.main(args);
        args = new String[]{"ParameterVariations","Rybnik | Krakow","temp","2020-01-28T10:00:00","2020-01-29T10:00:00"};
        Facade.main(args);
    }

    @Test(expected = NullPointerException.class)
    public void AverageParameterValueNoParameters() {
        AverageParamaterValueCommand averageParamaterValueCommand = new AverageParamaterValueCommand();
        averageParamaterValueCommand.executeCommand("","",0,0);
    }

    @Test(expected = NullPointerException.class)
    public void AverageParameterValueWrongParameters() {
        AverageParamaterValueCommand averageParamaterValueCommand = new AverageParamaterValueCommand();
        averageParamaterValueCommand.executeCommand("abc","xyz",0,0);
    }

    @Test
    public void AverageParameterValueCorrect() {
        AverageParamaterValueCommand averageParamaterValueCommand = new AverageParamaterValueCommand();
        averageParamaterValueCommand.executeCommand("Krakow","wind",1580205600, 1580292000);
    }

    @Test(expected = NullPointerException.class)
    public void BiggestVariationOnRectangleNoParameters() {
        BiggestVariationOnRectangleCommand biggestVariationOnRectangleCommand = new BiggestVariationOnRectangleCommand();
        biggestVariationOnRectangleCommand.executeCommand("","","","","");
    }

    @Test(expected = NullPointerException.class)
    public void BiggestVariationOnRectangleWrongParameters() {
        BiggestVariationOnRectangleCommand biggestVariationOnRectangleCommand = new BiggestVariationOnRectangleCommand();
        biggestVariationOnRectangleCommand.executeCommand("abc","a","b","c","d");
    }

    @Test
    public void BiggestVariationOnRectangleCorrect() {
        BiggestVariationOnRectangleCommand biggestVariationOnRectangleCommand = new BiggestVariationOnRectangleCommand();
        biggestVariationOnRectangleCommand.executeCommand("wind","50","52","53","55");
    }

    @Test(expected = NullPointerException.class)
    public void CurrentWeatherNoParameters() {
        CurrentWeatherCommand currentWeatherCommand = new CurrentWeatherCommand();
        currentWeatherCommand.executeCommand("");
    }

    @Test(expected = NullPointerException.class)
    public void CurrentWeatherWrongParameters() {
        CurrentWeatherCommand currentWeatherCommand = new CurrentWeatherCommand();
        currentWeatherCommand.executeCommand("abcdef");
    }

    @Test
    public void CurrentWeatherCorrect() {
        CurrentWeatherCommand currentWeatherCommand = new CurrentWeatherCommand();
        currentWeatherCommand.executeCommand("Krakow");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void MinAndMaxUltravioletNoParameters() {
        MinAndMaxUltravioletCommand minAndMaxUltravioletCommand = new MinAndMaxUltravioletCommand();
        minAndMaxUltravioletCommand.executeCommand("");
    }

    @Test(expected = NullPointerException.class)
    public void MinAndMaxUltravioletWrongParameters() {
        MinAndMaxUltravioletCommand minAndMaxUltravioletCommand = new MinAndMaxUltravioletCommand();
        minAndMaxUltravioletCommand.executeCommand("abc def | def abc");
    }

    @Test
    public void MinAndMaxUltravioletCorrect() {
        MinAndMaxUltravioletCommand minAndMaxUltravioletCommand = new MinAndMaxUltravioletCommand();
        minAndMaxUltravioletCommand.executeCommand("20 20 | 30 30");
    }

    @Test(expected = NullPointerException.class)
    public void SelectedParameterWeatherNoParameters() throws IOException {
        SelectedParameterWeatherCommand selectedParameterWeatherCommand = new SelectedParameterWeatherCommand();
        selectedParameterWeatherCommand.executeCommand("","",0);
    }

    @Test(expected = NullPointerException.class)
    public void SelectedParameterWeatherWrongParameters() throws IOException {
        SelectedParameterWeatherCommand selectedParameterWeatherCommand = new SelectedParameterWeatherCommand();
        selectedParameterWeatherCommand.executeCommand("abc","def",0);
    }

    @Test
    public void SelectedParameterWeatherCorrect() throws IOException {
        SelectedParameterWeatherCommand selectedParameterWeatherCommand = new SelectedParameterWeatherCommand();
        selectedParameterWeatherCommand.executeCommand("Krakow","wind",1580292000);
    }

    @Test(expected = IIOException.class)
    public void ReliefMapNoParameters() throws IOException {
        ReliefMapCommand reliefMapCommand = new ReliefMapCommand();
        reliefMapCommand.executeCommand("","","","");
    }

    @Test(expected = IIOException.class)
    public void ReliefMapWrongParameters() throws IOException {
        ReliefMapCommand reliefMapCommand = new ReliefMapCommand();
        reliefMapCommand.executeCommand("abc","def","abc","def");
    }

    @Test
    public void ReliefMapCorrect() throws IOException {
        ReliefMapCommand reliefMapCommand = new ReliefMapCommand();
        reliefMapCommand.executeCommand("temp","1","1","1");
    }

    @Test(expected = NullPointerException.class)
    public void ParameterVariationsNoParameters() throws IOException {
        ParameterVarationsCommand parameterVarationsCommand = new ParameterVarationsCommand();
        parameterVarationsCommand.executeCommand("","",0,0);
    }

    @Test(expected = NullPointerException.class)
    public void ParameterVariationsWrongParameters() throws IOException {
        ParameterVarationsCommand parameterVarationsCommand = new ParameterVarationsCommand();
        parameterVarationsCommand.executeCommand("abc","def",-1,-1);
    }

    @Test
    public void ParameterVariationsCorrect() throws IOException {
        ParameterVarationsCommand parameterVarationsCommand = new ParameterVarationsCommand();
        parameterVarationsCommand.executeCommand("Rybnik | Krakow","humidity",1580205600,1580292000);
    }
}
