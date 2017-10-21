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

    public GUI() {
        setTitle("Bejeweled");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(XCORD_FRAME, YCORD_FRAME, WIDTH_FRAME, HEIGHT_FRAME);
        setPreferredSize(new Dimension(1000,1000));
    }

    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
        setContentPane(jPanel);
        pack();
        setVisible(true);
        repaint();
        doLayout();
    }
}
