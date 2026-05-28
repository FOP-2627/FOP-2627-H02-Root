package h02;

import fopbot.Coin;
import fopbot.Direction;
import fopbot.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class PacmanTest {

    private final int startX = 2;
    private final int startY = 2;
    private Pacman pacman;
    private Context.Builder<?> baseContext;

    @BeforeEach
    public void setup() {
        World.setSize(5, 5);
        World.setDelay(0);
        pacman = new Pacman(startX, startY);
        baseContext = contextBuilder()
            .add("world height", 5)
            .add("world width", 5)
            .add("pacman", pacman);
    }

    @Test
    public void testMovementUp() {
        testBasicMovement(Direction.UP);
    }

    @Test
    public void testMovementRight() {
        testBasicMovement(Direction.RIGHT);
    }

    @Test
    public void testMovementDown() {
        testBasicMovement(Direction.DOWN);
    }

    @Test
    public void testMovementLeft() {
        testBasicMovement(Direction.LEFT);
    }

    private void testBasicMovement(Direction direction) {
        Context context = baseContext
            .add("k", direction.ordinal())
            .build();
        call(() -> pacman.handleKeyInput(direction.ordinal()), context, result -> "An exception occurred while invoking handleInputKey");

        assertEquals(direction, pacman.getDirection(), context, result ->
            "Pacman is not facing towards the expected direction");
        assertEquals(startX + direction.getDx(), pacman.getX(), context, result ->
            "Pacman's x-coordinate is incorrect");
        assertEquals(startY + direction.getDy(), pacman.getY(), context, result ->
            "Pacman's y-coordinate is incorrect");
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testMovementWithWalls(Direction direction) {
        World.placeHorizontalWall(startX, startY - 1);
        World.placeHorizontalWall(startX, startY);
        World.placeVerticalWall(startX - 1, startY);
        World.placeVerticalWall(startX, startY);
        Context context = baseContext
            .add("k", direction.ordinal())
            .build();
        call(() -> pacman.handleKeyInput(direction.ordinal()), context, result -> "An exception occurred while invoking handleInputKey");

        assertEquals(startX, pacman.getX(), context, result -> "Pacman's x-coordinate is incorrect");
        assertEquals(startY, pacman.getY(), context, result -> "Pacman's y-coordinate is incorrect");
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testMovementWithCoins(Direction direction) {
        World.putCoins(startX, startY + 1, 1);
        World.putCoins(startX + 1, startY, 1);
        World.putCoins(startX, startY - 1, 1);
        World.putCoins(startX - 1, startY, 1);
        Context context = baseContext
            .add("k", direction.ordinal())
            .build();
        call(() -> pacman.handleKeyInput(direction.ordinal()), context, result -> "An exception occurred while invoking handleInputKey");

        assertEquals(1, pacman.getNumberOfCoins(), context, result ->
            "Pacman did not pick up the expected number of coins");
        boolean pickedUpCorrectCoin = World.getGlobalWorld()
            .getField(startX + direction.getDx(), startY + direction.getDy())
            .getEntities()
            .stream()
            .noneMatch(fieldEntity -> fieldEntity instanceof Coin);
        assertTrue(pickedUpCorrectCoin, context, result ->
            "Pacman did not pick up the coin at (%d, %d)".formatted(startX + direction.getDx(), startY + direction.getDy()));
    }
}
