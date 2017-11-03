package panels;

import tiles.Tile;
import tiles.Tuple;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

public class Board {
    private Tile[][] board;
    private float[][] tileScale;
    private final int WIDTH;
    private final int HEIGHT;
    private final int TILE_SIZE;
    private Queue<Tuple> selectedTiles;
//    private final TileFactory tileFactory;

    public Board(int WIDTH, int HEIGHT, int SIZE) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.TILE_SIZE = SIZE;
        board = new Tile[HEIGHT][WIDTH];
        tileScale = new float[HEIGHT][WIDTH];

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                tileScale[i][j] = 1.0f;
            }
        }
        selectedTiles = new ArrayDeque<>();
    }

    public void clearSelected() {
        selectedTiles.clear();
    }

    public void addSelected(Tuple t) {
        if (selectedTiles.size() == 2)
            selectedTiles.remove();
        selectedTiles.add(t);
    }

    public void popSelected() {
        selectedTiles.remove();
    }

    public void setTile(final int X, final int Y, Tile newTile) {
        board[Y][X] = newTile;
        tileScale[Y][X] = 1.0f;
    }

    public void setTileScale(final int X, final int Y, final float SCALE) {
        tileScale[Y][X] = SCALE;
    }

    public double getTileScale(final int X, final int Y) {
        return tileScale[Y][X];
    }

    public Tile getTile(final int X, final int Y) {
        return board[Y][X];
    }

    public Image getTileImage(final int X, final int Y) {
        Tile tile = getTile(X, Y);

        String iconName = "/gem-";
        final String SUFFIX = ".png";
        switch (tile.getCOLOR()){
            case RED:
                iconName += "Red";
                break;
            case BLUE:
                iconName += "Blue";
                break;
            case PINK:
                iconName += "Pink";
                break;
            case GREEN:
                iconName += "Green";
                break;
            case PURPLE:
                iconName += "Purple";
                break;
            case YELLOW:
                iconName += "Yellow";
        }
        switch (tile.getTYPE()) {
            case SIMPLE:
                iconName += SUFFIX;
                break;
            case BOMB:
                iconName += "-Bomb" + SUFFIX;
                break;
            case BOMB_VERTICAL:
                iconName += "-Vert" + SUFFIX;
                break;
            case BOMB_HORIZONTAL:
                iconName += "-Horz" + SUFFIX;
        }
        try {
            return ImageIO.read(getClass().getResource(iconName));
        } catch (IOException e) {
            return null;
        }
    }

    public Tile[][] getBoardCopy () {
        Tile [][] boardCopy = new Tile[HEIGHT][];
        for(int k = 0; k < WIDTH; k++)
        {
            Tile[] boardRow = board[k];
            boardCopy[k] = new Tile[WIDTH];
            System.arraycopy(boardRow, 0, boardCopy[k], 0, WIDTH);
        }
        return boardCopy;
    }

    /**
     * Prints the board for debugging purposes
     */
    private void printBoard(){
        for (Tile[] aBoard : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (aBoard[j] == null) {
                    System.out.print("NUL | ");
                } else {
                    System.out.print(aBoard[j].getCOLOR().toString() + " | ");
                }

            }
            System.out.println();
        }
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    public void paintBoard(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                int image_size = Math.round(TILE_SIZE * tileScale[y][x]);
                Image scaledInstance = getTileImage(x, y).getScaledInstance(
                        image_size, image_size, Image.SCALE_FAST);
                g2D.drawImage(scaledInstance, x*TILE_SIZE, y*TILE_SIZE, (selectedTiles.contains(new Tuple(x,y))) ? Color.BLACK : Color.LIGHT_GRAY, null);
            }
        }
    }
}
