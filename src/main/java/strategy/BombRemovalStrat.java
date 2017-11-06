package strategy;

import states.GameLogic;
import tiles.Tuple;

import java.util.HashSet;
import java.util.Set;

public class BombRemovalStrat implements TileRemovalStrategy {
    Tuple location;
    Set<Tuple> removed;
    GameLogic gameLogic;

    public BombRemovalStrat(Tuple location,Set<Tuple> removed,GameLogic gameLogic) {
        this.location = location;
        this.removed = removed;
        this.gameLogic = gameLogic;
    }
    @Override
    public Set<Tuple> execute() {
        return clearArea();
    }

    private Set<Tuple> clearArea() {
        Set<Tuple> toRemove = new HashSet<>();
        int y = location.getY();
        int x = location.getX();

        if (removed.contains(location)) {
            toRemove.add(location);
            return toRemove;
        }
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                Tuple currentLocation = new Tuple(j, i);
                if (checkLimits(currentLocation)) {
                    toRemove.add(currentLocation);
                    removed.addAll(toRemove);
                    if (!removed.contains(currentLocation))
                        toRemove.addAll(gameLogic.tilesToRemove(currentLocation, removed));
                }
            }
        }
        return toRemove;
    }
    private boolean checkLimits(Tuple t) {
        int x = t.getX(), y = t.getY();
        return (x >= 0 && y >= 0 && x < gameLogic.getWIDTH()&& y < gameLogic.getHEIGHT());
    }
}
