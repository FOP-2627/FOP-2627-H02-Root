package h02.template;

import java.util.Random;

/**
 * Utility class for the ghosts
 */
public class Util {

    private static Random rnd = new Random();

    @SuppressWarnings("unused")
    public static void setRandom(Random random) {
        rnd = random;
    }

    /**
     * Returns a random integer between min and max (both inclusive)
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return a random integer between min and max (both inclusive)
     */
    public static int getRandomInteger(int min, int max) {
        return min + rnd.nextInt(max - min + 1);
    }

    public static boolean getRandomBoolean() {
        return rnd.nextBoolean();
    }
}
