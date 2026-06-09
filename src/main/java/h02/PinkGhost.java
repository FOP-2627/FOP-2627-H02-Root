package h02;

import org.tudalgo.algoutils.student.annotation.SolutionOnly;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import fopbot.Robot;
import h02.template.Families;
import h02.template.Ghost;
import h02.template.TickBased;
import h02.template.Util;

/**
 * The {@link PinkGhost} is a {@link Robot} that looks like a pink ghost.
 * It tries to move in a random direction.
 */
public class PinkGhost extends Robot implements Ghost, TickBased {
    /**
     * Creates a new {@link PinkGhost} at the given position.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public PinkGhost(int x, int y) {
        super(x, y, Families.GHOST_PINK);
    }

    /**
     * Finds all free directions turning clockwise and counts them. Then it
     * generates a random number between 1 and the number of free directions and
     * turns to the corresponding direction and moves forward.
     */
    @Override
    @StudentImplementationRequired("H2.2.4")
    public void doMove() {
        int freeLanes = 0;
        for (int i = 0; i < 4; i++) {
            turnRight();
            if (isFrontClear()) {
                freeLanes++;
            }
        }

        int rand = Util.getRandomInteger(1, freeLanes);
        for (int i = 0; i < rand; i++) {
            do {
                turnLeft();
            } while (!isFrontClear());
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
