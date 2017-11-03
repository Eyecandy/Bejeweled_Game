package panels;

import javax.swing.*;
import java.awt.*;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class GUI extends JFrame {
    JPanel jPanel;
    private final int XCORD_FRAME = 100;
    private final int YCORD_FRAME = 100;
    private final int WIDTH_FRAME = 1000;
    private final int HEIGHT_FRAME = 1000;

    private static final GUI INSTANCE = new GUI();

    public static GUI getInstance() {
        return INSTANCE;
    }

    private GUI() {
        setTitle("Bejeweled");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setBounds(XCORD_FRAME, YCORD_FRAME, WIDTH_FRAME, HEIGHT_FRAME);
        setPreferredSize(new Dimension(1000,1000));
        setMinimumSize(new Dimension(1000,1000));
        setMaximumSize(new Dimension(1000,1000));
        this.setContentPane(new JPanel(new BorderLayout()));
    }

    public void setjPanel(JPanel jPanel) {
        this.getContentPane().removeAll();
        this.jPanel = jPanel;
        this.getContentPane().add(jPanel, BorderLayout.CENTER);
        //this.setContentPane(jPanel);
        this.pack();
        this.setVisible(true);
        //repaint();
        //jPanel.repaint();
        //Graphics g = jPanel.getGraphics();
        //jPanel.revalidate();
        //doLayout();
    }
}
