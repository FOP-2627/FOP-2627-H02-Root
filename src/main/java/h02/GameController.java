package h02;

import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

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
        // GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // ge.getScreenDevices()[1].setFullScreenWindow(World.getGlobalWorld().getGuiFrame());
    }

    public void openOnSpecificMonitor(int monitorIndex) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();

        if (monitorIndex >= 0 && monitorIndex < devices.length) {
            GraphicsDevice device = devices[monitorIndex];
            Frame frame = new Frame(device.getDefaultConfiguration());
            frame.setSize(800, 600); // Set your desired window size
            frame.setVisible(true);
        } else {
            System.out.println("Invalid monitor index");
        }
    }

    /**
     * Checks if the player has won the game.
     */
    @Override
    public boolean checkWinCondition() {
        return pacman.getNumberOfCoins() == totalCoins;
    }
}
