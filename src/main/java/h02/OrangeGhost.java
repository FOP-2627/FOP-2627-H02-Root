package h02;

import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import fopbot.Robot;
import h02.template.Families;
import h02.template.Ghost;
import h02.template.TickBased;
import h02.template.Util;

/**
 * {@link OrangeGhost} is a {@link Robot} that looks like an orange ghost.
 * It tries to move in a straight line and alternates between turning left and
 * right.
 */
public class OrangeGhost extends Robot implements Ghost, TickBased {
    /**
     * Creates a new {@link OrangeGhost} at the given position.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public OrangeGhost(int x, int y) {
        super(x, y, Families.GHOST_ORANGE);
    }

    /**
     * Moves the robot in a straight line if possible.
     * If the robot cannot move forward, it turns left or right until there is no
     * wall in front.
     * The robot alternates between turning left and right.
     */
    @Override
    @StudentImplementationRequired("H2.3")
    public void doMove() {
        if (isFrontClear()) {
            move();
        } else {
            if (Util.getRandomBoolean()) {
                turnLeft();
            } else {
                turnLeft();
                turnLeft();
                turnLeft();
            }

            int randomNumber = Util.getRandomInteger(0, 2627);
            if (randomNumber % 4 == 0 && isFrontClear()) {
                move();
            }
        }
    }
}
