package strategy;

import states.GameLogic;
import tiles.Tuple;

import java.util.HashSet;
import java.util.Set;

public class HorizontalRemovalStrat implements TileRemovalStrategy {
    Tuple location;
    Set<Tuple> removed;
    GameLogic gameLogic;

    public HorizontalRemovalStrat(Tuple location, Set<Tuple> removed, GameLogic gameLogic) {
        this.location = location;
        this.removed = removed;
        this.gameLogic = gameLogic;
    }
    @Override
    public Set<Tuple> execute() {
        Set<Tuple> toRemove = new HashSet<>();
        toRemove.add(location);
        toRemove.addAll(clearRow());
        return toRemove;
    }

    private Set<Tuple> clearRow() {
        final int Y = location.getY();
        Set<Tuple> toRemove = new HashSet<>();

        if (removed.contains(location)) {
            toRemove.add(location);
            return toRemove;
        }
        for (int x = 0; x < gameLogic.getWIDTH(); x++) {
            Tuple currentLocation = new Tuple(x, Y);
            toRemove.add(currentLocation);
            removed.addAll(toRemove);
            if (!removed.contains(currentLocation))
               toRemove.addAll(gameLogic.tilesToRemove(currentLocation, removed));
        }
        return toRemove;
    }
}
