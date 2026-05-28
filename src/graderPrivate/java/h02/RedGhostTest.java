package h02;

import fopbot.Direction;
import fopbot.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class RedGhostTest {

    private final int startX = 1;
    private final int startY = 1;
    private Context baseContext;

    @BeforeEach
    public void setup() {
        int worldHeight = 3;
        int worldWidth = 3;
        World.setSize(worldWidth, worldHeight);
        World.setDelay(0);
        baseContext = contextBuilder()
            .add("world height", worldHeight)
            .add("world width", worldWidth)
            .add("starting position (X|Y)", "(%d|%d)".formatted(startX, startY))
            .add("starting direction", Direction.UP)
            .build();
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testTurnToPacman(Direction direction) {
        RedGhost redGhost = Mockito.mock(Mockito.withSettings()
            .defaultAnswer(Mockito.CALLS_REAL_METHODS)
            .useConstructor(startX, startY, Mockito.mock(Pacman.class)));
        Mockito.when(redGhost.nextDirection()).thenReturn(direction);
        Mockito.doNothing().when(redGhost).move();

        try {
            redGhost.doMove();
        } catch (Exception e) {
            System.err.printf("RedGhost.doMove() threw an unexpected exception: %s%n", e);
        }

        Context context = contextBuilder()
            .add(baseContext)
            .add("nextDirection() return value", direction)
            .add("move() action", "none")
            .add("walls", "none")
            .build();
        assertEquals(direction,
            redGhost.getDirection(),
            context,
            r -> "Red ghost did not face the correct direction after invoking doMove()");
    }

    @Test
    public void testTurnLeft() {
        World.placeVerticalWall(startX - 1, startY);
        World.placeVerticalWall(startX, startY);
        World.placeHorizontalWall(startX, startY);
        RedGhost redGhost = Mockito.mock(Mockito.withSettings()
            .defaultAnswer(Mockito.CALLS_REAL_METHODS)
            .useConstructor(startX, startY, Mockito.mock(Pacman.class)));
        Mockito.when(redGhost.nextDirection()).thenReturn(Direction.UP);
        Mockito.doNothing().when(redGhost).move();

        try {
            redGhost.doMove();
        } catch (Exception e) {
            System.err.printf("RedGhost.doMove() threw an unexpected exception: %s%n", e);
        }

        Context context = contextBuilder()
            .add(baseContext)
            .add("nextDirection() return value", Direction.UP)
            .add("move() action", "none")
            .add("walls", "to the left, above and to the right of the robot's position")
            .build();
        assertEquals(Direction.DOWN,
            redGhost.getDirection(),
            context,
            result -> "Red ghost does not face the correct direction to avoid walls in its path");
    }

    @Test
    public void testMove() {
        RedGhost redGhost = Mockito.mock(Mockito.withSettings()
            .defaultAnswer(Mockito.CALLS_REAL_METHODS)
            .useConstructor(startX, startY, Mockito.mock(Pacman.class)));
        Mockito.when(redGhost.nextDirection()).thenReturn(Direction.UP);

        try {
            redGhost.doMove();
        } catch (Exception e) {
            System.err.printf("RedGhost.doMove() threw an unexpected exception: %s%n", e);
        }

        Context context = contextBuilder()
            .add(baseContext)
            .add("nextDirection() return value", Direction.UP)
            .add("move() action", "default")
            .add("walls", "none")
            .build();
        assertEquals(startX, redGhost.getX(), context, result -> "Red ghost's x-coordinate is incorrect");
        assertEquals(startY + 1, redGhost.getY(), context, result -> "Red ghost's y-coordinate is incorrect");
    }
}
