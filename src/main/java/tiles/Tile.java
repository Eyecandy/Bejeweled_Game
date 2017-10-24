package tiles;

abstract public class Tile {

    private final TileColor COLOR;

    Tile(TileColor color) {
        COLOR = color;
    }

    public TileColor getCOLOR() {
        return COLOR;
    }

    public boolean compareColor(Tile tile){
        return COLOR.equals(tile.getCOLOR());
    }
}
