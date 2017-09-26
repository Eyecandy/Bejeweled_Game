package states;

import javax.swing.*;
import java.awt.*;


/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class StartingMenuState extends GameState {
    JPanel jPanel;
    private final String title = "Start Menu";
    private final Color titleColor = Color.GREEN;
    private final Integer X = 475;
    private final Integer Y = 75;
    private final Integer W = 90;
    private final Integer H = 90;
    private String[] options = {
            "Start Game",
            "Help",
            "Quit Game"}; // Menu Options

    public void init() {
        jPanel = new JPanel();
        JLabel jLabel = new JLabel();
        jLabel.setText(title);
        jLabel.setBounds(   X,Y,W,H);
        jPanel.setLayout(null);
        jPanel.add(jLabel);
        jPanel.setVisible(true);
        jPanel.setBounds(100, 100, 1000, 1000);
        jPanel.setBackground(Color.DARK_GRAY);
        int deduct = 40;
        for (int i =0;i < options.length;i++) {
            JLabel jLabel1 = new JLabel();
            jLabel1.setText(options[i]);
            jLabel1.setBounds(X,Y+deduct,W,H);
            deduct += 40;
            jPanel.add(jLabel1);
        }




    }

    public void setTitle(String title) {

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
        System.out.println(jPanel);
        return jPanel;
    }

    public void setJPanel(JPanel jPanel) {
        System.out.println("Set");
        this.jPanel = jPanel;
    }

}
