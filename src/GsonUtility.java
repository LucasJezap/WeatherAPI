import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

/**
 * Class designed for parsing URL call given by string to json response represented by string. It also check if
 * given URL call can be found in Cache.
 */
public class GsonUtility {

    /**
     * @param u URL call
     * @return URL response (json) represented by string
     */
    public String getJson(String u) {
        Cache cache = new Cache();
        String json = "";
        if(cache.isInCache(u)) {
            json = cache.findInCache(u);
        }
        else {
            try {
                URL url = new URL(u);
                BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));
                json = input.lines().collect(Collectors.joining());
                cache.addToCache(u,json);
            } catch (IOException e) {
            }
        }
        return json;
    }
}
