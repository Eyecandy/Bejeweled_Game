package panels;

import states.GameLogic;
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

public class GameBoard extends JPanel {
    private Map<JLabel, Tuple> labelToCord = new HashMap<>();
    private Set<JLabel> jLabelsClicked = new HashSet<>();
    private final GameLogic gl;
    public GameBoard(GameLogic gl) {
        this.gl = gl;
        this.setLayout(null);
    }
    private int width = 0;
    private int height = 0;
    private int offset = 50;
    private int boardWidth = 0;

    public void render(Tile[][] matrix) {
        System.out.println(boardWidth);
        render(matrix, boardWidth);
    }
    public void render(Tile[][] matrix, int width) {
        for (Component component : this.getComponents()) {
            this.remove(component);
        }
        boardWidth = width;
        height = matrix.length;
        this.width = matrix[0].length;
        offset = width/this.width;
        System.out.println(width);
        System.out.println(this.width);
        System.out.println(offset);

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < this.width; j++) {
                JLabel label = new JLabel(Integer.toString(i + j*height));
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                Image img = null;
                try {
                    switch (matrix[i][j].getCOLOR()){
                        case RED:
                            img = ImageIO.read(getClass().getResource("/gem-Red.png"));
                            break;
                        case BLUE:
                            img = ImageIO.read(getClass().getResource("/gem-Blue.png"));
                            break;
                        case PINK:
                            img = ImageIO.read(getClass().getResource("/gem-Pink.png"));
                            break;
                        case GREEN:
                            img = ImageIO.read(getClass().getResource("/gem-Green.png"));
                            break;
                        case PURPLE:
                            img = ImageIO.read(getClass().getResource("/gem-Purple.png"));
                            break;
                        case YELLOW:
                            img = ImageIO.read(getClass().getResource("/gem-Yellow.png"));
                            break;
                    }
                    if (img != null)
                        label.setIcon(new ImageIcon(img.getScaledInstance(offset,offset,0)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Tuple cord = new Tuple(j,i);
                labelToCord.put(label,cord);
                label.setBounds(j*offset, i*offset, offset, offset);
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
                        Tuple cord = labelToCord.get(e.getComponent());
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

                this.add(label);
            }
        }
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

}
