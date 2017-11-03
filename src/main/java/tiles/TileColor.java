package tiles;

import java.util.*;

public enum TileColor {
    RED(0), PINK(1), GREEN(2), BLUE(3), PURPLE(4), YELLOW(5);

    private final int value;

    TileColor(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name().toLowerCase().substring(0,3);
    }

    public static List<TileColor> colourList = new ArrayList<>(Arrays.asList(RED, PINK, GREEN, BLUE, PURPLE, YELLOW));

    public static TileColor getRandomColour() {
        Random rnd = new Random();
        return colourList.get(rnd.nextInt(6));
    }

    public static TileColor getRandomColourExcluding(Set<TileColor> exclude) {
        TileColor random;
        do
            random = getRandomColour();
        while (exclude.contains(random));
        return random;
    }
}
