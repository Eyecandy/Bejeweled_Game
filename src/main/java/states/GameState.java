package states;
import javax.swing.*;
import java.util.Observable;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public abstract class GameState extends Observable {


    private final Integer jpanelXCord = 475;
    private final Integer jpanelYCord = 75;
    private final Integer width = 300;
    private final Integer height = 100;

    private GameStateManager gameStateManager;
    public abstract void init();
    public abstract void update();
    public abstract void keyPressed(int k);
    public abstract JPanel getJpanel();

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public void setGameStateManager(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }
    public Integer getXJlabelCord() {
        return jpanelXCord;
    }

    public Integer getYJlabelCord() {
        return jpanelYCord;
    }

    public Integer getJlabelWidth() {
        return width;
    }

    public Integer getJlabelHeight() {
        return height;
    }


}


