import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * This class belongs to Command design patter. It's task is to display weather map 1.0 for given type, zoom,
 * xtile and ytile values.
 */
public class ReliefMapCommand {
    /**
     * result - result of the command after executing command
     */
    private StringBuilder result = new StringBuilder();

    /**
     * @param name - name of map type
     * @param zoom - value of zoom (Integer)
     * @param xtile - value of xtile (Decimal 0-1)
     * @param ytile - value of ytile (Decimal 0-1)
     * @throws IOException when provided arguments are incorrect
     */
    public void executeCommand(String name, String zoom, String xtile, String ytile) throws IOException {
        URL url = new URL("https://tile.openweathermap.org/map/"+ name + "_new/"+ zoom +
                "/" + xtile + "/" + ytile + "?appid=99ee4197968efacdb5fbb1b28f73543e");
        Image image = ImageIO.read(url).getScaledInstance(1900,1000,Image.SCALE_SMOOTH);
        JFrame frame = new JFrame();
        JButton b = new JButton("Type " + name + " zoom =" + zoom + " xtile =" + xtile + " ytile =" + ytile);
        b.setBounds(0,0,400,100);
        frame.add(b);
        JLabel label = new JLabel(new ImageIcon(image));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        result.append("Now displaying weather map 1.0 for parameter " + name + " and zoom = " + zoom + " and" +
                " xtile = " + xtile + ", ytile =" + ytile);
    }

    public StringBuilder getResult() {
        return result;
    }

    public void setResult(StringBuilder result) {
        this.result = result;
    }
}
