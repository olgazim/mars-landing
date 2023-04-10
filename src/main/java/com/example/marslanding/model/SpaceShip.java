package com.example.marslanding.model;

import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents a spaceship.
 *
 * @author Olga Zimina
 * @version 1.0
 */
public class SpaceShip {
    // The constant string representing the image for the Falcon 9 spaceship
    private static final String FALCON9_IMAGE = "small_space_ship.png";
    // The constant string representing the image for the Falcon Heavy spaceship
    private static final String FALCON_HEAVY_IMAGE = "falcon_heavy.png";
    // The constant integer representing the size of the Falcon 9 spaceship
    private static final int FALCON9_SIZE = 50;
    // The constant integer representing the height of the Falcon Heavy spaceship
    private static final int FALCON_HEAVY_HEIGHT = 80;
    // The constant integer representing the height of the BFR spaceship
    private static final int BFR_HEIGHT = 65;
    // The X coordinate limit of the spaceship's movement
    private final double limitX;
    // The Y coordinate limit of the spaceship's movement
    private final double limitY;
    // The type of the spaceship, of type ShipType
    private final ShipType type;
    // The image view of the spaceship
    private final ImageView shipImage;
    // The height of the spaceship's image
    private final double shipImgHeight;
    // The width of the spaceship's image
    private final double shipImgWidth;

    /**
     * Constructs a SpaceShip object based on specified type.
     * @param type the type of the spaceship, of type ShipType
     * @param limitX the X coordinate limit of the spaceship's movement
     * @param limitY the Y coordinate limit of the spaceship's movement
     */
    public SpaceShip(final ShipType type,
                     final double limitX,
                     final double limitY) {
        // The current coordinates of the spaceship
        double[] currentCoordinates = new double[2];
        this.type = type;
        this.limitX = limitX;
        this.limitY = limitY;
        currentCoordinates[0] = limitX / 2;
        currentCoordinates[1] = limitY / 2;
        switch (type) {
            case FALCON_HEAVY -> {
                shipImage = new ImageView(FALCON_HEAVY_IMAGE);
                shipImgHeight = FALCON_HEAVY_HEIGHT;
                shipImgWidth = FALCON9_SIZE;
            }
            case BFR -> {
                shipImage = new ImageView(FALCON_HEAVY_IMAGE);
                shipImgHeight = BFR_HEIGHT;
                shipImgWidth = FALCON9_SIZE;
            }
            default -> {
                shipImage = new ImageView(FALCON9_IMAGE);
                shipImgHeight = FALCON9_SIZE;
                shipImgWidth = FALCON9_SIZE;
            }
        }
        setupImageView(shipImgHeight, shipImgWidth, currentCoordinates);
    }

    /**
     * Returns the type of the spaceship.
     * @return the type of the spaceship, of type ShipType
     */
    public ShipType getType() {
        return type;
    }

    /**
     * Returns the image view of the spaceship.
     * @return the image view of the spaceship
     */
    public ImageView getShipImage() {
        return shipImage;
    }

    /**
     * Returns the height of the spaceship image.
     * @return the height of the spaceship image
     */
    public double getShipImgHeight() {
        return shipImgHeight;
    }

    /**
     * Returns the width of the spaceship image.
     * @return the width of the spaceship image
     */
    public double getShipImgWidth() {
        return shipImgWidth;
    }
    /*
    Sets up the image view of the spaceship with the specified height, width and coordinates.
     */
    private void setupImageView(final double height, final double width, final double[] coordinates) {
        shipImage.setFitWidth(width);
        shipImage.setFitHeight(height);
        shipImage.setLayoutY(coordinates[1]);
        shipImage.setLayoutX(coordinates[0]);
    }
    /*
     Moves the ImageView object to the specified coordinates.
     */
    private void moveImgTo(final double[] coordinates) {
        shipImage.relocate(coordinates[0], coordinates[1]);
    }
    /**
     * Moves the ImageView object of the SpaceShip by a given deltaX and deltaY.
     * <p>
     *      If either deltaX or deltaY is 0, the object remains in its current position.
     * </p>
     * @param deltaX the change in x position, of type double
     * @param deltaY the change in y position, of type double
     */
    public void moveShipBy(final double deltaX, final double deltaY) {
        if (deltaX == 0 && deltaY == 0) {
            return;
        }

        double newX = shipImage.getLayoutX() + deltaX;
        double newY = shipImage.getLayoutY() + deltaY;
        boolean isXValid = newX >= 0 && getShipImgWidth() <= limitX;
        boolean isYValid = newY >= 0 && getShipImgHeight() <= limitY;
        if (isXValid && isYValid) {
            double[] newCoordinates = {newX, newY};
            moveImgTo(newCoordinates);
        }
    }
    /**
     * Finds the bottom line of the ImageView object of the SpaceShip.
     * @return a double representing the y coordinate of the bottom line of the ImageView
     */
    public double findBottomLine() {
        return shipImage.getLayoutY() + shipImgHeight;
    }
    /**
     * Compares this SpaceShip object to the given object.
     *
     * @param obj The object that is compared to this SpaceShip object
     * @return True - if the given object is of type SpaceShip with the same state as this object,
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
        SpaceShip spaceShip = (SpaceShip) obj;
        return Double.compare(spaceShip.limitX, limitX) == 0
                && Double.compare(spaceShip.limitY, limitY) == 0
                && type == spaceShip.type
                && Objects.equals(shipImage, spaceShip.shipImage)
                && Double.compare(spaceShip.shipImgHeight, shipImgHeight) == 0
                && Double.compare(spaceShip.shipImgWidth, shipImgWidth) == 0;
    }
    /**
     * Returns a hash code for this SpaceShip object.
     * @return A hash code value of type int for this SpaceShip object
     */
    @Override
    public int hashCode() {
        final int prime = 17;
        final int additionValue = 37;
        final int shift = 32;
        int result = prime;
        long temp = Double.doubleToLongBits(limitX);
        result = additionValue * result + (int) (temp ^ (temp >>> shift));
        temp = Double.doubleToLongBits(limitY);
        result = additionValue * result + (int) (temp ^ (temp >>> shift));
        if (type != null) {
            result = additionValue * result + type.hashCode();
        }
        if (shipImage != null) {
            result = additionValue * result + shipImage.hashCode();
        }
        temp = Double.doubleToLongBits(getShipImgHeight());
        result = additionValue * result + (int) (temp ^ (temp >>> shift));
        temp = Double.doubleToLongBits(getShipImgWidth());
        result = additionValue * result + (int) (temp ^ (temp >>> shift));
        return result;
    }
    /**
     * Returns a string representation of the SpaceShip object.
     *
     * @return A string representation of the SpaceShip object.
     */
    @Override
    public String toString() {
        return "SpaceShip{"
                + "limitX=" + limitX
                + ", limitY=" + limitY
                + ", type=" + type
                + ", shipImage=" + shipImage
                + ", shipImgHeight=" + shipImgHeight
                + ", shipImgWidth=" + shipImgWidth
                + '}';
    }
}
