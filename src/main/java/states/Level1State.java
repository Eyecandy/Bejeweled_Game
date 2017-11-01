package states;

import panels.GameBoard;
import panels.GamePanel;
import tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class Level1State extends GameState {
    private GamePanel gamePanel;
    private Color background = Color.LIGHT_GRAY;
    private Font titleFont = new Font("Script",Font.BOLD,23);
    private Color titleColor = Color.BLACK;
    public GameBoard board;
    private final String title = "LEVEL 1";

    private final int SIZE = 8;
    private final int DIMENSION = 600;

    public void init() {

        gamePanel = new GamePanel(background,titleFont);
        GameLogic gl = new GameLogic(SIZE,SIZE);

        BoardObserver boardObserver = new BoardObserver(this,gl);
        gl.addObserver(boardObserver);
        board = new GameBoard(gl, SIZE, DIMENSION, this);

        Tile mat[][] = gl.getBoard();
        gamePanel.add(board);
        board.render(mat);
    }
    public void update() {
        System.out.println("update");
        gamePanel.updateUI();
        board.updateUI();
        gamePanel.remove(board);
        gamePanel.add(board);
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ESCAPE) {
            getGameStateManager().setGameStateToStartingMenu();

        }
    }

    public JPanel getJPanel() {
        return gamePanel;
    }
}
