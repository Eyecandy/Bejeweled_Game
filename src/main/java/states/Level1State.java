package states;

import panels.GameBoard;
import panels.GamePanel;
import tiles.SimpleTile;
import tiles.Tile;

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

    private int xCord;
    private int yCord;
    private int width;
    private int height;
    private final String title = "LEVEL 1";

    public void init() {
        gamePanel = new GamePanel(background,titleFont);
        setXYWH(gamePanel.getX(),gamePanel.getY(),gamePanel.getHeight(),gamePanel.getWidth());
        //JLabel titleLabel = createJlabel(title,titleFont,titleColor);
        GameBoard board = new GameBoard();
        Tile mat[][] = {
                {new SimpleTile(Tile.TileColor.BLUE), new SimpleTile(Tile.TileColor.RED), new SimpleTile(Tile.TileColor.PINK)},
                {new SimpleTile(Tile.TileColor.GREEN), new SimpleTile(Tile.TileColor.PURPLE), new SimpleTile(Tile.TileColor.YELLOW)},
                {new SimpleTile(Tile.TileColor.PINK), new SimpleTile(Tile.TileColor.RED), new SimpleTile(Tile.TileColor.BLUE)}} ;
        board.render(mat, 300);
        board.setBounds(xCord-50,yCord-50, 300,300);
        gamePanel.add(board);
    }
    public void update() {

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
