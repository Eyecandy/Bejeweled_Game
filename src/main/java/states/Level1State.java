package states;

import javax.swing.*;
import java.awt.*;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class Level1State extends GameState {
    JPanel jPanel;
    private final Integer X = 475;
    private final Integer Y = 75;
    private final Integer W = 300;
    private final Integer H = 100;

    private final String title = "LEVEL 1";
    private final Color titleColor = Color.GREEN;
    Font titleFont = new Font("Script",Font.BOLD,55);

    public void init() {

        jPanel = new JPanel();
        JLabel jLabel = new JLabel(title);
        jLabel.setBounds(X-70,Y,W+W,H);
        jLabel.setFont(titleFont);
        jPanel.setLayout(null);
        jPanel.add(jLabel);
        jPanel.setBackground(Color.RED);
        jPanel.setBounds(100, 100, 1000, 1000);


    }

    public void update() {

    }

    public void draw(Graphics2D g) {

    }

    public void keyReleased(int k) {

    }

    public void keyPressed(int k) {

    }

    public JPanel getJpanel() {
        return jPanel;
    }



}
