import panels.GUI;
import states.GameStateManager;

import javax.swing.*;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class Controller {
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.start();
    }
    private void start() {

        //Animation animation = new Animation();
        GameStateManager gameStateManager = new GameStateManager();
        JPanel jPanel =  gameStateManager.getGameState().getJPanel();
        jPanel.setVisible(true);
        GUI gui = GUI.getInstance();
        gui.setjPanel(jPanel);
        gui.addKeyListener(gameStateManager);
        GameStateObserver gameStateObserver = new GameStateObserver(gameStateManager,gui);
        gameStateManager.addObserver(gameStateObserver);
    }
}
