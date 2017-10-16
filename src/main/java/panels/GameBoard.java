package panels;

import tiles.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameBoard extends JPanel {
    public GameBoard() {
        this.setLayout(null);
    }

    private int width = 0;
    private int height = 0;
    private int offset = 50;

    public void render(Tile[][] matrix, int width) {
        for (Component component : this.getComponents()) {
            this.remove(component);
        }

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
                label.setBounds(j*offset, i*offset, offset, offset);
                this.add(label);
            }
        }
    }

}
