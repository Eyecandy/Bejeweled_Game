package tiles;

import javax.swing.*;

abstract public class Tile {

    private final TileColor COLOR;
    private int colorIndex;

    Tile(TileColor color) {
        COLOR = color;
        switch (COLOR) {
            case BLUE:
                colorIndex = 0;
                break;
            case RED:
                colorIndex = 1;
                break;
            case GREEN:
                colorIndex = 2;
                break;
            case PINK:
                colorIndex = 3;
                break;
            case YELLOW:
                colorIndex = 4;
                break;
        }
    }

    public int getColorIndex(){
        return colorIndex;
    }
    public TileColor getCOLOR() {
        return COLOR;
    }

    private String getColorString(){
        return COLOR.toString().toLowerCase();
    }

    public boolean compareColor(Tile tile){
        return this.getColorString().equals(tile.getColorString());
    }
}
