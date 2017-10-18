package states;

import tiles.SimpleTile;
import tiles.Tile;
import tiles.TileColor;
import tiles.Tuple;

import java.util.*;

public class GameLogic extends Observable{

    public Tile[][] getBoard() {
        return board;
    }

    private Tile[][] board;
    private Stack<Tuple> clickStack= new Stack<>();

    public void toClick(int i, int j) {
        System.out.println("click");
        clickStack.add(new Tuple(i, j));
        if (clickStack.size() > 1) {
            Tuple t1 = clickStack.pop();
            Tuple t2 = clickStack.pop();
            Tile [][] tmpBoard = new Tile[board.length][];
            for(int k = 0; k < board.length; k++)
            {
                Tile[] aMatrix = board[k];
                int  aLength = aMatrix.length;
                tmpBoard[k] = new Tile[aLength];
                System.arraycopy(aMatrix, 0, tmpBoard[k], 0, aLength);
            }
            if (isSwappable(tmpBoard, t1, t2)){
                swap(t1, t2);
            }
            clickStack.clear();
        }

    }

    private void swap(Tuple t1, Tuple t2) {

        int x1 = t1.x, x2 = t2.x, y1 = t1.y, y2 = t2.y;
        Tile swap1 = board[y1][x1];
        Tile swap2 = board[y2][x2];
        board[y1][x1] = swap2;
        board[y2][x2] = swap1;

        clearGroups(board);

        setChanged();
        notifyObservers();
    }

    private Random rnd = new Random();

    //Returns random int 0 to 4 (inclusive)
    private int getRandom(){
        return rnd.nextInt(5);
    }

    //Gets random except for the numbers in Set argument
    private int getRandomWithExclusion(Set<Integer> exclude) {
        int random = rnd.nextInt(4);
        for (int ex : exclude) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }

    //initializes board
    public void init(int row, int column){
        board = new Tile[row][column];
        int countX;
        int countY;
        Tile excludeX = newTile(0);
        Tile excludeY = newTile(0);
        Set<Integer> exclude = new HashSet<>();
        for(int i = row-1;i >= 0;i--){
            for(int j=0;j < column;j++){
                //Horizontal
                countX = 0;
                for(int k=j-1; k >= 0; k--){
                    if (k == (j-1)){
                        excludeX = board[i][k];
                    }else {
                        if (excludeX.compareColor(board[i][k])){
                            countX++;
                        }else {
                            break;
                        }
                    }
                }
                //Vertical
                countY = 0;
                for (int l=i+1; l < row;l++){
                    if(l == (i+1)){
                        excludeY = board[l][j];
                    }else{
                        if (excludeY.compareColor(board[l][j])){
                            countY++;
                        }else {
                            break;
                        }
                    }
                }
                if (countX > 0){ exclude.add(excludeX.getColorIndex()); }
                if (countY > 0){ exclude.add(excludeY.getColorIndex()); }
                board[i][j] = newTile(getRandomWithExclusion(exclude));
                exclude.clear();
            }
        }
    }


    //Creates new tile of a certain color
    private Tile newTile(int index){
        switch (index){
            case 0:
                return new SimpleTile(TileColor.BLUE);
            case 1:
                return new SimpleTile(TileColor.RED);
            case 2:
                return new SimpleTile(TileColor.GREEN);
            case 3:
                return new SimpleTile(TileColor.PINK);
            case 4:
                return new SimpleTile(TileColor.YELLOW);
            default:
                return new SimpleTile(TileColor.YELLOW);
        }
    }

    //prints board
    private void printBoard(Tile[][] board){
        for (Tile[] aBoard : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (aBoard[j] == null) {
                    System.out.print("NULL | ");
                } else {
                    System.out.print(aBoard[j].getCOLOR().toString() + " | ");
                }

            }
            System.out.println();
        }
    }

    private boolean isSwappable(Tile[][] board, Tuple a, Tuple b) {
        int aX = a.getX(), aY = a.getY(), bX = b.getX(), bY = b.getY();
        Tile tmp = board[aY][aX];
        board[aY][aX] = board[bY][bX];
        board[bY][bX] = tmp;
        //printBoard(board);
        int width = board[0].length, height = board.length;
        if (checkLimits(aX, aY, width, height) && checkLimits(bX, bY, width, height)) {
            if (aX == bX && Math.abs(aY - bY) == 1) {
                // swapping vertically
                if (columnGroups(board, aX, height).size() > 0)
                    return true;
                for (int j = Math.min(aY, bY); j <= Math.max(aY, bY); j++) {
                    if (rowGroups(board, j, width).size() > 0)
                        return true;
                }
            } else if (aY == bY && Math.abs(aX - bX) == 1) {
                // swapping horizontally
                if (rowGroups(board, aY, width).size() > 0)
                    return true;
                for (int j = Math.min(aX, bX); j <= Math.max(aX, bX); j++) {
                    if (columnGroups(board, j, height).size() > 0)
                        return true;
                }
            }
        }
        //System.out.println(false);
        return false;
    }

    private Set<Tuple> columnGroups(Tile[][] board, final int X, final int HEIGHT) {
        Set<Tuple> currentGroup = new HashSet<>();
        Set<Tuple> toRemove = new HashSet<>();

        TileColor currentColour = board[0][X].getCOLOR();
        currentGroup.add(new Tuple(X, 0));
        Tile currentTile;

        for (int y = 1; y < HEIGHT; y++) {
            currentTile = board[y][X];
            if (currentTile.getCOLOR() != currentColour) {
                currentGroup.clear();
                currentColour = currentTile.getCOLOR();
            }
            currentGroup.add(new Tuple(X, y));
            if (currentGroup.size() >= 3)
                toRemove.addAll(currentGroup);
        }
        return toRemove;
    }

    private Set<Tuple> rowGroups(Tile[][] board, final int Y, final int WIDTH) {
        Set<Tuple> currentGroup = new HashSet<>();
        Set<Tuple> toRemove = new HashSet<>();

        TileColor currentColour = board[Y][0].getCOLOR();
        currentGroup.add(new Tuple(0, Y));
        Tile currentTile;

        for (int x = 1; x < WIDTH; x++) {
            currentTile = board[Y][x];

            if (currentTile.getCOLOR() != currentColour) {
                currentColour = currentTile.getCOLOR();
                currentGroup.clear();
            }
            currentGroup.add(new Tuple(x, Y));
            if (currentGroup.size() >= 3)
                toRemove.addAll(currentGroup);
        }
        return toRemove;
    }

    private boolean checkLimits(int x, int y, int width, int height) {
        return (x >= 0 && y >= 0 && x < width && y < height);
    }

    //Set the tiles to be removed to null
    private void remove(Set<Tuple> removable, Tile[][] board){
        for (Tuple t : removable){
            board[t.getY()][t.getX()] = null;
        }
    }

    //Slide the board down to remove all removables
    private void slideDown(Tile[][] board){
        int row = board.length;
        int column = board[0].length;
        int count;
        ArrayList<Tile> temp = new ArrayList<>();
        for (int i=0;i < column;i++){
            for (Tile[] aBoard : board) {
                if (aBoard[i] != null) {
                    temp.add(aBoard[i]);
                }
            }
            count = temp.size() - 1;
            for(int k=row-1; k >= 0; k--){
                if (count < 0){
                    board[k][i] = null;
                }else {
                    board[k][i] = temp.get(count);
                    count--;
                }
            }
            temp.clear();
        }
    }

    //fill in the null entries with new random entries
    private void fillNull(Tile[][] board){
        for(int i=0;i < board[0].length;i++){
            for (int j=0; j < board.length;j++){
                if (board[j][i] == null){
                    board[j][i] = newTile(getRandom());
                }else {
                    break;
                }
            }
        }
    }

    private void clearGroups(Tile[][] board){
        Set<Tuple> toRemove = new HashSet<>();

        int width = board[0].length, height = board.length;

        do {
            toRemove.clear();
            for (int i = 0; i < width; i++) {
                toRemove.addAll(columnGroups(board, i, height));
            }
            for (int j = 0; j < height; j++) {
                toRemove.addAll(rowGroups(board, j, width));
            }
            printBoard(board);
            System.out.println(toRemove);
            remove(toRemove, board);
            slideDown(board);
            fillNull(board);
        } while (toRemove.size() != 0);
        //printBoard(board);
    }
}
