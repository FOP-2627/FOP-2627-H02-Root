package h02;

import fopbot.Robot;
import fopbot.Transition;
import fopbot.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.List;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class BlueGhostTest {

    private BlueGhost blueGhost;
    private Context context;

    @BeforeEach
    public void setup() {
        int worldHeight = 5;
        int worldWidth = 5;
        int startX = 2;
        int startY = 2;
        World.setSize(worldWidth, worldHeight);
        World.setDelay(0);
        World.placeVerticalWall(startX - 1, startY);
        World.placeHorizontalWall(startX, startY);
        blueGhost = new BlueGhost(startX, startY);
        context = contextBuilder()
            .add("world width", worldWidth)
            .add("world height", worldHeight)
            .add("blue ghost (initial state)", blueGhost.toString())
            .add("walls", "to the left and above the blue ghost's position")
            .build();
    }

    @Test
    public void testTurns() {
        try {
            blueGhost.doMove();
        } catch (Exception e) {
            System.err.printf("BlueGhost.doMove() threw an unexpected exception: %s%n", e);
        }
        List<Transition.RobotAction> robotActions = getRobotActions(blueGhost, 0, 8);
        assertEquals(8, robotActions.size(), context, r ->
            "Blue ghost did not perform the required amount of actions");

        List<Transition.RobotAction> rightTurnActions = List.of(
            Transition.RobotAction.TURN_LEFT,
            Transition.RobotAction.TURN_LEFT,
            Transition.RobotAction.TURN_LEFT
        );
        assertEquals(Transition.RobotAction.TURN_LEFT, robotActions.getFirst(), context, r ->
            "Blue ghost did not turn left as first action");
        assertEquals(rightTurnActions, robotActions.subList(1, 3 + 1), context, r ->
            "Blue ghost did not turn right (3 left turns) when facing the left wall");
        assertEquals(rightTurnActions, robotActions.subList(4, 6 + 1), context, r ->
            "Blue ghost did not turn right (3 left turns) when facing the upper wall");
        assertNotEquals(Transition.RobotAction.TURN_LEFT, robotActions.getLast(), context, result ->
            "Blue ghost turned left when facing no wall");
    }

    @Test
    public void testMove() {
        try {
            blueGhost.doMove();
        } catch (Exception e) {
            System.err.printf("BlueGhost.doMove() threw an unexpected exception: %s%n", e);
        }

        List<Transition.RobotAction> expected = List.of(
            Transition.RobotAction.MOVE,
            Transition.RobotAction.NONE
        );
        List<Transition.RobotAction> actual = getRobotActions(blueGhost, 7, 9);
        assertEquals(expected, actual, context, result -> "Blue ghost did not move one step after turning");
    }

    private static List<Transition.RobotAction> getRobotActions(Robot robot, int from, int to) {
        List<Transition> transitions = World.getGlobalWorld()
            .getTrace(robot)
            .getTransitions();
        return transitions
            .subList(Math.min(from, transitions.size()), Math.min(to, transitions.size()))
            .stream()
            .map(transition -> transition.action)
            .toList();
    }
}
