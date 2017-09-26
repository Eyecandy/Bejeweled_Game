import states.GameState;
import states.StartingMenuState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class GameStateManager extends Observable implements KeyListener {
    GameState gameState;

    public GameStateManager() {
        init();
    }

    private void init() {
        StartingMenuState startingMenu = new StartingMenuState();
        startingMenu.init();
        setGameState(startingMenu);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }
}
