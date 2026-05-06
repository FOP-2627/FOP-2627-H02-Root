package h02;

import org.tudalgo.algoutils.student.annotation.SolutionOnly;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import fopbot.Robot;
import h02.template.Families;
import h02.template.Ghost;
import h02.template.TickBased;

/**
 * The {@link BlueGhost} is a {@link Robot} that looks like a blue ghost.
 * It tries to move in a circle.
 */
public class BlueGhost extends Robot implements Ghost, TickBased {
    /**
     * Creates a new {@link BlueGhost} at the given position.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public BlueGhost(int x, int y) {
        super(x, y, Families.GHOST_BLUE);
    }

    /**
     * Turns the robot to the right and then moves in the first direction that is
     * clear.
     */
    @Override
    @StudentImplementationRequired("H2.1")
    public void doMove() {
        turnRight();
        while (!isFrontClear()) {
            turnLeft();
        }
        move();
    }

    /**
     * Turns the robot to the right.
     *
     */
    @SolutionOnly
    private void turnRight() {
        for (int i = 0; i < 3; i++) {
            turnLeft();
        }
    }
}
