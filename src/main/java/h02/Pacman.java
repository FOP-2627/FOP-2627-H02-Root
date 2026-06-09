package h02;

import static fopbot.Direction.DOWN;
import static fopbot.Direction.LEFT;
import static fopbot.Direction.RIGHT;
import static fopbot.Direction.UP;

import org.tudalgo.algoutils.student.annotation.SolutionOnly;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import fopbot.Direction;
import fopbot.Robot;
import h02.template.Controllable;
import h02.template.Families;
import h02.template.TickBased;

/**
 * {@link Pacman} is a {@link Robot} that can be controlled by the user and
 * looks like Pacman.
 * It can move in four directions and pick up coins.
 */
public class Pacman extends Robot implements Controllable, TickBased {
    /**
     * Creates a new {@link Pacman} at the given position.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Pacman(int x, int y) {
        super(x, y, Families.PACMAN);
    }

    /**
     * Handles the key input of the user.
     * The keys 0, 1, 2, 3 represent the arrow keys up, right, down, left.
     * If the key is not in this range, the method does nothing.
     * If the key is in the range, the robot turns in the corresponding direction,
     * moves one field and collects a coin if there is one.
     *
     * @param k the int value of the pressed key
     */
    @Override
    @StudentImplementationRequired("H2.1")
    public void handleKeyInput(int k) {
        if (k < 0 || k > 3) {
            return;
        }

        if (k == 0) {
            turnDirection(UP);
        } else if (k == 1) {
            turnDirection(RIGHT);
        } else if (k == 2) {
            turnDirection(DOWN);
        } else {
            turnDirection(LEFT);
        }

        if (isFrontClear()) {
            move();
        }

        if (isOnACoin()) {
            pickCoin();
        }
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
