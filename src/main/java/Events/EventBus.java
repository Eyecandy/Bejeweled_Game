package Events;

import panels.Board;
import tiles.Tuple;

import javax.swing.*;
import java.util.Map;
import java.util.Set;

public class EventBus{

    private Board board = null;

    private EventBus() {}

    private static final EventBus INSTANCE = new EventBus();

    public static EventBus getInstance() {
        return INSTANCE;
    }

    public boolean attachGameBoard(Board board) {
        if (this.board == null) {
            this.board = board;
            return true;
        }
        return false;
    }

    public boolean publish(Event e) throws Exception {
        if (board == null)
            throw new Exception("gameBoard Not attached");

        if (e.getEventType() == EventType.DESTROY_EVENT) {
            DestroyEvent de = (DestroyEvent) e;
            Set<Tuple> targets = de.getTARGETS();
//            for (float scale = 0.9f; scale > 0f; scale-=0.1f) {
//                DestroyTiles dt = new DestroyTiles(targets, scale);
//                dt.run();
//                Timer t = new Timer(1, null);
//                Thread.currentThread().sleep(200);
//            }
        }
        return true;
    }

    private class DestroyTiles extends SwingWorker<Object, Object> {
        private final Set<Tuple> t;
        private Map<Tuple, JLabel> m;
        private final float SCALE;
        private final int OFFSET;
        protected DestroyTiles(Set<Tuple> t, float scale) {
            this.t = t;
            this.m = m;
            this.SCALE = scale;
            this.OFFSET = board.getTILE_SIZE();
        }

        @Override
        protected Object doInBackground() throws Exception {
            for(Tuple tu : t) {
                board.setTileScale(tu.getX(), tu.getY(), SCALE);
                //m.put(tu, label);
            }
            return m;
        }
    }
}
