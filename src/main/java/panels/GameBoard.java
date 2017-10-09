package panels;

import tiles.Tile;

import javax.swing.*;
import java.awt.*;

public class GameBoard extends JPanel {
    public GameBoard() {
        this.setLayout(null);
    }

    public void render(Tile[][] matrix) {
        for (Component component : this.getComponents()) {
            this.remove(component);
        }
        int offset = 100;

        for (int i = 0; i<matrix.length; ++i) {
            for (int j = 0; j<matrix[0].length; j++) {
                JLabel label = new JLabel(Integer.toString(matrix.length*i + j));
                label.setBounds(i*offset, j*offset, offset, offset);
                this.add(label);
            }
        }
    }

}
