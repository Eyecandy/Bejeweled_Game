package tiles;

abstract public class Tile {

    enum TileColor {
        RED, PINK, GREEN, BLUE, PURPLE, YELLOW
    }

    private final TileColor COLOR;

    protected Tile(TileColor color) {
        COLOR = color;
    }

    public TileColor getCOLOR() {
        return COLOR;
    }
}
