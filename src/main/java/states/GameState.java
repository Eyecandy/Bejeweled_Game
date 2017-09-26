package states;
import javax.swing.*;
import java.util.Observable;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */


public abstract class GameState {
    public abstract void init();
    public abstract void update();
    public abstract void draw(java.awt.Graphics2D g);
    public abstract void keyReleased(int k);
    public abstract void keyPressed(int k);
    public abstract JPanel getJpanel();
}


