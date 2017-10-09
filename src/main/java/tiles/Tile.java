package tiles;


abstract public class Tile {

    public enum TileColor {
        RED, PINK, GREEN, BLUE, PURPLE, YELLOW
    }

    private final TileColor COLOR;

    Tile(TileColor color) {
        COLOR = color;
    }

    public TileColor getCOLOR() {
        return COLOR;
    }
}
