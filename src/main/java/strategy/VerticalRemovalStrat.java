package strategy;

import states.GameLogic;
import tiles.Tuple;

import java.util.HashSet;
import java.util.Set;

public class VerticalRemovalStrat implements TileRemovalStrategy {
    Tuple location;
    Set<Tuple> removed;
    GameLogic gameLogic;

    public VerticalRemovalStrat(Tuple location, Set<Tuple> removed, GameLogic gameLogic) {
        this.location = location;
        this.removed = removed;
        this.gameLogic = gameLogic;
    }
    @Override
    public Set<Tuple> execute() {
        Set<Tuple> toRemove = new HashSet<>();
        toRemove.add(location);
        toRemove.addAll(clearColumn());
        return toRemove;
    }

    private Set<Tuple> clearColumn() {

        final int X = location.getX();
        Set<Tuple> toRemove = new HashSet<>();

        if (removed.contains(location)) {
            toRemove.add(location);
            return toRemove;
        }
        for (int y = 0; y < gameLogic.getHEIGHT(); y++) {
            Tuple currentLocation = new Tuple(X, y);
            toRemove.add(currentLocation);
            removed.addAll(toRemove);
            if (!removed.contains(currentLocation))
               toRemove.addAll(gameLogic.tilesToRemove(currentLocation, removed));
        }
        return toRemove;
    }
}
