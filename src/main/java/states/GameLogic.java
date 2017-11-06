package states;

import Events.DestroyEvent;
import Events.EventBus;
import panels.Board;
import strategy.Strategist;
import strategy.TileRemovalStrategy;
import tiles.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import static tiles.TileColor.getRandomColour;
import static tiles.TileColor.getRandomColourExcluding;

public class GameLogic extends Observable{

    private final Board BOARD;



    private final int WIDTH;



    private final int HEIGHT;
    private Tuple previousClick = null;
    private TileFactory tileFactory;
    private EventBus eventBus;
    private Strategist strategist = new Strategist(this);
    public int getHEIGHT() {
        return HEIGHT;
    }
    public int getWIDTH() {
        return WIDTH;
    }


    GameLogic(int rows, int columns, Board BOARD) {
        super();
        this.HEIGHT = rows;
        this.WIDTH = columns;
        this.tileFactory = new TileFactory();
        this.eventBus = EventBus.getInstance();
        this.BOARD = BOARD;

        InitialiseBoard(this.WIDTH, this.HEIGHT);
    }


    private void InitialiseBoard(final int WIDTH, final int HEIGHT) {
        Tile[][] tmpBoard = new Tile[HEIGHT][WIDTH];
        int countX;
        int countY;
        Tile excludeX = tileFactory.createTile(getRandomColour());
        Tile excludeY = tileFactory.createTile(getRandomColour());
        Set<TileColor> exclude = new HashSet<>();
        do {
            for (int i = HEIGHT - 1; i >= 0; i--) {
                for (int j = 0; j < WIDTH; j++) {
                    //Horizontal
                    countX = 0;
                    for (int k = j - 1; k >= 0; k--) {
                        if (k == (j - 1)) {
                            excludeX = tmpBoard[i][k];
                        } else {
                            if (excludeX.compareColor(tmpBoard[i][k])) {
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
                            excludeY = tmpBoard[l][j];
                        } else {
                            if (excludeY.compareColor(tmpBoard[l][j])) {
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
                    Tile newTile = tileFactory.createTile(getRandomColourExcluding(exclude));
                    tmpBoard[i][j] = newTile;
                    BOARD.setTile(j, i, newTile);
                    exclude.clear();
                }
            }
        } while (isNotPlayable());
    }

    public void toClick(int x, int y) {
        System.out.println("click");
        Tuple click = new Tuple(x, y);
        BOARD.addSelected(click);
        if (previousClick == null)
            previousClick = click;
        else if (isNeighbours(previousClick, click)) {
            BOARD.clearSelected();
            if (isSwappable(previousClick, click))
                swap(previousClick, click);
            previousClick = null;
        } else {
            BOARD.popSelected();
            previousClick = click;
        }

        if (isNotPlayable())
            System.out.println("GameOver");

    }

    /**
     * Swaps a pair to tiles and performs group clearing operation
     *
     * @param t1 tile coordinate
     * @param t2 tile coordinate
     */
    private void swap(Tuple t1, Tuple t2) {
        int x1 = t1.getX(), x2 = t2.getX(), y1 = t1.getY(), y2 = t2.getY();
        Tile swap1 = BOARD.getTile(x1, y1);
        Tile swap2 = BOARD.getTile(x2, y2);
        BOARD.setTile(x1, y1, swap2);
        BOARD.setTile(x2, y2, swap1);

        if (areSpecialTiles(t1, t2)) {
            Set<Tuple> toRemove = tilesToRemove(t1, new HashSet<>());
            toRemove.addAll(tilesToRemove(t2, toRemove));
        }

        clearGroups();

        setChanged();
        notifyObservers();

    }

    /**
     *
     * @param a a tile coordinate
     * @param b a tile coordinate
     * @return true if tiles at the two coordinates are the same colour
     */
    private boolean areSameColours(Tuple a, Tuple b) {
        int aX = a.getX(), aY = a.getY(), bX = b.getX(), bY = b.getY();

        Tile tileA = BOARD.getTile(aX, aY);
        Tile tileB = BOARD.getTile(bX, bY);

        return tileA.getCOLOR() == tileB.getCOLOR();
    }

    /**
     *
     * @param a a tile coordinate
     * @param b a tile coordinate
     * @return true if tiles at the two coordinates are not simpleTiles
     */
    private boolean areSpecialTiles(Tuple a, Tuple b) {
        int aX = a.getX(), aY = a.getY(), bX = b.getX(), bY = b.getY();

        Tile tileA = BOARD.getTile(aX, aY);
        Tile tileB = BOARD.getTile(bX, bY);

        return tileA.getTYPE() != TileType.SIMPLE && tileA.getTYPE() == tileB.getTYPE();
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
        if (areSameColours(a, b) && areSpecialTiles(a, b))
            return true;

        int aX = a.getX(), aY = a.getY(), bX = b.getX(), bY = b.getY();

        Tile[][] boardCopy = BOARD.getBoardCopy();
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
        return columnGroups(BOARD.getBoardCopy(), X);
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
                for (Tuple t: currentGroup) {
                    toRemove.addAll(tilesToRemove(t, new HashSet<>()));
                }
        }
        return toRemove;
    }

    /**
     *
     * @param Y the coordinate of the row
     * @return the coordinates of tiles to be removed in a row Y
     */
    private Set<Tuple> rowGroups(final int Y) {
        return rowGroups(BOARD.getBoardCopy(), Y);
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
                for (Tuple t: currentGroup) {
                    toRemove.addAll(tilesToRemove(t, new HashSet<>()));
                }
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
     * @return true if coordinate falls within BOARD dimensions
     */
    private boolean checkLimits(Tuple t) {
        int x = t.getX(), y = t.getY();
        return (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT);
    }

    /**
     * removes all tiles from the removable set from the BOARD
     * @param removable set of tile coordinates
     */
    private void remove(Set<Tuple> removable){
        try {
            eventBus.publish(new DestroyEvent(removable));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Tuple t : removable){
            BOARD.setTile(t.getX(), t.getY(), null);
        }
    }

    /**
     * slide down all tiles to fill in empty spaces beneath them
     */
    private void slideDown(){
        int count;
        ArrayList<Tile> temp = new ArrayList<>();
        for (int i = 0; i < BOARD.getWIDTH(); i++){
            for (int j = 0; j < BOARD.getHEIGHT(); j++) {
                if (BOARD.getTile(i, j) != null)
                    temp.add(BOARD.getTile(i, j));
            }
            count = temp.size() - 1;
            for(int k = BOARD.getHEIGHT()-1; k >= 0; k--){
                if (count < 0){
                    BOARD.setTile(i, k, null);
                }else {
                    BOARD.setTile(i, k, temp.get(count));
                    count--;
                }
            }
            temp.clear();
        }
    }

    /**
     * Fills null tiles in the BOARD with new random tiles
     */
    private void fillNull(){
        for(int i = 0; i < BOARD.getWIDTH(); i++){
            for (int j = 0; j < BOARD.getHEIGHT(); j++){
                if (BOARD.getTile(i, j) == null){
                    BOARD.setTile(i, j, tileFactory.createTile(getRandomColour()));
                }else {
                    break;
                }
            }
        }
    }

    /**
     * @return true if the game is no swaps can be made
     */
    private boolean isNotPlayable() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (isSwappable(new Tuple(j,i), new Tuple(j + 1, i)))
                    return false;
                if (isSwappable(new Tuple(j, i), new Tuple(j, i + 1)))
                    return false;
            }
        }
        return true;
    }


    public Set<Tuple> tilesToRemove(Tuple location, Set<Tuple> removed) {
        int y = location.getY();
        int x = location.getX();
        Tile tile = BOARD.getTile(x, y);
        TileRemovalStrategy strategy = strategist.findStrat(tile,location,removed,this);
        return strategy.execute();
    }

    /**
     * Clears all groups in the BOARD until no more groups exist
     */
    private void clearGroups(){
        Set<Tuple> toRemove = new HashSet<>();
        do {
            toRemove.clear();
            for (int i = 0; i < WIDTH; i++) {
                toRemove.addAll(columnGroups(i));
            }
            for (int j = 0; j < HEIGHT; j++) {
                toRemove.addAll(rowGroups(j));
            }
            remove(toRemove);
            slideDown();
            fillNull();
        } while (toRemove.size() != 0);
    }
}
