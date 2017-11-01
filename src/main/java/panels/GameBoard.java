package panels;

import Events.EventBus;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import states.GameLogic;
import states.Level1State;
import tiles.Tile;
import tiles.Tuple;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameBoard extends JPanel implements Observable{
    private Map<JLabel, Tuple> labelToCord = new HashMap<>();
    private Map<Tuple, JLabel> cordToLabel = new HashMap<>();
    private Set<JLabel> jLabelsClicked = new HashSet<>();
    private final GameLogic gl;
    private final int BOARD_SIZE;
    private final int BOARD_DIMENSION;
    private final int OFFSET;
    private EventBus eventBus;
    private Level1State level;
    public GameBoard(GameLogic gl, int size, int dimensions, Level1State level) {
        this.gl = gl;
        BOARD_SIZE = size;
        BOARD_DIMENSION = dimensions;
        OFFSET = BOARD_DIMENSION/BOARD_SIZE;
        this.setMaximumSize(new Dimension(BOARD_DIMENSION, BOARD_DIMENSION));
        this.setBounds(200, 150, BOARD_DIMENSION, BOARD_DIMENSION);
        this.setLayout(null);
        eventBus = EventBus.getInstance();
        eventBus.attachGameBoard(this);
        this.level = level;
    }

    public void renderBoard() {
        renderBoard(cordToLabel);
    }

    public void renderBoard(Map<Tuple, JLabel> labels) {
        for (Component component : this.getComponents()) {
            this.remove(component);
            this.revalidate();
            GUI.getInstance().repaint();
        }
        System.out.println("----");
        for (Tuple t: labels.keySet()) {
            System.out.println(labels.get(t).getBounds().height);
            this.add(labels.get(t));
            this.revalidate();
            this.getParent().revalidate();
            this.getParent().repaint();
            GUI.getInstance().repaint();
        }
//        this.revalidate();
//        this.repaint();
//        this.updateUI();
//        this.getParent().revalidate();
//        this.getParent().repaint();
//        level.update();
        //this.dr
        System.out.println("rendered");
    }

    public void calcLabelPositions(Tile[][] matrix) {
        labelToCord.clear();
        cordToLabel.clear();
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                JLabel label = new JLabel();
                label.setBackground(Color.LIGHT_GRAY);

                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                ImageIcon img = getTileIcon(matrix[i][j], OFFSET);
                if (img != null) {
                    label.setIcon(img);
                }
                Tuple cord = new Tuple(j,i);
                cordToLabel.put(cord, label);
                labelToCord.put(label,cord);
                label.setBounds(j*OFFSET, i*OFFSET, OFFSET, OFFSET);
                label.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        if (!jLabelsClicked.contains(label)) {
                            jLabel1MouseEntered(label);
                        }
                    }
                    public void mouseExited(MouseEvent e) {
                        if (!jLabelsClicked.contains(label)) {
                            jLabel1MouseExited(label);
                        }
                    }
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        jLabelsClicked.add(label);

                        jLabel1MouseClicked(label);
                        if (jLabelsClicked.size() == 2) {
                            jLabel1MouseExited(label);

                        }
                        Tuple cord = labelToCord.get((JLabel) e.getComponent());
                        gl.toClick(cord.getX(),cord.getY());
                        super.mouseClicked(e);
                    }
                    public void mouseReleased(MouseEvent e) {
                        if (jLabelsClicked.size() == 1) {
                            jLabel1MouseExited(label);
                            for( JLabel label1:jLabelsClicked) {
                                label1.setOpaque(false);
                                label1.setBackground(Color.WHITE);
                            }
                            jLabelsClicked = new HashSet<>();
                        }
                    }
                });
            }
        }
    }


    public void render(Tile[][] matrix) {
        calcLabelPositions(matrix);
        renderBoard(cordToLabel);
//        labelToCord.clear();
//        cordToLabel.clear();
//        for (Component component : this.getComponents()) {
//            this.remove(component);
//        }
//
//        for (int i = 0; i < BOARD_SIZE; ++i) {
//            for (int j = 0; j < BOARD_SIZE; j++) {
//                JLabel label = new JLabel();
//                label.setBackground(Color.LIGHT_GRAY);
//                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//
//                ImageIcon img = getTileIcon(matrix[i][j], OFFSET);
//                if (img != null) {
//                    label.setIcon(img);
//                }
//                Tuple cord = new Tuple(j,i);
//                cordToLabel.put(cord, label);
//                labelToCord.put(label,cord);
//                label.setBounds(j*OFFSET, i*OFFSET, OFFSET, OFFSET);
//                label.addMouseListener(new MouseAdapter() {
//                    public void mouseEntered(MouseEvent e) {
//                        if (!jLabelsClicked.contains(label)) {
//                            jLabel1MouseEntered(label);
//                        }
//                    }
//                    public void mouseExited(MouseEvent e) {
//                        if (!jLabelsClicked.contains(label)) {
//                            jLabel1MouseExited(label);
//                        }
//                    }
//                    @Override
//                    public void mouseClicked(MouseEvent e) {
//                        jLabelsClicked.add(label);
//
//                        jLabel1MouseClicked(label);
//                        if (jLabelsClicked.size() == 2) {
//                            jLabel1MouseExited(label);
//
//                        }
//                        Tuple cord = labelToCord.get((JLabel) e.getComponent());
//                        gl.toClick(cord.getX(),cord.getY());
//                        super.mouseClicked(e);
//                    }
//                    public void mouseReleased(MouseEvent e) {
//                        if (jLabelsClicked.size() == 1) {
//                            jLabel1MouseExited(label);
//                            for( JLabel label1:jLabelsClicked) {
//                                 label1.setOpaque(false);
//                                 label1.setBackground(Color.WHITE);
//                            }
//                            jLabelsClicked = new HashSet<>();
//                        }
//                    }
//                });
//                this.add(label);
//            }
//        }

//        System.out.println("bus");
//        eventBus.subscribe(cordToLabel);
//        System.out.println("send");
//        eventBus.publish(new DestroyEvent(cordToLabel.keySet()));
//        System.out.println("event");
    }

    private ImageIcon getTileIcon(Tile tile, int size) {
        Image img;
        String iconName = "/gem-";
        String iconSuffix = ".png";
        switch (tile.getCOLOR()){
            case RED:
                iconName += "Red";
                break;
            case BLUE:
                iconName += "Blue";
                break;
            case PINK:
                iconName += "Pink";
                break;
            case GREEN:
                iconName += "Green";
                break;
            case PURPLE:
                iconName += "Purple";
                break;
            case YELLOW:
                iconName += "Yellow";
        }
        switch (tile.getTYPE()) {
            case SIMPLE:
                iconName += iconSuffix;
                break;
            case BOMB:
                iconName += "-Bomb" + iconSuffix;
                break;
            case BOMB_VERTICAL:
                iconName += "-Vert" + iconSuffix;
                break;
            case BOMB_HORIZONTAL:
                iconName += "-Horz" + iconSuffix;
        }
        try {
            img = ImageIO.read(getClass().getResource(iconName));
        } catch (IOException e) {
            return null;
        }
        return new ImageIcon(img.getScaledInstance(size,size,1));
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

    @Override
    public void addListener(InvalidationListener invalidationListener) {

    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {

    }
}
