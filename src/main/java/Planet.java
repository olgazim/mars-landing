/**
 * Represents a planet.
 *
 * @author Olga Zimina
 * @version 1.0
 */
public class Planet {
    // The name of the planet
    private final String name;
    // The gravitation of the planet
    private final double gravitation;
    // A 2D array of doubles representing the surface coordinates of the planet
    private final double[][] surfaceCoordinates;
    // A  2D array of doubles representing the safe landing area of the planet
    private double[][] landingArea;
    // An array of doubles representing the starting coordinates of the spaceship on the planet
    private final double[] startCoordinates;

    /**
     * Constructs a new object of a Planet.
     * @param name a name of the planet of type String
     * @param gravitation the gravitation of the planet as double
     * @param surfaceCoordinates the surface coordinates of the planet as 2D array of doubles
     * @param landingArea the safe landing area coordinates of the planet as 2D array of doubles
     * @param startCoordinates the starting coordinates of the spaceship on the planet as an array of doubles
     */
    public Planet(final String name,
                  final double gravitation,
                  final double[][] surfaceCoordinates,
                  final double[][] landingArea,
                  final double[] startCoordinates) {
        this.name = name;
        this.gravitation = gravitation;
        this.surfaceCoordinates = surfaceCoordinates;
        this.landingArea = landingArea;
        this.startCoordinates = startCoordinates;
    }
    /**
     * Gets the name of the planet.
     * @return  the name of the planet as a String
     */
    public String getName() {
        return name;
    }
    /**
     * Gets the gravitation of the planet.
     * @return  the gravitation of the planet as a double
     */
    public double getGravitation() {
        return gravitation;
    }
    /**
     * Gets a 2D array of doubles representing the surface coordinates of the planet.
     * @return  a 2D array of doubles representing the surface coordinates of the planet
     */
    public double[][] getSurfaceCoordinates() {
        return surfaceCoordinates;
    }
    /**
     * Gets a 2D array of doubles representing the safe landing area of the planet.
     * @return  a 2D array of doubles representing the safe landing area of the planet
     */
    public double[][] getLandingArea() {
        return landingArea;
    }
    /**
     * Gets an array of doubles representing the starting coordinates of the spaceship on the planet.
     * @return  an array of doubles representing the starting coordinates of the spaceship on the planet
     */
    public double[] getStartCoordinates() {
        return startCoordinates;
    }
}
