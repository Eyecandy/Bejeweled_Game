package Events;

import panels.GameBoard;
import tiles.Tuple;

import java.util.Set;

public class EventBus{

    private GameBoard gameBoard = null;

    private EventBus() {}

    private static final EventBus INSTANCE = new EventBus();

    public static EventBus getInstance() {
        return INSTANCE;
    }

    public boolean attachGameBoard(GameBoard gameBoard) {
        if (this.gameBoard == null) {
            this.gameBoard = gameBoard;
            return true;
        }
        return false;
    }

    public boolean publish(Event e) throws Exception {
        if (gameBoard == null)
            throw new Exception("gameBoard Not attached");
        System.out.println("publish");
        if (e.getEventType() == EventType.DESTROY_EVENT) {
            DestroyEvent de = (DestroyEvent) e;
            Set<Tuple> targets = de.getTARGETS();
            gameBoard.destroyTiles(targets);
        }
        return true;
    }
}
