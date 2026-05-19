package h02;

import org.tudalgo.algoutils.student.annotation.SolutionOnly;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import fopbot.Direction;
import fopbot.Robot;
import h02.template.Families;
import h02.template.Ghost;
import h02.template.TickBased;

/**
 * The {@link RedGhost} is a {@link Robot} that looks like a red ghost.
 * It tries to move in the direction of the chased robot.
 */
public class RedGhost extends Robot implements Ghost, TickBased {
    private final Robot pacman;

    /**
     * Creates a new {@link RedGhost} at the given position.
     *
     * @param x      the x-coordinate
     * @param y      the y-coordinate
     * @param pacman the robot that is being chased
     */
    public RedGhost(int x, int y, Robot pacman) {
        super(x, y, Families.GHOST_RED);
        this.pacman = pacman;
    }

    /**
     * Returns the direction in which the robot is the closest to the pacman and
     * should turn to.
     *
     * @param pacman pacman the robot that is being chased
     * @return the direction in which the robot is the closest to the pacman and
     *         should turn to
     */
    public Direction nextDirection(Robot pacman) {
        int px = pacman.getX();
        int py = pacman.getY();
        int cx = this.getX();
        int cy = this.getY();

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

    /**
     * Moves the robot in the direction of the chased robot.
     */
    @Override
    @StudentImplementationRequired("H2.4")
    public void doMove() {
        turnDirection(nextDirection(pacman));
        while (!isFrontClear()) {
            turnLeft();
        }
        move();
    }

    /**
     * Turns the robot in the given direction.
     *
     * @param dir the direction to turn to
     */
    @SolutionOnly
    private void turnDirection(Direction dir) {
        while (getDirection() != dir) {
            turnLeft();
        }
    }

}
