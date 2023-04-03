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
        // >>>>
        double[][] currentCoordinates = spaceShip.getCurrentCoordinates();
        double[][] newCoordinates = new double[1][2];

        double currentX = currentCoordinates[0][0];
        double currentY = currentCoordinates[0][1];

        double speed = spaceShip.getCurrentSpeed();
        double direction = spaceShip.getCurrentDirection();
        double acceleration = spaceShip.getAcceleration();
        double time = 1; //time interval of 1 second

        // Calculate the new position based on the current speed and direction
        double deltaX = speed * Math.cos(Math.toRadians(direction)) * time;
        double deltaY = speed * Math.sin(Math.toRadians(direction)) * time;

        // Update the current speed based on the acceleration
        speed += acceleration * time;

        // Update the current direction based on any external forces (e.g. gravity)
        // You will need to calculate this based on the specific planet the spaceship is on
        // Here's an example of how you could calculate the gravitational force:
        double gravitationalForce = planet.getGravity() * spaceShip.getMass();
        double gravitationalAcceleration = gravitationalForce / spaceShip.getMass();
        double gravitationalDirection = planet.getGravityDirection(spaceShip.getCurrentCoordinates());
        double gravitationalDeltaX = gravitationalAcceleration * Math.cos(Math.toRadians(gravitationalDirection)) * time;
        double gravitationalDeltaY = gravitationalAcceleration * Math.sin(Math.toRadians(gravitationalDirection)) * time;

        // Update the new position with the gravitational forces
        deltaX += gravitationalDeltaX;
        deltaY += gravitationalDeltaY;

        // Update the new coordinates
        newCoordinates[0][0] = currentX + deltaX;
        newCoordinates[0][1] = currentY + deltaY;

        // Return the new coordinates
        return newCoordinates;
    }
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
