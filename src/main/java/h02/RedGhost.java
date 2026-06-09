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
     * Returns the dirction where the robot should turn to in order to get closer to
     * pacman.
     *
     * @return the direction to turn to get closer to pacman
     */
    public Direction nextDirection() {
        int px = pacman.getX();
        int py = pacman.getY();
        int cx = this.getX();
        int cy = this.getY();

        int xDelta = Math.abs(px - cx);
        int yDelta = Math.abs(py - cy);

        if (xDelta > yDelta) {
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
     * Turns the robot in the direction of pacman. If the front is not clear, it
     * turns to the left until the front is clear and then moves forward.
     */
    @Override
    @StudentImplementationRequired("H2.2.2")
    public void doMove() {
        turnDirection(nextDirection());
        while (!isFrontClear()) {
            turnLeft();
        }
        move();
    }

    /**
     * Turns the robot to the given direction.
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
