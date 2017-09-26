import javax.swing.*;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class Controller {
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.start();
    }
    public void start() {

        Animation animation = new Animation();
        GameStateManager gameStateManager = new GameStateManager();
        JPanel jPanel =  gameStateManager.getGameState().getJpanel();
        jPanel.setVisible(true);
        GUI gui = new GUI();
        gui.setTitle("Bejeweled");
        gui.setVisible(true);
        //gui.add(jPanel);
        gui.setjPanel(jPanel);






    }


}
