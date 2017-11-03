package panels;

import Events.EventBus;
import states.GameLogic;
import tiles.Tuple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameBoard extends JPanel implements MouseListener{
    private Map<JLabel, Tuple> labelToCord = new HashMap<>();
    private Map<Tuple, JLabel> cordToLabel = new HashMap<>();
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
        eventBus.attachGameBoard(board);


        this.board = board;
        setDoubleBuffered(true);
        Timer timer = new Timer(100, e -> {
//            revalidate();
//            repaint();
            board.paintBoard(this.getGraphics());
//                paintComponents(getGraphics());
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
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int col = e.getX()/OFFSET;
        int row = e.getY()/OFFSET;
        System.out.println(col + ", " + row + " -> press");
        gl.toClick(col, row);
//        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void jLabel1MouseEntered(JLabel jLabel1)
    {
        jLabel1.setOpaque(true);
        jLabel1.setBackground(Color.GRAY);
    }

    private void jLabel1MouseExited(JLabel jLabel1)
    {
        jLabel1.setOpaque(false);
        jLabel1.setBackground(Color.WHITE);
    }
    private void jLabel1MouseClicked(JLabel jLabel1)
    {
        jLabel1.setOpaque(true);
        jLabel1.setBackground(Color.BLACK);
    }

    public Map<Tuple, JLabel> getCordToLabel() {
        return cordToLabel;
    }

    public void setCordToLabel(Map<Tuple, JLabel> cordToLabel) {
        this.cordToLabel = cordToLabel;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public int getOFFSET() {
        return OFFSET;
    }
}
