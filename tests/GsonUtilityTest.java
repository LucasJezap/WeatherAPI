import org.junit.Test;

import static org.junit.Assert.*;

public class GsonUtilityTest {

    @Test
    public void getJson() {
        String json = new GsonUtility().getJson("https://api.openweathermap.org/data/2.5/bo" +
                "x/city?bbox=40,40,45,45,8&appid=99ee4197968efacdb5fbb1b28f73543e");
        assertNotEquals("",json);
    }
}