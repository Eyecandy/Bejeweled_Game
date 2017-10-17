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

    Tile[][] board;
    private Stack<Tuple> clickStack= new Stack<Tuple>();

    public void toClick(int i, int j) {
        System.out.println("click");
        clickStack.add(new Tuple(i, j));
        if (clickStack.size() > 1) {
            Tuple t1 = clickStack.pop();
            Tuple t2 = clickStack.pop();
            //System.out.println("brdaou");
            //printBoard(board);
            Tile [][] tmpbrd = new Tile[board.length][];
            for(int k = 0; k < board.length; k++)
            {
                Tile[] aMatrix = board[k];
                int  aLength = aMatrix.length;
                tmpbrd[k] = new Tile[aLength];
                System.arraycopy(aMatrix, 0, tmpbrd[k], 0, aLength);
            }
            if (isSwappable(tmpbrd, t1, t2)){
                //System.out.println("swap");
                //printBoard(board);
                //System.out.println("beforeSwap ^");
                //System.out.println("swapping     " + t1.toString() + " and " + t2.toString());
                swap(t1, t2);
                //System.out.println("new");
                //printBoard(board);
            }
            clickStack.clear();
        }

    }

    public void swap (Tuple t1, Tuple t2) {

        int x1 = t1.x, x2 = t2.x, y1 = t1.y, y2 = t2.y;
        Tile swap1 = board[y1][x1];
        Tile swap2 = board[y2][x2];
        board[y1][x1] = swap2;
        board[y2][x2] = swap1;

        clearGroups(board);

        setChanged();
        //System.out.println("notified");
        notifyObservers();
        //System.out.println("BOARD WITH NULL");
        //this.printBoard(board);
        //System.out.println("SLIDE DOWN");

        //gameLogic.printBoard(board);
        //System.out.println("FILL UP NULL TILES WITH RANDOM");

        //gameLogic.printBoard(board);
    }

    private Random rnd = new Random();

    //Returns random int 0 to 4 (inclusive)
    public int getRandom(){
        return rnd.nextInt(5);
    }

    //Gets random except for the numbers in Set argument
    public int getRandomWithExclusion(Set<Integer> exclude) {
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
        Set<Integer> exclude = new HashSet<Integer>();
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
//        printBoard(board);
//        System.out.println(hasRemovable(board));
    }

    //Checks if the board has any 3 or more which can be removed
    public boolean hasRemovable(Tile[][] board){
        int row = board.length;
        int column = board[0].length;
        Tile currentTile = board[0][0];
        int count;
        //horizontal
        for(int i = 0;i < row; i++){
            count = 0;
            currentTile = board[i][0];
            for(int j=0;j < column; j++){
                if (count == 3){
                    System.out.println("HORIZONTAl");
                    return true;
                }
                if (currentTile.compareColor(board[i][j])){
                    count++;
                }else {
                    currentTile = board[i][j];
                    count = 1;
                }
            }
        }
        //vertical
        for(int k = 0; k < column;k++){
            count = 0;
            currentTile = board[0][k];
            for(int l = 0; l < row ; l++){
                if (count == 3){
                    System.out.println("VERTICAL");
                    return true;
                }
                if (currentTile.compareColor(board[l][k])){
                    count++;
                }else {
                    currentTile = board[l][k];
                    count = 1;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic();
        gameLogic.init(10,8);
        Tile[][] board = gameLogic.getBoard();
        board[3][3] = gameLogic.newTile(0);
        board[3][4] = gameLogic.newTile(1);
        board[3][5] = gameLogic.newTile(0);
        board[2][4] = gameLogic.newTile(0);
        board[2][3] = gameLogic.newTile(0);
        board[4][4] = gameLogic.newTile(0);
        board[5][4] = gameLogic.newTile(0);
        board[6][4] = gameLogic.newTile(0);
        board[7][4] = gameLogic.newTile(0);
        System.out.println(board[0][0]);
        gameLogic.printBoard(board);
//        Set<Tuple> removable = new HashSet<Tuple>();
//        Tile swap1 = board[3][4];
//        Tile swap2 = board[2][4];
//        boolean canSwap = false;
//        if (gameLogic.isSwappable(board,2,4,3,4)){
//            swap1 = board[3][4];
//            swap2 = board[2][4];
//            canSwap = true;
//        }
//        System.out.println("SWAP ROW 2, COL 4 WITH ROW 3, COL 4");
//        System.out.println("AFTER");
//        if (canSwap){
//            board[3][4] = swap2;
//            board[2][4] = swap1;
//            boolean removeH1 = gameLogic.isRemovableH(board,3,4,removable);
//            boolean removeV1 = gameLogic.isRemovableV(board,3,4,removable);
//
//            if (!removeH1 && !removeV1){
//                board[3][4] = swap1;
//                board[2][4] = swap2;
//                removable.clear();
//            }else {
//                gameLogic.remove(removable,board);
//                removable.clear();
//            }
//            System.out.println("BOARD WITH NULL");
//            gameLogic.printBoard(board);
//            System.out.println("SLIDE DOWN");
//            gameLogic.slideDown(board);
//            gameLogic.printBoard(board);
//            System.out.println("FILL UP NULL TILES WITH RANDOM");
//            gameLogic.fillNull(board);
//            gameLogic.printBoard(board);
//        }
        gameLogic.clearGroups(board);
    }

    //Creates new tile of a certain color
    public Tile newTile(int index){
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
    public void printBoard(Tile[][] board){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if (board[i][j] == null){
                    System.out.print("NULL | ");
                }else{
                    System.out.print(board[i][j].getCOLOR().toString() + " | ");
                }

            }
            System.out.println();
        }
    }

    //Checks if any 2 tiles are swappable(check their colors and if they are neighbors)
    public boolean isSwappable(Tile[][] board,int a, int b, int c, int d){
        if (a==c && b==d){
            System.out.println("1");
            return false;
        }
        if (a < 0 || b < 0 || c < 0 || d < 0){
            System.out.println("2");
            return false;
        }
        if (a > board.length || c > board.length || b > board[0].length || d > board[0].length ){
            System.out.println("3");
            return false;
        }
        if (((a==c+1) || a==(c-1) || (a==c)) && (b==d+1 || b==d-1) || (b==d)){
            if (!board[a][b].compareColor(board[c][d])){
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean isSwappable(Tile[][] board, Tuple a, Tuple b) {
        int aX = a.getX(), aY = a.getY(), bX = b.getX(), bY = b.getY();
        Tile tmp = board[aY][aX];
        board[aY][aX] = board[bY][bX];
        board[bY][bX] = tmp;
        //printBoard(board);
        int width = board[0].length, height = board.length;
        if (checkLimits(aX, aY, width, height) && checkLimits(bX, bY, width, height)) {
            if (aX == bX && Math.abs(aY - bY) == 1) {
                // swapping vertically
                TileColor currentColour = TileColor.YELLOW;
                Tile currentTile;
                int count = 1;
                for (int i = 0; i < height; i++) {
                    System.out.println(aX + ", " + i);
                    if (i == 0) {
                        currentColour = board[i][aX].getCOLOR();
                        count = 1;
                    } else {
                        currentTile = board[i][aX];
                        //System.out.println(currentTile.getCOLOR());
                        //System.out.println(count);
                        if (currentTile.getCOLOR() == currentColour) {
                            count++;
                            if (count >= 3) {
                                return true;
                            }
                        } else {
                            currentColour = currentTile.getCOLOR();
                            count = 1;
                        }
                    }
                }
                //System.out.println("Vert row");
                for (int j = Math.min(aY, bY); j <= Math.max(aY, bY); j++) {
                    //System.out.println("row " + j);
                    for (int i = 0; i < width; i++) {
                        //System.out.println(i + ", " + j);
                        if (i == 0) {
                            currentColour = board[j][i].getCOLOR();
                            count = 1;
                        } else {
                            currentTile = board[j][i];
                            //System.out.println(currentTile.getCOLOR());
                            //System.out.println(count);
                            if (currentTile.getCOLOR() == currentColour) {
                                count++;
                                if (count >= 3) {
                                    return true;
                                }
                            } else {
                                currentColour = currentTile.getCOLOR();
                                count = 1;
                            }
                        }
                    }
                }
            } else if (aY == bY && Math.abs(aX - bX) == 1) {
                // swappinng horizontally
                TileColor currentColour = TileColor.YELLOW;
                Tile currentTile;
                int count = 1;
                //System.out.println("Horizontal row");
                for (int i = 0; i < width; i++) {
                    //System.out.println(i + ", " + aY);
                    if (i == 0) {
                        currentColour = board[aY][i].getCOLOR();
                        count = 1;
                    } else {
                        currentTile = board[aY][i];
                        //System.out.println(currentTile.getCOLOR());
                        //System.out.println(count);
                        if (currentTile.getCOLOR() == currentColour) {
                            count++;
                            if (count >= 3) {
                                return true;
                            }
                        } else {
                            currentColour = currentTile.getCOLOR();
                            count = 1;
                        }
                    }
                }
                System.out.println("horz col");
                for (int j = Math.min(aX, bX); j <= Math.max(aX, bX); j++) {
                    System.out.println("col " + j);
                    for (int i = 0; i < height; i++) {
                        //System.out.println(j + ", " + i);
                        if (i == 0) {
                            currentColour = board[i][j].getCOLOR();
                            count = 1;
                        } else {
                            currentTile = board[i][j];
                            //System.out.println(currentTile.getCOLOR());
                            //System.out.println(count);
                            if (currentTile.getCOLOR() == currentColour) {
                                count++;
                                if (count >= 3) {
                                    return true;
                                }
                            } else {
                                currentColour = currentTile.getCOLOR();
                                count = 1;
                            }
                        }
                    }
                }
            }
        }
        //System.out.println(false);
        return false;
    }

    private boolean checkLimits(int x, int y, int width, int height) {
        return (x >= 0 && y >= 0 && x < width && y < height);
    }
    //checks a tiles vertical neighbors to see if any can be removed
    // if it can be removed, boolean will be true and removable contains tiles to be removed
    // call this after checking if tile a,b is swappable
    public boolean isRemovableV(Tile[][] board,int a, int b, Set<Tuple> removable){
        int countV = 0;
//        System.out.println("Vertical");
        //vertical
        Tile currentTile = board[a][b];
        for (int i = a - 1; i >= 0; i--){
            if (currentTile.compareColor(board[i][b])){
                countV++;
                removable.add(new Tuple(i,b));
            }else {
                break;
            }
        }
        for (int j = a + 1; j < board.length; j++){
            if (currentTile.compareColor(board[j][b])){
                countV++;
                removable.add(new Tuple(j,b));
            }else {
                break;
            }
        }
        countV++;
        if (countV >= 3){
            removable.add(new Tuple(a,b));
            return true;
        }
        return false;
    }

    //checks a tiles horizontal neighbors to see if any can be removed
    // if it can be removed, boolean will be true and removable contains tiles to be removed
    // call this after checking if tile a,b is swappable
    public boolean isRemovableH(Tile[][] board,int a, int b,Set<Tuple> removable){
        int countH = 0;
//        System.out.println("Horizontal");
        //vertical
        Tile currentTile = board[a][b];
        for (int i = b - 1; i >= 0; i--){
            if (currentTile.compareColor(board[a][i])){
                countH++;
                removable.add(new Tuple(a,i));
            }else {
                break;
            }
        }
        for (int j = b + 1; j < board.length; j++){
            if (currentTile.compareColor(board[a][j])){
                removable.add(new Tuple(a,j));
                countH++;
            }else {
                break;
            }
        }
        countH++;
        if (countH >= 3){
            removable.add(new Tuple(a,b));
            return true;
        }
        return false;
    }

    //Set the tiles to be removed to null
    public void remove(Set<Tuple> removable, Tile[][] board){
        for (Tuple t : removable){
            board[t.getY()][t.getX()] = null;
        }
    }

    //Slide the board down to remove all removables
    public void slideDown(Tile[][] board){
        int row = board.length;
        int column = board[0].length;
        int count;
        ArrayList<Tile> temp = new ArrayList<>();
        for (int i=0;i < column;i++){
            for(int j=0; j < row; j++){
                if (board[j][i] != null){
                    temp.add(board[j][i]);
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
    public void fillNull(Tile[][] board){
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

    public void clearGroups(Tile[][] board){
        boolean repeat;
        TileColor currentColour = TileColor.YELLOW;
        Set<Tuple> toRemove = new HashSet<>();
        Set<Tuple> currentGroup = new HashSet<>();
        Tile currentTile;
        //int counter = 0;
        do {
            //printBoard(board);
            repeat = false;
            currentGroup.clear();
            toRemove.clear();
            for (int i = 0; i < board[0].length; i++) {
                for (int j = 0; j < board.length; j++) { // each row of column
                    if (j == 0) {
                        currentColour = board[j][i].getCOLOR();
                        currentGroup.clear();
                        currentGroup.add(new Tuple(i, j));
                    } else {
                        currentTile = board[j][i];
                        if (currentTile.getCOLOR() == currentColour) {
                            currentGroup.add(new Tuple(i, j));
                            if (currentGroup.size() >= 3) {
                                toRemove.addAll(currentGroup);
                                repeat = true;
                            }
                        } else {
                            currentColour = currentTile.getCOLOR();
                            currentGroup.clear();
                            currentGroup.add(new Tuple(i, j));
                        }
                    }

                }
            }
            for (int j = 0; j < board.length; j++) {
                for (int i = 0; i < board[0].length; i++) {
                    if (i == 0) {
                        currentColour = board[j][i].getCOLOR();
                        currentGroup.clear();
                        currentGroup.add(new Tuple(i, j));
                    } else {
                        currentTile = board[j][i];
                        if (currentTile.getCOLOR() == currentColour) {
                            currentGroup.add(new Tuple(i, j));
                            if (currentGroup.size() >= 3) {
                                toRemove.addAll(currentGroup);
                                repeat = true;
                            }
                        } else {
                            currentColour = currentTile.getCOLOR();
                            currentGroup.clear();
                            currentGroup.add(new Tuple(i, j));
                        }
                    }

                }

            }

            //System.out.println(toRemove);
            remove(toRemove, board);
            slideDown(board);
            fillNull(board);
        } while (repeat);
        //printBoard(board);
    }
}
