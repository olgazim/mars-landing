package com.example.marslanding.model;

import javafx.scene.image.ImageView;

/**
 * Represents a spaceship.
 *
 * @author Olga Zimina
 * @version 1.0
 */
public class SpaceShip {
    public static final String FALCON9_IMAGE = "small_space_ship.png";
    public static final String BFR_IMAGE = "small_space_ship.png";
    public static final String FALCON_HEAVY_IMAGE = "small_space_ship.png";
    public static final int FALCON9_SIZE = 50;
    private final double limitX, limitY;
    // The current coordinates of the spaceship
    private double[] currentCoordinates = new double[2];
    private final ShipType type;
    private final ImageView shipImage;
    private final double shipImgHeight;
    private final double shipImgWidth;

    /**
     * Constructs a SpaceShip object based on specified type.
     * @param type the type of the spaceship, of type ShipType
     */
    public SpaceShip(final ShipType type,
                     final double limitX,
                     final double limitY) {
        this.type = type;
        this.limitX = limitX;
        this.limitY = limitY;
        currentCoordinates[0] = limitX / 2;
        currentCoordinates[1] = limitY / 2;
        switch (type) {
            case FALCON_HEAVY -> {
                shipImage = new ImageView(FALCON9_IMAGE);
                shipImgHeight = FALCON9_SIZE;
                shipImgWidth = FALCON9_SIZE;
                break;
            }
            case BFR -> {
                shipImage = new ImageView(FALCON9_IMAGE);
                shipImgHeight = FALCON9_SIZE;
                shipImgWidth = FALCON9_SIZE;
                break;
            }
            default -> {
                shipImage = new ImageView(FALCON9_IMAGE);
                shipImgHeight = FALCON9_SIZE;
                shipImgWidth = FALCON9_SIZE;
            }
        }
        setupImageView(shipImgHeight, shipImgWidth, currentCoordinates);
    }

    public ShipType getType() {
        return type;
    }

    public ImageView getShipImage() {
        return shipImage;
    }

    public double getShipImgHeight() {
        return shipImgHeight;
    }

    public double getShipImgWidth() {
        return shipImgWidth;
    }
    private void setupImageView(final double height, final double width, final double[] currentCoordinates) {
        shipImage.setFitWidth(width);
        shipImage.setFitHeight(height);
        shipImage.setLayoutY(currentCoordinates[1]);
        shipImage.setLayoutX(currentCoordinates[0]);
    }

    private void moveImgTo(final double[] coordinates) {
        shipImage.relocate(coordinates[0], coordinates[1]);
    }

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

    public double findBottomLine() {
        return shipImage.getLayoutY() + shipImgHeight;
    }
}
