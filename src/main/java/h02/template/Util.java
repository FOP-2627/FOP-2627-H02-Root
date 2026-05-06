package h02.template;

import java.util.Random;

import fopbot.Direction;
import fopbot.Robot;

/**
 * Utility class for the ghosts
 */
public class Util {

    private static Random rnd = new Random();

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

    /**
     * Returns the direction in which the robot is the closest to the chaser
     *
     * @param pacman the robot that is being chased
     * @param chaser the robot that is chasing
     * @return the direction in which the robot is the closest to the chaser
     */
    public static Direction furthestDirection(Robot pacman, Robot chaser) {
        int px = pacman.getX();
        int py = pacman.getY();
        int cx = chaser.getX();
        int cy = chaser.getY();

        int xdelta = Math.abs(px - cx);
        int ydelta = Math.abs(py - cy);

        if (xdelta > ydelta) {
            if (cx > px)
                return Direction.LEFT;
            else
                return Direction.RIGHT;
        } else {
            if (cy > py)
                return Direction.DOWN;
            else
                return Direction.UP;
        }
    }

}
