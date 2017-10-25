package tiles;


abstract public class Tile {

    private final TileColor COLOR;
    private final TileType TYPE;

    Tile(TileColor color, TileType type) {
        COLOR = color;
        TYPE = type;
    }

    public TileColor getCOLOR() {
        return COLOR;
    }

    public TileType getTYPE() {
        return TYPE;
    }

    public boolean compareColor(Tile tile){
        return COLOR.equals(tile.getCOLOR());
    }
}
