package states;

import javax.swing.*;
import java.awt.*;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class Level1State extends GameState {
    JPanel jPanel;
    private final Integer X = getXJlabelCord();
    private final Integer Y = getYJlabelCord();
    private final Integer W = getJlabelHeight();
    private final Integer H = getJlabelWidth();
    private final String title = "LEVEL 1";

    Font titleFont = new Font("Script",Font.BOLD,23);

    public void init() {
        jPanel = new JPanel();
        jPanel.setOpaque(true);
        JLabel jLabel = new JLabel(title);
        jLabel.setBackground(Color.BLUE);
        //jLabel.setBounds(100,100,300,200);
        jLabel.setBounds(X-70,Y-100,W+W,H);
        jLabel.setFont(titleFont);
        jPanel.setLayout(new BorderLayout());
        jPanel.add(jLabel, BorderLayout.CENTER);
        jPanel.setBounds(100, 100, 1000, 1000);
        //jPanel.setBackground(Color.RED);

    }

    public void update() {

    }



    public void keyPressed(int k) {

    }

    public JPanel getJpanel() {
        return jPanel;
    }



}
