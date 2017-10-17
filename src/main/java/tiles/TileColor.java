package tiles;

public enum TileColor {
    RED, PINK, GREEN, BLUE, PURPLE, YELLOW;

    @Override
    public String toString() {
        return name().toLowerCase().substring(0,3);
    }
}
