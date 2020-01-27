import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class representing Cache. It's designed to work on two files called ".../CACHE.json" and ".../URLS.txt" which
 * task is to help the programme to work faster on already found API calls. The CACHE.json file has both URL calls
 * and responses while URLS.txt have only URL calls and is used to faster check if given URL was already called.
 */
public class Cache {
    /**
     * filePath - file path to the CACHE.json file
     * filePath2 - file path to the URLS.txt file
     * filePath3 - file path to the time.txt
     */
    private String filePath = "C:/Users/yyy/Desktop/AGH/Programowanie Obiektowe/WeatherAPI/CACHE.json";
    private String filePath2 = "C:/Users/yyy/Desktop/AGH/Programowanie Obiektowe/WeatherAPI/URLS.txt";
    private String filePath3 = "C:/Users/yyy/Desktop/AGH/Programowanie Obiektowe/WeatherAPI/time.txt";

    /**
     * @param cityName name of the city
     * @return list of json strings (matching the city) ready to be processed
     */
    public ArrayList<String> searchForecastThroughCache(String cityName) {
        ArrayList<String> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                if(line.contains("forecast?q="+cityName)) {
                    line = reader.readLine();
                    result.add(line);
                }
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Adds URL call to Cache
     * @param URL URL call
     * @param json URL response
     * @throws IOException when files doesnt exist or URL is inappropriate
     */
    public void addToCache(String URL, String json) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath2,true));
        writer.append(URL);
        writer.append("\n");
        writer.close();
        BufferedWriter writer2 = new BufferedWriter(new FileWriter(filePath,true));
        writer2.append(URL);
        writer2.append("\n");
        writer2.append(json);
        writer2.append("\n\n");
        writer2.close();
    }

    /**
     * @param URL URL call
     * @return boolean answer if given URL was in Cache
     */
    public boolean isInCache(String URL) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath2));
            String line = reader.readLine();
            while (line != null) {
                if(line.equals(URL))
                    return true;
                line = reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param URL URL call
     * @return URL response (if call was in Cache)
     */
    public String findInCache(String URL) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                if(line.equals(URL)) {
                    String json = "";
                    line = reader.readLine();
                    while (!line.trim().isEmpty()) {
                        json += line;
                        line = reader.readLine();
                    }
                    return json;
                }
                line= reader.readLine();
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Clears Cache
     */
    public void clearCache() {
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.close();
            FileWriter writer2 = new FileWriter(filePath2);
            writer2.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Updates Cache if it's outdated (1 hour)
     */
    public void updateCache() {
        try {
            BufferedReader timeReader = new BufferedReader(new FileReader(filePath3));
            String time = timeReader.readLine();
            if (System.currentTimeMillis()/1000-Long.parseLong(time)/1000<3600)
                return;
            BufferedWriter timeWriter = new BufferedWriter(new FileWriter(filePath3));
            timeWriter.write(Long.toString(System.currentTimeMillis()));
            timeWriter.close();
            BufferedReader reader = new BufferedReader(new FileReader(filePath2));
            ArrayList<String> lines = new ArrayList<>();
            String l = reader.readLine();
            while(l != null) {
                lines.add(l);
                l = reader.readLine();
            }
            clearCache();
            for (String line: lines) {
                URL url = new URL(line);
                BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));
                String json = input.lines().collect(Collectors.joining());
                addToCache(line,json);
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFilePath2() {
        return filePath2;
    }
}
