package states;

import tiles.Tuple;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
        if (o.getClass() == GameLogic.class && arg instanceof  Integer){
            //level1State.gamePanel.remove(level1State.board);
//            Set<Tuple> tupSet = gameLogic.getAnimateRemove();
            Integer updateChoice = (Integer)  arg;
            if (updateChoice== 0) {
                level1State.update();
                try {
                    TimeUnit.MILLISECONDS.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if (updateChoice == 1) {
                level1State.board.render(((GameLogic)o).getBoard());
                level1State.update();
            }
            //level1State.board.setBounds(xCord-400,yCord, 800,800);
            //level1State.gamePanel.add(level1State.board);
            ;
        }
    }
}
