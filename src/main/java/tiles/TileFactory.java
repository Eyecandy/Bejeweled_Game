package tiles;

import java.util.Random;

public class TileFactory {

    private final Random random;

    public TileFactory() {
        random = new Random();
    }

    public Tile createTile(TileColor colour) {
        Double rng = random.nextDouble();
        if (rng > 0.98)
            return new BombHorizontalTile(colour);
        else if (rng > 0.96)
            return new BombVerticalTile(colour);
        else if (rng > 0.92)
            return new BombTile(colour);
        else
            return new SimpleTile(colour);
    }
}
