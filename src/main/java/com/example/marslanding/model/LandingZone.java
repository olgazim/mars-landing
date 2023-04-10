package com.example.marslanding.model;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

public class LandingZone {
    private static final String image = "landing_area.png";
    private final ImageView imageView;
    private final double maxXLimit;
    private final double minXLimit;
    private final double maxYLimit;
    private final double minYLimit;
    private static final double LANDING_LINE_COEFFICIENT = 0.6;
    public LandingZone(
            final double maxXLimit,
            final double minXLimit,
            final double maxYLimit,
            final double minYLimit
    ) {
        this.imageView = new ImageView(image);
        this.maxXLimit = maxXLimit;
        this.minXLimit = minXLimit;
        this.maxYLimit = maxYLimit;
        this.minYLimit = minYLimit;
    }

    private static double generateRandomDoubleInRange(final double min, final double max) {
        return (Math.random() * (max - min)) + min;
    }

    private double getWidth() {
        return imageView.getBoundsInLocal().getWidth();
    }

    private void setPosition(final double xCoordinate, final double yCoordinate) {
        imageView.setX(xCoordinate);
        imageView.setY(yCoordinate);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setRandomPosition() {
        final double xCoordinate = generateRandomDoubleInRange(minXLimit, maxXLimit - getWidth());
        final double yCoordinate = generateRandomDoubleInRange(minYLimit, maxYLimit);

        setPosition(xCoordinate, yCoordinate);
    }

    public void setScaleX(final double value) {
        imageView.setScaleX(value);
    }

    public Bounds getBounds() {
        return imageView.getBoundsInParent();
    }

    public double getBaseLine() {
        final Bounds bounds = getBounds();
        final double height = bounds.getHeight();
        final double y = imageView.getLayoutY();
        return maxYLimit + height * LANDING_LINE_COEFFICIENT;
    }
}
