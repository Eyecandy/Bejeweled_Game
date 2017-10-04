package panels;

import javafx.scene.layout.Background;

import javax.swing.*;
import java.awt.*;

/**
 * Created by joakimnilfjord on 10/2/2017 AD.
 */
public class GamePanel extends JPanel {

    private final Integer XCORD = 475;
    private final Integer YCORD = 75;
    private final Integer WIDTH = 300;
    private final Integer HEIGHT = 100;

    public GamePanel(Color background,Font title) {
        setBounds(XCORD,YCORD,WIDTH,HEIGHT);
        setLayout(null);
        setBackground(background);
        setFont(title);

    }

    public Integer getXCORD() {
        return XCORD;
    }

    public Integer getYCORD() {
        return YCORD;
    }

    public Integer getWIDTH() {
        return WIDTH;
    }

    public Integer getHEIGHT() {
        return HEIGHT;
    }



}
