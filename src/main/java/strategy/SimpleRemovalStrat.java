package strategy;

import tiles.Tuple;

import java.util.HashSet;
import java.util.Set;

public class SimpleRemovalStrat implements TileRemovalStrategy {
    Tuple location;

    public SimpleRemovalStrat(Tuple location)  {
        this.location = location;
    }
    @Override
    public Set<Tuple> execute() {

        Set<Tuple> toRemove = new HashSet<>();
        toRemove.add(location);
        return toRemove;
    }
}
