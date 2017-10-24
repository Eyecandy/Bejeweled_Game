package states;

import tiles.SimpleTile;
import tiles.Tile;
import tiles.TileColor;
import tiles.Tuple;

import java.util.*;

public class GameLogic extends Observable{

    private Tile[][] board;
    private final int WIDTH;
    private final int HEIGHT;
    private Tuple previousClick = null;

    public Set<Tuple> getAnimateRemove() {
        return animateRemove;
    }

    private Set<Tuple> animateRemove = new HashSet<>();

    GameLogic(int rows, int columns) {
        this.HEIGHT = rows;
        this.WIDTH = columns;
        this.board = new Tile[HEIGHT][WIDTH];


        int countX;
        int countY;
        Tile excludeX = new SimpleTile(getRandomColour());
        Tile excludeY = new SimpleTile(getRandomColour());
        Set<TileColor> exclude = new HashSet<>();
        do {
            for (int i = HEIGHT - 1; i >= 0; i--) {
                for (int j = 0; j < WIDTH; j++) {
                    //Horizontal
                    countX = 0;
                    for (int k = j - 1; k >= 0; k--) {
                        if (k == (j - 1)) {
                            excludeX = board[i][k];
                        } else {
                            if (excludeX.compareColor(board[i][k])) {
                                countX++;
                            } else {
                                break;
                            }
                        }
                    }
                    //Vertical
                    countY = 0;
                    for (int l = i + 1; l < HEIGHT; l++) {
                        if (l == (i + 1)) {
                            excludeY = board[l][j];
                        } else {
                            if (excludeY.compareColor(board[l][j])) {
                                countY++;
                            } else {
                                break;
                            }
                        }
                    }
                    if (countX > 0) {
                        exclude.add(excludeX.getCOLOR());
                    }
                    if (countY > 0) {
                        exclude.add(excludeY.getCOLOR());
                    }
                    board[i][j] = new SimpleTile(getRandomWithExclusion(exclude));
                    exclude.clear();
                }
            }
        } while (!isPlayable());
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void toClick(int i, int j) {
        System.out.println("click");
        Tuple click = new Tuple(i, j);
        if (previousClick == null)
            previousClick = click;
        else if (isNeighbours(previousClick, click)) {
            if (isSwappable(previousClick, click))
                swap(previousClick, click);
            previousClick = null;
        } else
            previousClick = click;

        if (!isPlayable())
            System.out.println("GameOver");

    }

    public void myNotifyObserver(){
        setChanged();
        notifyObservers();
    }

    /**
     * Copy the board to test operations on such as checking if tiles are swappable
     *
     * @return a copy deep copy of the board
     */
    private Tile[][] copyBoard() {
        Tile [][] boardCopy = new Tile[HEIGHT][];
        for(int k = 0; k < WIDTH; k++)
        {
            Tile[] boardRow = board[k];
            boardCopy[k] = new Tile[WIDTH];
            System.arraycopy(boardRow, 0, boardCopy[k], 0, WIDTH);
        }
        return boardCopy;
    }

    private void swap(Tuple t1, Tuple t2) {
        int x1 = t1.getX(), x2 = t2.getX(), y1 = t1.getY(), y2 = t2.getY();
        Tile swap1 = board[y1][x1];
        Tile swap2 = board[y2][x2];
        board[y1][x1] = swap2;
        board[y2][x2] = swap1;

        clearGroups();
    }

    private Random rnd = new Random();

    //Returns random int 0 to 5 (inclusive)
    private TileColor getRandomColour(){
        return TileColor.colourList.get(rnd.nextInt(6));
    }

    //Gets random except for the numbers in Set argument
    private TileColor getRandomWithExclusion(Set<TileColor> exclude) {
        TileColor random;
        do
            random = getRandomColour();
        while (exclude.contains(random));
        return random;
    }

    /**
     * Prints the board for debugging purposes
     */
    private void printBoard(){
        printBoard(this.board);
    }

    /**
     * Prints a particular board for debugging purposes
     * @param board a board to print
     */
    private void printBoard(Tile[][] board){
        for (Tile[] aBoard : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (aBoard[j] == null) {
                    System.out.print("NUL | ");
                } else {
                    System.out.print(aBoard[j].getCOLOR().toString() + " | ");
                }

            }
            System.out.println();
        }
    }

    /**
     *
     * @param a tile coordinate
     * @param b tile coordinate
     * @return true if swapping a and b results in a group of tiles to destroy
     */
    private boolean isSwappable(Tuple a, Tuple b) {
        if (!checkLimits(a) || !checkLimits(b))
            return false;

        Tile[][] boardCopy = copyBoard();
        int aX = a.getX(), aY = a.getY(), bX = b.getX(), bY = b.getY();
        Tile tmp = boardCopy[aY][aX];
        boardCopy[aY][aX] = boardCopy[bY][bX];
        boardCopy[bY][bX] = tmp;
        if (isVerticalNeighbour(a, b)) {
            // swapping vertically
            if (columnGroups(boardCopy, aX).size() > 0)
                return true;
            for (int j = Math.min(aY, bY); j <= Math.max(aY, bY); j++) {
                if (rowGroups(boardCopy, j).size() > 0)
                    return true;
            }
        } else if (isHorizontalNeighbour(a, b)) {
            // swapping horizontally
            if (rowGroups(boardCopy, aY).size() > 0)
                return true;
            for (int j = Math.min(aX, bX); j <= Math.max(aX, bX); j++) {
                if (columnGroups(boardCopy, j).size() > 0)
                    return true;
            }
        }
        return false;
    }

    /**
     *
     * @param X the coordinate of the column
     * @return the coordinates of tiles to be removed in a column X
     */
    private Set<Tuple> columnGroups(final int X) {
        return columnGroups(this.board, X);
    }

    /**
     *
     * @param board the tile matrix to check for groups
     * @param X the coordinate of the column
     * @return the coordinates of tiles to be removed in a column X
     */
    private Set<Tuple> columnGroups(final Tile[][] board, final int X) {
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

    /**
     *
     * @param Y the coordinate of the row
     * @return the coordinates of tiles to be removed in a row Y
     */
    private Set<Tuple> rowGroups(final int Y) {
        return rowGroups(this.board, Y);
    }

    /**
     *
     * @param board the tile matrix to check for groups
     * @param Y the coordinate of the row
     * @return the coordinates of tiles to be removed in a row Y
     */
    private Set<Tuple> rowGroups(final Tile[][] board, final int Y) {
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

    /**
     * Checks if two coordinates are adjacent the vertically direction
     * @param a tile coordinate
     * @param b tile coordinate
     * @return whether two coordinates are next to each other vertically
     */
    private boolean isVerticalNeighbour(Tuple a, Tuple b) {
        int aX = a.getX(), bX = b.getX(), aY = a.getY(), bY = b.getY();
        return ((checkLimits(a) && checkLimits(b)) &&
                (aX == bX && Math.abs(aY - bY) == 1));
    }

    /**
     * Checks if two coordinates are adjacent the horizontally direction
     * @param a tile coordinate
     * @param b tile coordinate
     * @return whether two coordinates are next to each other horizontally
     */
    private boolean isHorizontalNeighbour(Tuple a, Tuple b) {
        int aX = a.getX(), bX = b.getX(), aY = a.getY(), bY = b.getY();
        return ((checkLimits(a) && checkLimits(b)) &&
                (aY == bY && Math.abs(aX - bX) == 1));
    }

    /**
     * Checks if two coordinates are adjacent to each other
     * @param a tile coordinate
     * @param b tile coordinate
     * @return whether two coordinates are adjacent to each other
     */
    private boolean isNeighbours(Tuple a, Tuple b) {
        return (isVerticalNeighbour(a, b) || isHorizontalNeighbour(a, b));
    }

    /**
     * @param t tile coordinate
     * @return true if coordinate falls within board dimensions
     */
    private boolean checkLimits(Tuple t) {
        int x = t.getX(), y = t.getY();
        return (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT);
    }

    //Set the tiles to be removed to null
    private void remove(Set<Tuple> removable){
        for (Tuple t : removable){
            board[t.getY()][t.getX()] = null;
        }
    }

    //Slide the board down to remove all removables
    private void slideDown(){
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
    private void fillNull(){
        for(int i=0;i < board[0].length;i++){
            for (int j=0; j < board.length;j++){
                if (board[j][i] == null){
                    board[j][i] = new SimpleTile(getRandomColour());
                }else {
                    break;
                }
            }
        }
    }

    private boolean isPlayable() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (isSwappable(new Tuple(j,i), new Tuple(j + 1, i)))
                    return true;
                if (isSwappable(new Tuple(j, i), new Tuple(j, i + 1)))
                    return true;
            }
        }
        return false;
    }

    /**
     * Clears all groups in the board until no more groups exist
     */
    private void clearGroups(){
        Set<Tuple> toRemove = new HashSet<>();
        int count = 0;
        do {
            toRemove.clear();
            for (int i = 0; i < WIDTH; i++) {
                if (count==0) animateRemove.addAll(columnGroups(i));
                toRemove.addAll(columnGroups(i));
            }
            for (int j = 0; j < HEIGHT; j++) {
                if (count==0) animateRemove.addAll(rowGroups(j));
                toRemove.addAll(rowGroups(j));
            }
            count++;
            setChanged();
            notifyObservers(0);
            remove(toRemove);

            slideDown();
            fillNull();
        } while (toRemove.size() != 0);
        setChanged();
        notifyObservers(1);
    }
}
