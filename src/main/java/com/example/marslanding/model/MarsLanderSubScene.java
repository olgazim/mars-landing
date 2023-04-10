package com.example.marslanding.model;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;
/**
 * Represents a MarsLanderSubScene.
 *
 * @author Olga Zimina
 * @version 1.0
 */
public class MarsLanderSubScene extends SubScene {
    // /The constant integer representing the height of the scene
    private static final int HEIGHT = 300;
    // /The constant integer representing the width of the scene
    private static final int WIDTH = 400;
    // The constant integer representing the x-coordinate of the scene's position
    private static final int X_COORDINATE = 1024;
    // The constant integer representing the y-coordinate of the scene's position
    private static final int Y_COORDINATE = 180;
    // The constant integer representing the delta value for moving sub scene
    private static final int TRANSITION_DELTA = -500;
    // The constant double representing the duration of the transition animation
    private static final double TRANSITION_DURATION = 0.5;
    // The boolean indicating whether the sub scene is currently presented
    private boolean isPresented = false;

    /**
     * Constructs MarsLanderSubScene object.
     */
    public MarsLanderSubScene() {
        super(new AnchorPane(), WIDTH, HEIGHT);
        AnchorPane rootPane = (AnchorPane) this.getRoot();
        BackgroundFill bgFill = new BackgroundFill(Color.web("#B7DEF1"), CornerRadii.EMPTY, Insets.EMPTY);
        rootPane.setBackground(new Background(bgFill));
        setLayoutX(X_COORDINATE);
        setLayoutY(Y_COORDINATE);
    }

    /**
     * Animates the sub scene to move horizontally by changing its position from its current
     * X-coordinate to a new X-coordinate.
     */
    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(TRANSITION_DURATION));
        transition.setNode(this);
        if (!isPresented) {
//            how to change position
            transition.setToX(TRANSITION_DELTA);
            isPresented = true;
        } else {
            transition.setToX(0);
            isPresented = false;
        }
        transition.play();
    }
    /**
     * Compares this MarsLanderSubScene object to the given object.
     *
     * @param obj The object that is compared to this MarsLanderSubScene object
     * @return True - if the given object is of type MarsLanderSubScene with the same state as this object,
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
        MarsLanderSubScene subScene = (MarsLanderSubScene) obj;
        return isPresented == subScene.isPresented;
    }
    /**
     * Returns a hash code for this MarsLanderSubScene object.
     * @return A hash code value of type int for this MarsLanderSubScene object
     */
    @Override
    public int hashCode() {
        final int prime = 17;
        final int additionValue = 37;
        int result = prime;
        if (isPresented) {
            result = additionValue * result + 1;
        }
        return result;
    }
    /**
     * Returns a string representation of the MarsLanderSubScene object.
     *
     * @return A string representation of the MarsLanderSubScene object.
     */
    @Override
    public String toString() {
        return "MarsLanderSubScene{"
                + "isPresented=" + isPresented
                + '}';
    }
}
