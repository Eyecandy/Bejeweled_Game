package Events;

import panels.GameBoard;
import tiles.Tuple;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

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

        Map<Tuple, JLabel> labelMap = gameBoard.getCordToLabel();

        if (e.getEventType() == EventType.DESTROY_EVENT) {
            DestroyEvent de = (DestroyEvent) e;
            Set<Tuple> targets = de.getTARGETS();
            for (float scale = 0.9f; scale > 0f; scale-=0.1f) {
                DestroyTiles dt = new DestroyTiles(targets, labelMap, scale);
                try {
                    dt.run();
                    //gameBoard.setCordToLabel(dt.get()); //blocking
                    gameBoard.renderBoard(dt.get());
                } catch (InterruptedException | ExecutionException e1) {
                    e1.printStackTrace();
                }
                Timer t = new Timer(1, null);
                Thread.currentThread().sleep(200);
            }
        }
        return true;
    }

    private class DestroyTiles extends SwingWorker<Map<Tuple, JLabel>, Object> {
        private final Set<Tuple> t;
        private Map<Tuple, JLabel> m;
        private final float SCALE;
        private final int OFFSET;
        protected DestroyTiles(Set<Tuple> t, Map<Tuple, JLabel> m, float scale) {
            this.t = t;
            this.m = m;
            this.SCALE = scale;
            this.OFFSET = gameBoard.getOFFSET();
        }

        @Override
        protected Map<Tuple, JLabel> doInBackground() throws Exception {
            for(Tuple tu : t) {
                JLabel label = m.get(tu);
                Rectangle r = new Rectangle();
                label.getBounds(r);
                label.setBounds(r.x, r.y, Math.round(OFFSET*SCALE), Math.round(OFFSET*SCALE));
                //m.put(tu, label);
            }
            return m;
        }
    }
}
