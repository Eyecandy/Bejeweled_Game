package states;

import panels.GameBoard;
import panels.GamePanel;
import tiles.SimpleTile;
import tiles.Tile;
import tiles.TileColor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by joakimnilfjord on 9/25/2017 AD.
 */
public class Level1State extends GameState {
    GamePanel gamePanel;
    private Color background = Color.MAGENTA;
    private Font titleFont = new Font("Script",Font.BOLD,23);
    private Color titleColor = Color.BLACK;
    public GameBoard board;

    private int xCord;
    private int yCord;
    private int width;
    private int height;
    private final String title = "LEVEL 1";

    public void init() {
        gamePanel = new GamePanel(background,titleFont);
        setXYWH(gamePanel.getX(),gamePanel.getY(),gamePanel.getHeight(),gamePanel.getWidth());
        GameLogic gl = new GameLogic();

        BoardObserver boardObserver = new BoardObserver(this,gl);
        gl.addObserver(boardObserver);
        //JLabel titleLabel = createJlabel(title,titleFont,titleColor);
        board = new GameBoard(gl);

        gl.init(8, 8);
        Tile mat[][] = gl.getBoard();
        board.render(mat, 600);
        board.setBounds(xCord-300,yCord, 600,600);
        gamePanel.add(board);
    }
    public void update() {
        System.out.println("update");
        gamePanel.updateUI();
        board.updateUI();
        gamePanel.remove(board);
        board.setBounds(xCord-300,yCord, 600,600);
        gamePanel.add(board);
    }

    public void keyPressed(int k) {
    }

    public JPanel getJpanel() {
        return gamePanel;
    }

    public void setXYWH(int x,int y,int w,int h) {
        xCord = x; yCord=y; width = w; height = h;
    }
}
