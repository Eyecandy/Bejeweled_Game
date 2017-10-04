package states;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class StartingMenuState extends GameState {
    JPanel jPanel;
    private final String title = "Start Menu";
    Font titleFont = new Font("Script",Font.BOLD,55);

    private int currentSelect = 0;
    private Color backGroundColor = Color.pink;

    private Font selectedFont = new Font("Script",Font.BOLD,45);
    private Color selectedColor = Color.yellow;

    private Font normalFont = new Font("Script",Font.PLAIN,30);
    private Color normalColor = Color.black;

    private final Integer X = getXJlabelCord();
    private final Integer Y = getYJlabelCord();
    private final Integer W = getJlabelWidth();
    private final Integer H = getJlabelHeight();
    private String[] options = {
            "Start Game",
            "Help",
            "Quit Game"}; // Menu Options
    private JLabel[] optionsLabels = new JLabel[3];

    public void init() {
        jPanel = new JPanel();
        JLabel jLabel = new JLabel(title);
        jLabel.setBounds(X-70,Y,W+W,H);
        jLabel.setFont(titleFont);
        jPanel.setLayout(null);
        jPanel.add(jLabel);
        jPanel.setBounds(100, 100, 1000, 1000);
        jPanel.setBackground(backGroundColor);
        int deduct = 100;
        for (int i =0;i < options.length;i++) {
            JLabel jLabel1 = new JLabel();
            jLabel1.setFont(normalFont);
            jLabel1.setForeground(normalColor);
            jLabel1.setText(options[i]);
            jLabel1.setBounds(X,Y+deduct,W,H);
            deduct += 60;
            jPanel.add(jLabel1);
            optionsLabels[i] = jLabel1;
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
        else if (k == KeyEvent.VK_ENTER && currentSelect == 0) {
            GameState gameState = new Level1State();
            gameState.init();
            getGameStateManager().setGameState(gameState);


        }

    }

    public JPanel getJpanel() {
        return jPanel;
    }




}
