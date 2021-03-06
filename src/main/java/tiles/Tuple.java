package tiles;

public class Tuple {

    private final int x;
    private final int y;

    public Tuple(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null && obj.getClass() == Tuple.class && obj.hashCode() == this.hashCode());
    }
}
