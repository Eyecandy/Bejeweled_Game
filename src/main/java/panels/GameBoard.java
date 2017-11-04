package panels;

import Events.EventBus;
import states.GameLogic;
import tiles.Tuple;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class GameBoard extends JPanel implements MouseListener{
    private Map<JLabel, Tuple> labelToCord = new HashMap<>();
    private Set<JLabel> jLabelsClicked = new HashSet<>();
    private final GameLogic gl;
    private final Board board;
    private final int BOARD_SIZE;
    private final int BOARD_DIMENSION;
    private final int OFFSET;
    private EventBus eventBus;
    public GameBoard(GameLogic gl, int size, int dimensions, Board board) {
        this.gl = gl;
        BOARD_SIZE = size;
        BOARD_DIMENSION = dimensions;
        OFFSET = BOARD_DIMENSION/BOARD_SIZE;
        this.setMaximumSize(new Dimension(BOARD_DIMENSION, BOARD_DIMENSION));
        this.setPreferredSize(new Dimension(BOARD_DIMENSION, BOARD_DIMENSION));
        this.setMinimumSize(new Dimension(BOARD_DIMENSION, BOARD_DIMENSION));
        this.setMaximumSize(new Dimension(BOARD_DIMENSION, BOARD_DIMENSION));
        this.setBackground(Color.LIGHT_GRAY);
        this.setBounds(200, 150, BOARD_DIMENSION, BOARD_DIMENSION);
        this.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        this.addMouseListener(this);
        eventBus = EventBus.getInstance();
        eventBus.attachGameBoard(this);


        this.board = board;
        setDoubleBuffered(true);
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.paintBoard(GameBoard.this.getGraphics());
            }
        });
        //timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        board.paintBoard(g);
    }

    @Override
    public void paintComponents(Graphics g) {
        System.out.println("paint component");
        super.paintComponents(g);
        board.paintBoard(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        int col = e.getX()/OFFSET;
        int row = e.getY()/OFFSET;
        System.out.println(col + ", " + row + " -> press");
        gl.toClick(col, row);
//        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    public void destroyTiles(Set<Tuple> toRemove) {
        float scale = 1.0f;
        while (scale > 0.f) {
            DestroyTiles destroyTiles = new DestroyTiles(toRemove, scale);
            destroyTiles.run();
            try {
                destroyTiles.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            scale -= 0.15f;
        }
        System.out.println("DoneAnimate");
    }

    class DestroyTiles extends SwingWorker<Object,Object> {
        Set<Tuple> toRemove;
        final float SIZE;
        public DestroyTiles(Set<Tuple> toRemove, float size) {
            this.toRemove = toRemove;
            SIZE = size;
        }

        @Override
        protected Object doInBackground() throws Exception {
            for (Tuple t : toRemove) {
                board.setTileScale(t.getX(), t.getY(), SIZE);
            }
            board.paintBoard(GameBoard.this.getGraphics());
            return null;
        }
    }

}
