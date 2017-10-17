package states;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;


/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class GameStateManager extends Observable implements KeyListener{
    private GameState gameState;

    private GameState startingMenuState;

    public GameStateManager() {
        init();
    }

    private void init() {
        StartingMenuState startingMenu = new StartingMenuState();
        startingMenu.init();
        startingMenuState = startingMenu;
        startingMenu.setGameStateManager(this);
        setGameState(startingMenu);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        setChanged();
        notifyObservers();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        gameState.keyPressed(e.getKeyCode());
    }
    public void keyReleased(KeyEvent e) {
    }

    public void setGameStateToStartingMenu() {
        System.out.println(startingMenuState);
        this.gameState = startingMenuState;
        setChanged();
        notifyObservers();
    }






}
