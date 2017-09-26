import states.GameStateManager;

import javax.swing.*;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class GUI extends JFrame {

    JPanel jPanel;
    GameStateManager gameStateManager;


    public GUI(GameStateManager gameStateManager) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 1000);
        this.gameStateManager = gameStateManager;
        addKeyListener(gameStateManager);

    }

    public JPanel getjPanel() {
        return jPanel;
    }


    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
        add(jPanel);
        repaint();
        doLayout();
    }

    public void removejPanel() {
        jPanel.removeAll();
        remove(jPanel);
    }




}
