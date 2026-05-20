package h02;

import h02.template.GameControllerTemplate;

/**
 * The {@link GameController} class is responsible for controlling the game.
 */
public class GameController extends GameControllerTemplate {
    /**
     * Creates a new {@link GameController} object and sets up the game.
     */
    public GameController() {
        setup();
    }

    /**
     * Checks if the player has won the game.
     */
    @Override
    public boolean checkWinCondition() {
        return pacman.getNumberOfCoins() == totalCoins;
    }
}
