package states;
import javax.swing.*;
import java.awt.*;
import java.util.Observable;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public abstract class GameState extends Observable {



    private GameStateManager gameStateManager;
    public abstract void init();
    public abstract void update();
    public abstract void keyPressed(int k);
    public abstract JPanel getJpanel();
    public abstract void setXYWH(int x,int y, int w,int h);

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public void setGameStateManager(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    public JLabel createJlabel(String text, Font font, Color foreground) {
        JLabel jLabel = new JLabel();
        jLabel.setText(text);
        jLabel.setFont(font);
        jLabel.setForeground(foreground);
        return jLabel;

    }


}


