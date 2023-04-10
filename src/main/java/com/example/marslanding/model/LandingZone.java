package com.example.marslanding.model;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
/**
 * Represents a LandingZone.
 *
 * @author Olga Zimina
 * @version 1
 */
public class LandingZone {
    // The path of the image used for the landing zone
    private static final String IMAGE = "landing_area.png";
    // The coefficient used to calculate the landing line of the lander
    private static final double LANDING_LINE_COEFFICIENT = 0.6;
    // The ImageView of the landing zone image
    private final ImageView imageView;
    // The maximum x-coordinate limit of the landing zone
    private final double maxXLimit;
    // The minimum x-coordinate limit of the landing zone
    private final double minXLimit;
    // The maximum y-coordinate limit of the landing zone
    private final double maxYLimit;
    // The minimum y-coordinate limit of the landing zone
    private final double minYLimit;
    /**
     * Constructors a new LandingZone object.
     * @param maxXLimit The maximum x-coordinate limit of the landing zone, of type double
     * @param minXLimit The minimum x-coordinate limit of the landing zone, of type double
     * @param maxYLimit The maximum y-coordinate limit of the landing zone, of type double
     * @param minYLimit The minimum y-coordinate limit of the landing zone, of type double
     */
    public LandingZone(
            final double maxXLimit,
            final double minXLimit,
            final double maxYLimit,
            final double minYLimit
    ) {
        this.imageView = new ImageView(IMAGE);
        this.maxXLimit = maxXLimit;
        this.minXLimit = minXLimit;
        this.maxYLimit = maxYLimit;
        this.minYLimit = minYLimit;
    }

    /**
     * Returns the width of the landing zone image.
     * @return The width of the landing zone image, of type double
     */
    private double getWidth() {
        return imageView.getBoundsInLocal().getWidth();
    }

    /**
     * Sets the position of the landing zone image to a specified x- and y-coordinate.
     * @param xCoordinate The x-coordinate to set the image to, of type double
     * @param yCoordinate The y-coordinate to set the image to, of type double
     */
    private void setPosition(final double xCoordinate, final double yCoordinate) {
        imageView.setX(xCoordinate);
        imageView.setY(yCoordinate);
    }

    /**
     * Returns the ImageView of the landing zone image.
     * @return The ImageView of the landing zone image.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Sets a random position for the landing zone image within the limits of the landing zone.
     */
    public void setRandomPosition() {
        final double xCoordinate = generateRandomDoubleInRange(minXLimit, maxXLimit - getWidth());
        final double yCoordinate = generateRandomDoubleInRange(minYLimit, maxYLimit);

        setPosition(xCoordinate, yCoordinate);
    }
    /**
     *Sets the scale of the landing zone image on the x-axis.
     * @param value The value to set the scale to.
     */
    public void setScaleX(final double value) {
        imageView.setScaleX(value);
    }
    /**
     * Returns the bounds of the landing zone image in its parent coordinate space.
     * @return The bounds of the landing zone image.
     */
    public Bounds getBounds() {
        return imageView.getBoundsInParent();
    }

    /**
     * Calculates and returns the baseline position of the landing zone, where the lander should touch down.
     * @return The baseline position of the landing zone.
     */
    public double getBaseLine() {
        final Bounds bounds = getBounds();
        final double height = bounds.getHeight();
        return maxYLimit + height * LANDING_LINE_COEFFICIENT;
    }

    /**
     * Generates a random double within a specified range.
     * @param min The minimum value of the range, of type double
     * @param max The maximum value of the range, of type double
     * @return A random double within the specified range
     */
    private static double generateRandomDoubleInRange(final double min, final double max) {
        return (Math.random() * (max - min)) + min;
    }
    /**
     * Compares this LandingZone object to the given object.
     *
     * @param obj The object that is compared to this LandingZone object
     * @return True - if the given object is of type LandingZone with the same state as this object,
     * false - otherwise
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        LandingZone landingZone = (LandingZone) obj;
        return Double.compare(landingZone.maxXLimit, maxXLimit) == 0
                && Double.compare(landingZone.minXLimit, minXLimit) == 0
                && Double.compare(landingZone.maxYLimit, maxYLimit) == 0
                && Double.compare(landingZone.minYLimit, minYLimit) == 0;
    }
    /**
     * Returns a hash code for this LandingZone object.
     * @return A hash code value of type int for this LandingZone object
     */
    @Override
    public int hashCode() {
        final int prime = 17;
        final int additionValue = 37;
        final int shift = 32;
        int result = prime;
        long temp;
        result = additionValue * result + imageView.hashCode();
        temp = Double.doubleToLongBits(maxXLimit);
        result = additionValue * result + (int) (temp ^ (temp >>> shift));
        temp = Double.doubleToLongBits(minXLimit);
        result = additionValue * result + (int) (temp ^ (temp >>> shift));
        temp = Double.doubleToLongBits(maxYLimit);
        result = additionValue * result + (int) (temp ^ (temp >>> shift));
        temp = Double.doubleToLongBits(minYLimit);
        result = additionValue * result + (int) (temp ^ (temp >>> shift));
        return result;
    }
    /**
     * Returns a String representation of this LandingZone.
     * @return description as a String
     */
    @Override
    public String toString() {
        return "LandingZone{"
                + "imageView=" + imageView
                + ", maxXLimit=" + maxXLimit
                + ", minXLimit=" + minXLimit
                + ", maxYLimit=" + maxYLimit
                + ", minYLimit=" + minYLimit
                + '}';
    }
}
