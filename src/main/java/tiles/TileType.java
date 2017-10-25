package tiles;

public enum TileType {
    SIMPLE(0), BOMB(1), BOMB_VERTICAL(2), BOMB_HORIZONTAL(3);

    private final int value;

    TileType(int value) {
        this.value = value;
    }
}
