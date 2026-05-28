package h02;

import fopbot.Direction;
import org.sourcegrade.jagr.api.rubric.*;
import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;

public class H02_RubricProviderPrivate implements RubricProvider {

    private static final Criterion H2_1 = Criterion.builder()
            .shortDescription("H2.1 | Steuerung von Pacman")
            .maxPoints(4)
            .addChildCriteria(
                criterion("Pacman kann sich in eine der möglichen Richtungen bewegen.",
                    JUnitTestRef.or(
                        JUnitTestRef.ofMethod(() -> PacmanTest.class.getDeclaredMethod("testMovementUp")),
                        JUnitTestRef.ofMethod(() -> PacmanTest.class.getDeclaredMethod("testMovementRight")),
                        JUnitTestRef.ofMethod(() -> PacmanTest.class.getDeclaredMethod("testMovementDown")),
                        JUnitTestRef.ofMethod(() -> PacmanTest.class.getDeclaredMethod("testMovementLeft"))
                    )),
                criterion("Pacman kann sich in alle möglichen Richtungen bewegen.",
                    JUnitTestRef.and(
                        JUnitTestRef.ofMethod(() -> PacmanTest.class.getDeclaredMethod("testMovementUp")),
                        JUnitTestRef.ofMethod(() -> PacmanTest.class.getDeclaredMethod("testMovementRight")),
                        JUnitTestRef.ofMethod(() -> PacmanTest.class.getDeclaredMethod("testMovementDown")),
                        JUnitTestRef.ofMethod(() -> PacmanTest.class.getDeclaredMethod("testMovementLeft"))
                    )),
                criterion("Pacman kann sich nicht durch Wände bewegen.",
                    JUnitTestRef.ofMethod(() -> PacmanTest.class.getDeclaredMethod("testMovementWithWalls", Direction.class))),
                criterion("Pacman sammelt Münzen auf, wenn er sich auf ein Feld mit einer Münze bewegt.",
                    JUnitTestRef.ofMethod(() -> PacmanTest.class.getDeclaredMethod("testMovementWithCoins", Direction.class))))
            .build();

    private static final Criterion H2_2_1 = Criterion.builder()
            .shortDescription("H2.2.1 | Blaues Gespenst")
            .maxPoints(2)
            .addChildCriteria(
                criterion("Das blaue Gespenst führt die korrekten Drehungen aus.",
                    JUnitTestRef.ofMethod(() -> BlueGhostTest.class.getDeclaredMethod("testTurns"))),
                criterion("Das blaue Gespenst bewegt sich nach den Drehungen ein Feld nach vorne.",
                    JUnitTestRef.ofMethod(() -> BlueGhostTest.class.getDeclaredMethod("testMove"))))
            .build();

    private static final Criterion H2_2_2 = Criterion.builder()
        .shortDescription("H2.2.2 | Rotes Gespenst")
        .maxPoints(3)
        .addChildCriteria(
            criterion("Das rote Gespenst dreht sich in die Richtung in der sich Pacman befindet.",
                JUnitTestRef.ofMethod(() -> RedGhostTest.class.getDeclaredMethod("testTurnToPacman", Direction.class))),
            criterion("Das rote Gespenst dreht weiter nach links, bis es keine Wand mehr vor sich hat.",
                JUnitTestRef.ofMethod(() -> RedGhostTest.class.getDeclaredMethod("testTurnLeft"))),
            criterion("Das rote Gespenst bewegt sich ein Feld nach vorne.",
                JUnitTestRef.ofMethod(() -> RedGhostTest.class.getDeclaredMethod("testMove"))))
        .build();

    private static final Criterion H2_2_3 = Criterion.builder()
        .shortDescription("H2.2.3 | Oranges Gespenst")
        .maxPoints(4)
        .addChildCriteria(
            criterion("Das orange Gespenst läuft nach vorne, wenn keine Wand vor ihm ist.",
                JUnitTestRef.ofMethod(() -> OrangeGhostTest.class.getDeclaredMethod("testMoveUnblocked"))),
            criterion("Das orange Gespenst dreht sich nach links bzw. rechts, wenn eine Wand vor ihm ist.",
                JUnitTestRef.ofMethod(() -> OrangeGhostTest.class.getDeclaredMethod("testTurnBlocked", boolean.class))),
            criterion("Das orange Gespenst bewegt sich nicht, wenn nach der Drehung der Weg blockiert ist.",
                JUnitTestRef.ofMethod(() -> OrangeGhostTest.class.getDeclaredMethod("testMoveBlockedSides", boolean.class, int.class))),
            criterion("Das orange Gespenst bewegt sich auf das korrekte Feld, wenn nach der Drehung der Weg nicht blockiert ist.",
                JUnitTestRef.ofMethod(() -> OrangeGhostTest.class.getDeclaredMethod("testMoveBlockedFront", boolean.class, int.class))))
        .build();

    private static final Criterion H2_2_4 = Criterion.builder()
            .shortDescription("H2.2.4 | Pinkes Gespenst")
            .maxPoints(3)
            .addChildCriteria(
                criterion("Das pinke Gespenst wählt einen zufälligen Weg aus den freien Wegen.",
                    JUnitTestRef.ofMethod(() -> PinkGhostTest.class.getDeclaredMethod("testPicksRandomFreePath"))),
                criterion("Das pinke Gespenst dreht sich zu dem gewählten freien Weg.",
                    JUnitTestRef.ofMethod(() -> PinkGhostTest.class.getDeclaredMethod("testTurnToFreePath"))),
                criterion("Das pinke Gespenst bewegt sich ein Feld nach vorne.",
                    JUnitTestRef.ofMethod(() -> PinkGhostTest.class.getDeclaredMethod("testMove"))))
            .build();

    private static final Criterion H2_2 = Criterion.builder()
            .shortDescription("H2.2 | Die Gespenster kommen")
            .addChildCriteria(H2_2_1, H2_2_2, H2_2_3, H2_2_4)
            .build();

    public static final Rubric RUBRIC = Rubric.builder()
            .title("H02 | Pacman")
            .addChildCriteria(H2_1, H2_2)
            .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
