import states.StartingMenu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class GameStateManager implements KeyListener {

    public GameStateManager() {
        init();
    }

    private void init() {
        StartingMenu startingMenu = new StartingMenu();
        startingMenu.setTitle("Start Menu");
    }


    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }
}
