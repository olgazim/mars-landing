/**
 * Represents a level in the game.
 *
 * @author Olga Zimina
 * @version 1.0
 */
public class Level {
    // The maximum number of attempts allowed for a level
    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    // The number of attempts made for the level
    private static int attempt = 0;
    // The planet to be landed on
    private Planet planet;
    // The spaceship used for the mission
    private SpaceShip spaceShip;
//    // The ship controllers mode
//    private Mode mode;
    // The number of the level
    private int number;
    // The name of the level (mission)
    private String name;
    // A boolean indicating whether the spaceship has crashed during landing attempts
    private boolean isCrashed;
    // A boolean indicating whether the spaceship has successfully landed on the planet
    private boolean isLanded;

    /**
     * Constructs an object of Level.
     */
    public Level() {

    }
}
