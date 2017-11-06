package strategy;

import tiles.Tuple;

import java.util.Set;

public interface TileRemovalStrategy {
    Set<Tuple> execute();
}
