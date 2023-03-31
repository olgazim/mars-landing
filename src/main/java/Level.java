import java.util.Arrays;
/**
 * Represents a level in the game.
 *
 * @author Olga Zimina, Shuyi Liu
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

    public Level(Planet planet, SpaceShip spaceShip, int number, String name) {
        this.planet = planet;
        this.spaceShip = spaceShip;
        this.number = number;
        this.name = name;
        this.isCrashed = false;
        this.isLanded = false;
    }

    public double[][] calculateNewShipCoordinate(){
    }

    public void updateShipFuel(double fuelAmount){
        fuelAmount -= 20;
    }

    //check if current coordinate is same as landing coordinate
    public void checkIfLanded(){
        double[] currentCoordinates = spaceShip.getCurrentCoordinates();
        double[][] landingCoordinates = planet.getLandingArea();
        for (double[] subArray : landingCoordinates) {
            if (Arrays.equals(subArray, currentCoordinates)) {
                isLanded = true;
            }
        }
    }

    //check if current coordinate is same as surface coordinate
    public void checkIfCrashed(){
        double[] currentCoordinates = spaceShip.getCurrentCoordinates();
        double[][] crashingCoordinates = planet.getSurfaceCoordinates();
        for (double[] subArray : crashingCoordinates) {
            if (Arrays.equals(subArray, currentCoordinates)) {
                isCrashed = true;
            }
        }
    }

    // check if spaceship crashed or landed
    public boolean checkIfLevelCompleted(){
        return isCrashed | isLanded;
    }

    // create a new level instance
    public void restartLevel(Level level, Planet planet, SpaceShip spaceShip, int number, String name) {
        level = new Level(planet, spaceShip, number, name);
    }

    // After each level, you earn 100*level coins
    public void updateCoinsBalance(int coinBalance) {
        int currentCoin = coinBalance;
        // int currentCoin = spaceship.getCoin()
        currentCoin += number*100;
        spaceShip.setCoins(currentCoin);
    }
}
