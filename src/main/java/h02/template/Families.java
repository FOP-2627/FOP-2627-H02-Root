package h02.template;

import java.awt.Color;

import fopbot.RobotFamily;
import fopbot.SvgBasedRobotFamily;

public class Families {
    public static final RobotFamily GHOST_ORANGE = new SvgBasedRobotFamily(
            "GHOST_ORANGE",
            "/robots/ghost_orange.svg",
            "/robots/ghost_orange.svg",
            Color.ORANGE);
    public static final RobotFamily GHOST_BLUE = new SvgBasedRobotFamily(
            "GHOST_BLUE",
            "/robots/ghost_blue.svg",
            "/robots/ghost_blue.svg",
            Color.BLUE);
    public static final RobotFamily GHOST_RED = new SvgBasedRobotFamily(
            "GHOST_RED",
            "/robots/ghost_red.svg",
            "/robots/ghost_red.svg",
            Color.RED);
    public static final RobotFamily GHOST_PINK = new SvgBasedRobotFamily(
            "GHOST_PINK",
            "/robots/ghost_pink.svg",
            "/robots/ghost_pink.svg",
            Color.PINK);
    public static final RobotFamily PACMAN = new SvgBasedRobotFamily(
            "PACMAN",
            "/robots/pacman.svg",
            "/robots/pacman.svg",
            Color.YELLOW,
            270,
            270);
}
