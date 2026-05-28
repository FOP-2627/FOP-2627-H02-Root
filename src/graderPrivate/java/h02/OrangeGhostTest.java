package h02;

import fopbot.Direction;
import fopbot.World;
import h02.template.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.cartesian.CartesianTest;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.invocation.InvocationOnMock;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.ArrayList;
import java.util.List;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class OrangeGhostTest {

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
            .add("walls", "none")
            .build();
    }

    @Test
    public void testMoveUnblocked() {
        OrangeGhost orangeGhost = new OrangeGhost(startX, startY);

        try {
            orangeGhost.doMove();
        } catch (Exception e) {
            System.err.printf("OrangeGhost.doMove() threw an unexpected exception: %s%n", e);
        }

        assertEquals(startX, orangeGhost.getX(), baseContext, result -> "Orange ghost's x-coordinate is incorrect");
        assertEquals(startY + 1, orangeGhost.getY(), baseContext, result -> "Orange ghost's y-coordinate is incorrect");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testTurnBlocked(boolean turnLeft) {
        World.placeVerticalWall(startX - 1, startY);
        World.placeVerticalWall(startX, startY);
        World.placeHorizontalWall(startX, startY);
        OrangeGhost orangeGhost = Mockito.mock(Mockito.withSettings()
            .defaultAnswer(Mockito.CALLS_REAL_METHODS)
            .useConstructor(startX, startY));
        Mockito.doNothing().when(orangeGhost).move();

        Context context = contextBuilder()
            .add(baseContext)
            .add("Util.getRandomBoolean() return value", turnLeft)
            .add("Util.getRandomInteger(int, int)", 1)
            .add("walls", "to the left, above and to the right of the orange ghost")
            .build();
        try (MockedStatic<Util> utilMock = Mockito.mockStatic(Util.class, Mockito.CALLS_REAL_METHODS)) {
            Mockito.when(Util.getRandomBoolean()).thenReturn(turnLeft);
            Mockito.when(Util.getRandomInteger(Mockito.anyInt(), Mockito.anyInt())).thenReturn(1);
            orangeGhost.doMove();

            try {
                utilMock.verify(Util::getRandomBoolean, Mockito.atLeastOnce());
            } catch (MockitoAssertionError e) {
                fail(e, context, r -> "Util.getRandomBoolean() was not called at least once");
            }
        } catch (Exception e) {
            System.err.printf("OrangeGhost.doMove() threw an unexpected exception: %s%n", e);
        }

        assertEquals(turnLeft ? Direction.LEFT : Direction.RIGHT, orangeGhost.getDirection(), context, r ->
            "Orange ghost does not face the correct direction when its front is blocked");
    }

    @CartesianTest
    public void testMoveBlockedSides(
        @CartesianTest.Values(booleans = {true, false}) boolean turnLeft,
        @CartesianTest.Values(ints = {1000, 1001}) int randomInt
    ) {
        World.placeVerticalWall(startX - 1, startY);
        World.placeVerticalWall(startX, startY);
        World.placeHorizontalWall(startX, startY);
        testMoveBlocked(turnLeft, randomInt, true);
    }

    @CartesianTest
    public void testMoveBlockedFront(
        @CartesianTest.Values(booleans = {true, false}) boolean turnLeft,
        @CartesianTest.Values(ints = {1000, 1001}) int randomInt
    ) {
        World.placeHorizontalWall(startX, startY);
        testMoveBlocked(turnLeft, randomInt, false);
    }

    private void testMoveBlocked(boolean turnLeft, int randomInt, boolean sidesBlocked) {
        OrangeGhost orangeGhost = new OrangeGhost(startX, startY);

        Context context = contextBuilder()
            .add(baseContext)
            .add("Util.getRandomBoolean() return value", turnLeft)
            .add("Util.getRandomInteger(int, int)", randomInt)
            .add("walls", "to the left, above and to the right of the orange ghost")
            .build();
        try (MockedStatic<Util> utilMock = Mockito.mockStatic(Util.class, Mockito.CALLS_REAL_METHODS)) {
            List<InvocationOnMock> getRandomIntegerInvocations = new ArrayList<>();
            Mockito.when(Util.getRandomBoolean()).thenReturn(turnLeft);
            Mockito.when(Util.getRandomInteger(Mockito.anyInt(), Mockito.anyInt())).then(invocation -> {
                getRandomIntegerInvocations.add(invocation);
                return randomInt;
            });
            orangeGhost.doMove();

            try {
                utilMock.verify(Util::getRandomBoolean, Mockito.atLeastOnce());
            } catch (MockitoAssertionError e) {
                fail(e, context, r -> "Util.getRandomBoolean() was not called at least once");
            }
            try {
                utilMock.verify(() -> Util.getRandomInteger(Mockito.anyInt(), Mockito.anyInt()), Mockito.atLeastOnce());
            } catch (MockitoAssertionError e) {
                fail(e, context, r -> "Util.getRandomInteger(int, int) was not called at least once");
            }
            for (InvocationOnMock invocation : getRandomIntegerInvocations) {
                assertEquals(0, invocation.getArgument(0), context, r ->
                    "doMove() did not call Util.getRandomInteger(int, int) with the correct first argument");
                assertEquals(2627, invocation.getArgument(1), context, r ->
                    "doMove() did not call Util.getRandomInteger(int, int) with the correct second argument");
            }
        } catch (Exception e) {
            System.err.printf("OrangeGhost.doMove() threw an unexpected exception: %s%n", e);
        }

        Direction direction = turnLeft ? Direction.LEFT : Direction.RIGHT;
        assertEquals(direction, orangeGhost.getDirection(), context, r ->
            "Orange ghost does not face the correct direction when its front is blocked");
        assertEquals(startX + (!sidesBlocked && randomInt % 4 == 0 ? direction.getDx() : 0),
            orangeGhost.getX(),
            context,
            result -> "Orange ghost's x-coordinate is incorrect");
        assertEquals(startY, orangeGhost.getY(), context, result -> "Orange ghost's y-coordinate is incorrect");
    }
}
