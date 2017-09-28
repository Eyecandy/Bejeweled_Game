import states.GameStateManager;

import javax.swing.*;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class GUI extends JFrame {

    JPanel jPanel;



    public GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 1000);

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
