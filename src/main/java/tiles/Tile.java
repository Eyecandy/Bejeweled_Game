package tiles;

import javax.swing.*;

abstract public class Tile {

    private final TileColor COLOR;
    private int colorIndex;

    public Tile(TileColor color) {
        COLOR = color;
        if (COLOR==TileColor.BLUE){
            colorIndex = 0;
        } else if(COLOR==TileColor.RED){
            colorIndex = 1;
        }else if(COLOR==TileColor.GREEN){
            colorIndex = 2;
        }else if(COLOR==TileColor.PINK){
            colorIndex = 3;
        }else if(COLOR==TileColor.YELLOW){
            colorIndex = 4;
        }
    }

    public int getColorIndex(){
        return colorIndex;
    }
    public TileColor getCOLOR() {
        return COLOR;
    }

    public String getColorString(){
        return COLOR.toString().toLowerCase();
    }

    public boolean compareColor(Tile tile){
        return this.getColorString().equals(tile.getColorString());
    }
}
