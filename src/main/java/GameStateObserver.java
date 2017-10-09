import states.GameStateManager;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class GameStateObserver implements Observer {
    GameStateManager gameStateManager;
    GUI gui;
    public GameStateObserver(GameStateManager gameStateManager,GUI gui) {
        this.gui = gui;
        this.gameStateManager = gameStateManager;
    }

    public void update(Observable o, Object arg) {
        gui.setjPanel(gameStateManager.getGameState().getJpanel());
    }
}
