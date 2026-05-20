package h02.template;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import fopbot.Direction;
import fopbot.World;

/**
 * A {@link GameInputHandler} handles the input of the user.
 */
public class GameInputHandler {
    /**
     * The {@link fopbot.Direction} to turn to. If {@code -1} the robot should not
     * turn.
     */
    private final AtomicInteger direction = new AtomicInteger(-1);

    /**
     * Parses the inputs to a direction.
     *
     * @param keysPressed the keys pressed
     * @return the direction or null if no direction is pressed
     */
    public static Direction getDirectionFromKeysPressed(final Set<Integer> keysPressed) {
        final Map<Direction, List<Integer>> directionKeys = Map.of(
                Direction.UP, List.of(KeyEvent.VK_UP, KeyEvent.VK_W),
                Direction.LEFT, List.of(KeyEvent.VK_LEFT, KeyEvent.VK_A),
                Direction.DOWN, List.of(KeyEvent.VK_DOWN, KeyEvent.VK_S),
                Direction.RIGHT, List.of(KeyEvent.VK_RIGHT, KeyEvent.VK_D));
        final Set<Direction> pressedDirections = new HashSet<>();
        for (final Direction direction : directionKeys.keySet()) {
            for (final Integer key : directionKeys.get(direction)) {
                if (keysPressed.contains(key)) {
                    pressedDirections.add(direction);
                }
            }
        }
        if (pressedDirections.size() == 1) {
            return pressedDirections.iterator().next();
        } else {
            return null;
        }
    }

    /**
     * Installs the {@link GameInputHandler} to the {@link World}.
     */
    public void install() {
        World.getGlobalWorld().getInputHandler().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(final KeyEvent e) {
                updateKeysPressed();
            }

            @Override
            public void keyPressed(final KeyEvent e) {
                updateKeysPressed();
            }

            @Override
            public void keyReleased(final KeyEvent e) {
                updateKeysPressed();
            }
        });
    }

    /**
     * Updates the {@link #direction} based on the pressed keys.
     */
    protected void updateKeysPressed() {
        this.direction.set(
                Optional.ofNullable(
                        getDirectionFromKeysPressed(World.getGlobalWorld().getInputHandler().getKeysPressed()))
                        .map(Enum::ordinal)
                        .orElse(-1));
    }

    /**
     * Returns the current {@link fopbot.Direction} to turn to. If {@code -1} the
     * robot should not turn.
     *
     * @return the current {@link fopbot.Direction} to turn to. If {@code -1} the
     *         robot should not turn.
     */
    public int getDirection() {
        return this.direction.get();
    }
}
