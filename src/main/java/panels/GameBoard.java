package panels;

import states.GameLogic;
import tiles.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameBoard extends JPanel {
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
                label.setName(i + "," + j);
                label.setBounds(j*offset, i*offset, offset, offset);
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        String name = e.getComponent().getName();
                        System.out.println(name);
                        String[] str = name.split(",");
                        gl.toClick(Integer.parseInt(str[0]),Integer.parseInt(str[1]));
                        super.mouseClicked(e);
                    }
                });
                this.add(label);
            }
        }
    }

}
