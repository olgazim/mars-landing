/**
 * Represents a spaceship.
 *
 * @author Olga Zimina
 * @version 1.0
 */
public class SpaceShip {
    // The mass of the Falcon 9 spaceship in kilograms
    public static final int FALCON9_MASS = 152;
    // The mass of the Falcon Heavy spaceship in kilograms
    public static final int FALCON_HEAVY_MASS = 170;
    // The mass of the Big Falcon Rocket (BFR) spaceship in kilograms
    public static final int BFR_MASS = 200;
    // The amount of fuel in the tank of the Falcon 9 spaceship
    public static final double FALCON9_FUEL_AMOUNT = 70;
    // The amount of fuel in the tank of the Falcon Heavy spaceship
    public static final double FALCON_FUEL_AMOUNT = 90;
    // The amount of fuel in the tank of the BFR spaceship
    public static final double BFR_FUEL_AMOUNT = 130;
    // The mass of the spaceship in kilograms
    private int mass;
    // The amount of fuel in the spaceship's tank
    private double fuelAmount;
    // The type of the spaceship
    private ShipType type;
    // The current burn rate of the spaceship's engines
    private double currentBurnRate;
    // The current coordinates of the spaceship
    private double[] currentCoordinates;
    // The current velocity of the spaceship
    private double velocity;
    // The number of coins collected by the spaceship
    private int coins;
    // The maximum velocity at which the spaceship can land safely
    private double maxLandingVelocity;
    // The width of the spaceship in meters
    private int width;

    /**
     * Constructs a SpaceShip object based on specified type.
     * @param type the type of the spaceship, of type ShipType
     */
    public SpaceShip(final ShipType type) {
        switch (type) {
            case FALCON_HEAVY -> {
                mass = FALCON_HEAVY_MASS;
                fuelAmount = FALCON_FUEL_AMOUNT;
            }
            case BFR -> {
                mass = BFR_MASS;
                fuelAmount = BFR_FUEL_AMOUNT;
            }
            default -> {
                mass = FALCON9_MASS;
                fuelAmount = FALCON9_FUEL_AMOUNT;
            }
        }
    }

    /**
     * Gets the fuel amount of the spaceship.
     * @return the fuel amount of the spaceship as double
     */
    public double getFuelAmount() {
        return fuelAmount;
    }

    /**
     * Gets the current coordinates of the spaceship.
     * @return the current coordinates of the spaceship as array of doubles,
     * where first element represents x-coordinate and second element represents y-coordinate
     */
    public double[] getCurrentCoordinates() {
        return currentCoordinates;
    }

    /**
     * Gets the number of coins collected by the spaceship.
     * @return the number of coins collected by the spaceship as int
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Sets the fuel amount of the spaceship to the specified value.
     * @param fuelAmount the new fuel amount of the spaceship of type double
     */
    public void setFuelAmount(double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    /**
     * Sets the current coordinates of the spaceship.
     * @param currentCoordinates the new current coordinates of the spaceship as array of doubles,
     * where first element represents x-coordinate and second element represents y-coordinate
     */
    public void setCurrentCoordinates(final double[] currentCoordinates) {
        this.currentCoordinates = currentCoordinates;
    }

    /**
     * Sets the number of coins collected by the spaceship.
     * @param coins the new number of coins collected by the spaceship of type int
     */
    public void setCoins(final int coins) {
        this.coins = coins;
    }

    /**
     * Calculates the velocity of the spaceship based on the specified gravitational force of the planet.
     * @param gravitation the gravitational force of the planet of type double
     */
    public void calculateVelocity(final double gravitation) {
        velocity = Math.sqrt((mass + fuelAmount) * gravitation);
    }

    //TODO: toString, equals, hashCode
}
