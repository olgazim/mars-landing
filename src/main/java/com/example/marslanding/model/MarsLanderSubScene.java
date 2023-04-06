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

public class MarsLanderSubScene extends SubScene {
    private static final int HEIGHT = 300;
    private static final int WIDTH = 400;
    private boolean isPresented = false;

    public MarsLanderSubScene() {
        super(new AnchorPane(), WIDTH, HEIGHT);
        AnchorPane rootPane = (AnchorPane) this.getRoot();
        BackgroundFill bgFill = new BackgroundFill(Color.web("#6C5B7B"), CornerRadii.EMPTY, Insets.EMPTY);
        rootPane.setBackground(new Background(bgFill));
        setLayoutX(1024);
        setLayoutY(180);
    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.5));
        transition.setNode(this);
        if(!isPresented) {
//            how to change position
            transition.setToX(-550);
            isPresented = true;
        } else {
            transition.setToX(0);
            isPresented = false;
        }
        transition.play();
    }
}
