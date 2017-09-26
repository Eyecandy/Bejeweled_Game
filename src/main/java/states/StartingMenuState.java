package states;

import javax.swing.*;
import java.awt.*;


/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class StartingMenuState extends GameState {
    JPanel jPanel;
    public void init() {
        jPanel = new JPanel();
        JLabel jLabel = new JLabel();
        jLabel.setText("START MENU");
        jPanel.add(jLabel);
        jPanel.setVisible(true);
        jPanel.setBounds(100, 100, 1000, 1000);
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
