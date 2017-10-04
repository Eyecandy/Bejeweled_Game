package tiles;

import javax.swing.*;

abstract public class Tile {

    enum TileColor {
        RED, PINK, GREEN, BLUE, PURPLE, YELLOW
    }

    private final TileColor COLOR;

    private int xPos;
    private int yPos;

    Tile(TileColor color, int xPos, int yPos) {
        COLOR = color;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public TileColor getCOLOR() {
        return COLOR;
    }
}
