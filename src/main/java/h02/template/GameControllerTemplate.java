package h02.template;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import fopbot.ColorProfile;
import fopbot.Robot;
import fopbot.World;
import h02.BlueGhost;
import h02.OrangeGhost;
import h02.Pacman;
import h02.PinkGhost;
import h02.RedGhost;

/**
 * A {@link GameControllerTemplate} controls the game loop and the
 * {@link Robot}s and checks the win condition.
 */
@SuppressWarnings("UnstableApiUsage")
public abstract class GameControllerTemplate {
    /**
     * The {@link Timer} that controls the game loop.
     */
    private final Timer gameLoopTimer = new Timer();

    /**
     * The {@link GameInputHandler} that handles the input of the user.
     */
    private final GameInputHandler inputHandler = new GameInputHandler();

    /**
     * The total umber of coins in the map.
     */
    protected int totalCoins;

    /**
     * The {@link Robot}s that are controlled by the {@link GameControllerTemplate}.
     */
    protected final ArrayList<Robot> robots = new ArrayList<>();

    /**
     * The {@link Pacman} {@link Robot}.
     */
    protected Robot pacman;

    /**
     * The {@link BlueGhost} {@link Robot}.
     */
    protected Robot blue;

    /**
     * The {@link OrangeGhost} {@link Robot}.
     */
    protected Robot orange;

    /**
     * The {@link PinkGhost} {@link Robot}.
     */
    protected Robot pink;

    /**
     * The {@link RedGhost} {@link Robot}.
     */
    protected Robot red;

    private final Point ghostField = new Point(4, 4);

    /**
     * A {@link Map} that maps a {@link Robot} to the amount of ticks that have
     * passed since the last tick action.
     */
    private final Map<Robot, Integer> robotTicks = new HashMap<>();

    /**
     * The {@link TimerTask} that is executed every tick.
     */
    private final TimerTask gameLoopTask = new TimerTask() {
        @Override
        public void run() {
            for (final Robot robot : GameControllerTemplate.this.robots) {
                if (!(robot instanceof final TickBased tb)) {
                    continue;
                }
                if (!GameControllerTemplate.this.robotTicks.containsKey(robot)) {
                    GameControllerTemplate.this.robotTicks.put(robot, 0);
                }
                if (GameControllerTemplate.this.robotTicks.get(robot) < tb.getUpdateDelay()) {
                    GameControllerTemplate.this.robotTicks.put(robot,
                            GameControllerTemplate.this.robotTicks.get(robot) + 1);
                    continue;
                }
                GameControllerTemplate.this.robotTicks.put(robot, 0);
                // do tick action
                if (robot instanceof final Pacman r) {
                    r.handleKeyInput(
                            GameControllerTemplate.this.inputHandler.getDirection());
                } else if (robot instanceof final Ghost r) {
                    r.doMove();
                }
            }
            // check win condition
            if (checkWinCondition())
                stopGame(true);
            if (checkLoseCondition())
                stopGame(false);
        }
    };

    /**
     * Gets the {@link h02.Pacman} {@link Robot}.
     *
     * @return the {@link h02.Pacman} {@link Robot}
     */
    @SuppressWarnings("unused")
    public Robot getPacman() {
        return pacman;
    }

    /**
     * Gets the {@link BlueGhost} {@link Robot}.
     *
     * @return the {@link BlueGhost} {@link Robot}
     */
    @SuppressWarnings("unused")
    public Robot getBlue() {
        return blue;
    }

    /**
     * Gets the {@link OrangeGhost} {@link Robot}.
     *
     * @return the {@link OrangeGhost} {@link Robot}
     */
    @SuppressWarnings("unused")
    public Robot getOrange() {
        return orange;
    }

    /**
     * Gets the {@link PinkGhost} {@link Robot}.
     *
     * @return the {@link PinkGhost} {@link Robot}
     */
    @SuppressWarnings("unused")
    public Robot getPink() {
        return pink;
    }

    /**
     * Gets the {@link RedGhost} {@link Robot}.
     *
     * @return the {@link RedGhost} {@link Robot}
     */
    @SuppressWarnings("unused")
    public Robot getRed() {
        return red;
    }

    /**
     * Starts the game loop.
     */
    public void startGame() {
        System.out.println("Starting game...");
        this.gameLoopTimer.scheduleAtFixedRate(this.gameLoopTask, 0, 200);
    }

    /**
     * Stops the game loop.
     */
    public void stopGame(boolean won) {
        this.gameLoopTimer.cancel();
        endscreen(won ? Color.GREEN : Color.RED);
    }

    public void endscreen(Color color) {
        World.getGlobalWorld().getGuiPanel().setColorProfile(
                ColorProfile.DEFAULT.toBuilder()
                        .backgroundColorDark(Color.BLACK)
                        .backgroundColorLight(Color.BLACK)
                        .fieldColorDark(color)
                        .fieldColorLight(color)
                        .innerBorderColorLight(color)
                        .innerBorderColorDark(color)
                        .wallColorDark(Color.BLUE)
                        .wallColorLight(Color.BLUE)
                        .outerBorderColorDark(Color.BLUE)
                        .outerBorderColorLight(Color.BLUE)
                        .coinColorDark(color)
                        .coinColorLight(color)
                        .build());
        World.getGlobalWorld().getGuiPanel().updateGui();
    }

    /**
     * Sets up the game.
     */
    protected void setup() {
        setupWorld();
        setupTheme();
        setupRobots();
        totalCoins = 10;
        setupCoins(totalCoins);
        this.inputHandler.install();
    }

    public void setupTheme() {
        World.getGlobalWorld().getGuiPanel().setColorProfile(
                ColorProfile.DEFAULT.toBuilder()
                        .backgroundColorDark(Color.BLACK)
                        .backgroundColorLight(Color.BLACK)
                        .fieldColorDark(Color.BLACK)
                        .fieldColorLight(Color.BLACK)
                        .innerBorderColorLight(Color.BLACK)
                        .innerBorderColorDark(Color.BLACK)
                        .wallColorDark(Color.BLUE)
                        .wallColorLight(Color.BLUE)
                        .outerBorderColorDark(Color.BLUE)
                        .outerBorderColorLight(Color.BLUE)
                        .build());
    }

    /**
     * Initializes the {@link World} and adds the {@link Robot}s to it.
     */
    public void setupWorld() {
        World.setSize(9, 9);
        World.setDelay(0);
        World.setVisible(true);
        World.getGlobalWorld().setDrawTurnedOffRobots(false);

        World.placeVerticalWall(0, 3);
        World.placeVerticalWall(0, 4);
        World.placeVerticalWall(0, 5);

        World.placeVerticalWall(1, 2);
        World.placeVerticalWall(1, 4);
        World.placeVerticalWall(1, 6);

        World.placeVerticalWall(2, 2);
        World.placeVerticalWall(2, 3);
        World.placeVerticalWall(2, 5);
        World.placeVerticalWall(2, 6);

        World.placeVerticalWall(3, 4);
        World.placeVerticalWall(3, 7);

        World.placeVerticalWall(4, 4);
        World.placeVerticalWall(4, 1);

        World.placeVerticalWall(5, 2);
        World.placeVerticalWall(5, 3);
        World.placeVerticalWall(5, 5);
        World.placeVerticalWall(5, 6);

        World.placeVerticalWall(6, 2);
        World.placeVerticalWall(6, 4);
        World.placeVerticalWall(6, 6);

        World.placeVerticalWall(7, 3);
        World.placeVerticalWall(7, 4);
        World.placeVerticalWall(7, 5);

        World.placeHorizontalWall(1, 0);
        World.placeHorizontalWall(1, 1);
        World.placeHorizontalWall(1, 6);
        World.placeHorizontalWall(1, 7);

        World.placeHorizontalWall(2, 0);
        World.placeHorizontalWall(2, 7);

        World.placeHorizontalWall(3, 0);
        World.placeHorizontalWall(3, 2);
        World.placeHorizontalWall(3, 5);

        World.placeHorizontalWall(4, 1);
        World.placeHorizontalWall(4, 3);
        World.placeHorizontalWall(4, 6);

        World.placeHorizontalWall(5, 2);
        World.placeHorizontalWall(5, 5);
        World.placeHorizontalWall(5, 7);

        World.placeHorizontalWall(6, 0);
        World.placeHorizontalWall(6, 7);

        World.placeHorizontalWall(7, 0);
        World.placeHorizontalWall(7, 1);
        World.placeHorizontalWall(7, 6);
        World.placeHorizontalWall(7, 7);

        World.getGlobalWorld().setFieldColor(ghostField.x, ghostField.y, Color.YELLOW);
    }

    /**
     * Adds the {@link Robot}s to the {@link World}.
     */
    public void setupRobots() {
        this.robots.add(pacman = new Pacman(4, 3));
        this.robots.add(blue = new BlueGhost(ghostField.x, ghostField.y));
        this.robots.add(orange = new OrangeGhost(ghostField.x, ghostField.y));
        this.robots.add(pink = new PinkGhost(ghostField.x, ghostField.y));
        this.robots.add(red = new RedGhost(ghostField.x, ghostField.y, pacman));
    }

    public void setupCoins(int numberOfCoins) {
        if (numberOfCoins > World.getHeight() * World.getWidth() - 2) {
            throw new IllegalArgumentException("Too many coins for this world size.");
        }

        ArrayList<Point> Fields = new ArrayList<>();
        for (int y = 0; y < World.getHeight(); y++) {
            for (int x = 0; x < World.getWidth(); x++) {
                if (!((x == ghostField.x && y == ghostField.y) || x == pacman.getX() && y == pacman.getY()))
                    Fields.add(new Point(x, y));
            }
        }

        for (int i = 0; i < numberOfCoins; i++) {
            int randomIndex = (int) (Math.random() * Fields.size());
            Point spot = Fields.remove(randomIndex);
            World.putCoins(spot.x, spot.y, 1);
        }
    }

    /**
     * Checks the win condition.
     *
     * @return Returns true if the game is won.
     */
    public abstract boolean checkWinCondition();

    /**
     * Checks the loss condition.
     *
     * @return Returns true if the game is lost.
     */
    public boolean checkLoseCondition() {
        boolean gameDone = pacman.getX() == orange.getX() && pacman.getY() == orange.getY();

        if (pacman.getX() == blue.getX() && pacman.getY() == blue.getY()) {
            gameDone = true;
        }

        if (pacman.getX() == pink.getX() && pacman.getY() == pink.getY()) {
            gameDone = true;
        }

        if (pacman.getX() == red.getX() && pacman.getY() == red.getY()) {
            gameDone = true;
        }
        return gameDone;
    }
}
