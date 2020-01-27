import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class UnixTimeConverterTest {

    public long unixTimeConverted(String timestamp) {
        timestamp = timestamp + ".000-0000";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date dt = sdf.parse(timestamp);
            long epoch = dt.getTime();
            return ((epoch / 1000));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Test
    public void unixTimeConverted() {
        assertEquals(1603189220, unixTimeConverted("2020-10-20T10:20:20"));
        assertEquals(1760091010, unixTimeConverted("2025-10-10T10:10:10"));
        assertNotEquals(1760091011, unixTimeConverted("2025-10-10T10:10:10"));
    }
}