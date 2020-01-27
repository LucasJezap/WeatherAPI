import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void searchForecastThroughCache() throws IOException {
        SelectedParameterWeatherCommand selectedParameterWeatherCommand = new SelectedParameterWeatherCommand();
        selectedParameterWeatherCommand.executeCommand("Krakow","wind",1580292000);
        Cache cache = new Cache();
        assertTrue(cache.searchForecastThroughCache("Krakow").size()>0);
    }

    @Test
    public void addToCache() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new Cache().getFilePath2()));
        int count = 0;
        String line = reader.readLine();
        while(line != null) {
            count += 1;
            line = reader.readLine();
        }
        new Cache().addToCache("testURL","testResponse");
        BufferedReader reader2 = new BufferedReader(new FileReader(new Cache().getFilePath2()));
        int count2 = 0;
        String line2 = reader2.readLine();
        while(line2 != null) {
            count2 += 1;
            line2 = reader2.readLine();
        }
        assertEquals(count+1,count2);
    }

    @Test
    public void isInCache() throws IOException {
        new Cache().addToCache("testURL","testResponse");
        assertTrue(new Cache().isInCache("testURL"));
        assertFalse(new Cache().isInCache("testResponse"));
    }

    @Test
    public void findInCache() throws IOException {
        new Cache().addToCache("testURL","testResponse");
        assertEquals("testResponse",new Cache().findInCache("testURL"));
        assertNotEquals("testURL",new Cache().findInCache("testURL"));
    }

    @Test
    public void updateCache() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new Cache().getFilePath2()));
        int count = 0;
        String line = reader.readLine();
        while(line != null) {
            count += 1;
            line = reader.readLine();
        }
        new Cache().updateCache();
        BufferedReader reader2 = new BufferedReader(new FileReader(new Cache().getFilePath2()));
        int count2 = 0;
        String line2 = reader2.readLine();
        while(line2 != null) {
            count2 += 1;
            line2 = reader2.readLine();
        }
        assertEquals(count,count2);
    }

    @Test
    public void clearCache() throws IOException {
        new Cache().clearCache();
        BufferedReader reader = new BufferedReader(new FileReader(new Cache().getFilePath2()));
        int count = 0;
        String line = reader.readLine();
        while(line != null) {
            count += 1;
            line = reader.readLine();
        }
        assertEquals(0,count);
    }
}