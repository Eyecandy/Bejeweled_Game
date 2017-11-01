package states;

import panels.GamePanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;



/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class StartingMenuState extends GameState {
    GamePanel gamePanel;
    private final String title = "Start Menu";
    Font titleFont = new Font("Script",Font.BOLD,55);

    private Color titleColor = Color.BLACK;

    private int currentSelect = 0;
    private Color backGroundColor = Color.pink;

    private Font selectedFont = new Font("Script",Font.BOLD,45);
    private Color selectedColor = Color.yellow;

    private Font normalFont = new Font("Script",Font.PLAIN,30);
    private Color normalColor = Color.black;
    private final int START_GAME= 0;
    private final int OPTIONS= 1;
    private final int QUIT = 2;

    private int xCord;
    private int yCord;
    private int width;
    private int height;

    private String[] options = {
            "Start Game",
            "Help",
            "Quit Game"}; // Menu Options
    private JLabel[] optionsLabels = new JLabel[3];


    public void init() {
        gamePanel = new GamePanel(backGroundColor,titleFont);
        setXYWH(gamePanel.getX(),gamePanel.getY(),gamePanel.getHEIGHT(),gamePanel.getHEIGHT());
        JLabel titleLabel = createJlabel(title,titleFont,titleColor);
        titleLabel.setBounds(xCord-70,yCord,width*4,height);
        gamePanel.add(titleLabel);
        int deduct = 100;
        for (int i =0;i < options.length;i++) {
            JLabel optionLabel = createJlabel(options[i],normalFont,normalColor);
            optionLabel.setBounds(xCord,yCord+deduct,width*3,height);
            deduct += 60;
            gamePanel.add(optionLabel);
            optionsLabels[i] = optionLabel;
        }
        optionsLabels[currentSelect].setFont(selectedFont);
        optionsLabels[currentSelect].setForeground(selectedColor);
    }

    public void update() {


    }
    private void updateJlabelOptions(int old) {
        optionsLabels[old].setFont(normalFont);
        optionsLabels[old].setForeground(normalColor);
        optionsLabels[currentSelect].setFont(selectedFont);
        optionsLabels[currentSelect].setForeground(selectedColor);

    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_DOWN) {
            if (currentSelect != options.length-1) {
                int old = currentSelect;
                currentSelect +=1;
                updateJlabelOptions(old);
            }
        }
        else if (k == KeyEvent.VK_UP) {
            if (currentSelect != 0) {
                int old = currentSelect;
                currentSelect -=1;
                updateJlabelOptions(old);
            }
        }
        else if (k == KeyEvent.VK_ENTER && currentSelect == START_GAME) {
            GameState gameState = new Level1State();
            gameState.init();
            gameState.setGameStateManager(getGameStateManager());
            getGameStateManager().setGameState(gameState);
        }

        else if (k == KeyEvent.VK_ENTER && currentSelect == QUIT) {
            System.exit(0);

        }
    }



    public JPanel getJPanel() {
        return gamePanel;
    }


    public void setXYWH(int x,int y,int w,int h) {
        xCord = x; yCord=y; width = w; height = h;
    }



}
