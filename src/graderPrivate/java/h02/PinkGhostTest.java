package h02;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.invocation.InvocationOnMock;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import fopbot.Direction;
import fopbot.World;
import h02.template.Util;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class PinkGhostTest {

    private final int startX = 2;
    private final int startY = 2;
    private PinkGhost pinkGhost;
    private Context baseContext;

    @BeforeEach
    public void setup() {
        int worldHeight = 5;
        int worldWidth = 5;
        World.setSize(worldWidth, worldHeight);
        World.setDelay(0);
        World.placeHorizontalWall(startX, startY - 1);
        World.placeHorizontalWall(startX, startY);
        pinkGhost = new PinkGhost(startX, startY);
        baseContext = contextBuilder()
            .add("world height", worldHeight)
            .add("world width", worldWidth)
            .add("starting position (X|Y)", "(%d|%d)".formatted(startX, startY))
            .add("starting direction", Direction.UP)
            .add("walls", "above and below the pink ghost")
            .build();
    }

    @Test
    public void testPicksRandomFreePath() {
        Context context = contextBuilder()
            .add(baseContext)
            .add("Util.getRandomInteger(int, int) return value", 1)
            .build();
        try (MockedStatic<Util> utilMock = Mockito.mockStatic(Util.class, Mockito.CALLS_REAL_METHODS)) {
            List<InvocationOnMock> getRandomIntegerInvocations = new ArrayList<>();
            Mockito.when(Util.getRandomInteger(Mockito.anyInt(), Mockito.anyInt())).then(invocation -> {
                getRandomIntegerInvocations.add(invocation);
                return 1;
            });
            pinkGhost.doMove();

            try {
                utilMock.verify(() -> Util.getRandomInteger(Mockito.anyInt(), Mockito.anyInt()), Mockito.atLeastOnce());
            } catch (MockitoAssertionError e) {
                fail(e, context, r -> "Util.getRandomInteger(int, int) was not called at least once");
            }
            for (InvocationOnMock invocation : getRandomIntegerInvocations) {
                assertEquals(1, invocation.getArgument(0), context, r ->
                    "doMove() did not call Util.getRandomInteger(int, int) with the correct first argument");
                assertEquals(2, invocation.getArgument(1), context, r ->
                    "doMove() did not call Util.getRandomInteger(int, int) with the correct second argument");
            }
        } catch (Exception e) {
            System.err.printf("PinkGhost.doMove() threw an unexpected exception: %s%n", e);
        }
    }

    @Test
    public void testTurnToFreePath() {
        Context context = contextBuilder()
            .add(baseContext)
            .add("Util.getRandomInteger(int, int) return value", 2)
            .build();
        try (MockedStatic<Util> utilMock = Mockito.mockStatic(Util.class, Mockito.CALLS_REAL_METHODS)) {
            Mockito.when(Util.getRandomInteger(Mockito.anyInt(), Mockito.anyInt())).thenReturn(2);
            pinkGhost.doMove();

            try {
                utilMock.verify(() -> Util.getRandomInteger(Mockito.anyInt(), Mockito.anyInt()), Mockito.atLeastOnce());
            } catch (MockitoAssertionError e) {
                fail(e, context, r -> "Util.getRandomInteger(int, int) was not called at least once");
            }
        } catch (Exception e) {
            System.err.printf("PinkGhost.doMove() threw an unexpected exception: %s%n", e);
        }

        assertEquals(Direction.RIGHT, pinkGhost.getDirection(), context,
                result -> "Pink ghost does not face the right direction");
    }

    @Test
    public void testMove() {
        Context context = contextBuilder()
            .add(baseContext)
            .add("Util.getRandomInteger(int, int) return value", 1)
            .build();
        try (MockedStatic<Util> utilMock = Mockito.mockStatic(Util.class, Mockito.CALLS_REAL_METHODS)) {
            Mockito.when(Util.getRandomInteger(Mockito.anyInt(), Mockito.anyInt())).thenReturn(1);
            pinkGhost.doMove();

            try {
                utilMock.verify(() -> Util.getRandomInteger(Mockito.anyInt(), Mockito.anyInt()), Mockito.atLeastOnce());
            } catch (MockitoAssertionError e) {
                fail(e, context, r -> "Util.getRandomInteger(int, int) was not called at least once");
            }
        } catch (Exception e) {
            System.err.printf("PinkGhost.doMove() threw an unexpected exception: %s%n", e);
        }

        assertEquals(startX + Direction.LEFT.getDx(), pinkGhost.getX(), context,
                result -> "The pink ghost's x-coordinate is incorrect");
        assertEquals(startY + Direction.LEFT.getDy(), pinkGhost.getY(), context,
                result -> "The pink ghost's y-coordinate is incorrect");
    }
}
