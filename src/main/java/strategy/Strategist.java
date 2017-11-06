package strategy;

import states.GameLogic;
import tiles.Tile;
import tiles.Tuple;

import java.util.Set;

public class Strategist {
    GameLogic gameLogic;

    public Strategist(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public TileRemovalStrategy findStrat(Tile tile, Tuple location, Set<Tuple> removed, GameLogic gameLogic) {

        switch (tile.getTYPE()) {
            case SIMPLE:
                return new SimpleRemovalStrat(location);
            case BOMB:
                return new BombRemovalStrat(location,removed,gameLogic);
            case BOMB_HORIZONTAL:
                return new HorizontalRemovalStrat(location,removed,gameLogic);
            case BOMB_VERTICAL:
                return new VerticalRemovalStrat(location,removed,gameLogic);
            default: throw new NullPointerException();
        }
    }
}
