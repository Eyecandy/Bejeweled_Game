package states;

import java.util.Observable;
import java.util.Observer;

public class BoardObserver implements Observer {
    private GameLogic gameLogic;
    private Level1State level1State;

    public BoardObserver(Level1State level1State, GameLogic gameLogic) {
        this.level1State = level1State;
        this.gameLogic = gameLogic;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("observed");
        if (o.getClass() == GameLogic.class){
            //level1State.gamePanel.remove(level1State.board);
            level1State.board.render(((GameLogic)o).getBoard());
            //level1State.board.setBounds(xCord-400,yCord, 800,800);
            //level1State.gamePanel.add(level1State.board);
            level1State.update();
        }
    }
}
