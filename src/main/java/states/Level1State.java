package states;

import panels.GamePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class Level1State extends GameState {
    GamePanel gamePanel;
    private Color background = Color.MAGENTA;
    private Font titleFont = new Font("Script",Font.BOLD,23);
    private Color titleColor = Color.BLACK;

    private int xCord;
    private int yCord;
    private int width;
    private int height;
    private final String title = "LEVEL 1";

    public void init() {
        gamePanel = new GamePanel(background,titleFont);
        setXYWH(gamePanel.getX(),gamePanel.getY(),gamePanel.getHeight(),gamePanel.getWidth());
        JLabel titleLabel = createJlabel(title,titleFont,titleColor);
        titleLabel.setBounds(xCord-70,yCord-100,width*2,height);
        gamePanel.add(titleLabel);
    }
    public void update() {

    }

    public void keyPressed(int k) {
    }

    public JPanel getJpanel() {
        return gamePanel;
    }

    public void setXYWH(int x,int y,int w,int h) {
        xCord = x; yCord=y; width = w; height = h;
    }
}
