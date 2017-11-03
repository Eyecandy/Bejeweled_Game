package states;

import panels.Board;
import panels.GameBoard;
import panels.GamePanel;

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
    private Board boardModel;
    private final String title = "LEVEL 1";

    private final int SIZE = 8;
    private final int DIMENSION = 600;

    public void init() {

        gamePanel = new GamePanel(background,titleFont);

        boardModel = new Board(SIZE, SIZE, DIMENSION/SIZE);
        GameLogic gl = new GameLogic(SIZE,SIZE, boardModel);

        BoardObserver boardObserver = new BoardObserver(this,gl);
        gl.addObserver(boardObserver);
        board = new GameBoard(gl, SIZE, DIMENSION, boardModel);

        gamePanel.add(board);
        gamePanel.setVisible(true);
        //board.paintComponents(board.getGraphics());
        //board.setVisible(true);
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
