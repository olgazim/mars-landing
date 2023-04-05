package com.example.marslanding.model;

import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class MarsLanderSubScene extends SubScene {
    private static final int HEIGHT = 300;
    private static final int WIDTH = 400;

    public MarsLanderSubScene() {
        super(new AnchorPane(), WIDTH, HEIGHT);
        AnchorPane rootPane = (AnchorPane) this.getRoot();
        BackgroundFill bgFill = new BackgroundFill(Color.web("#6C5B7B"), CornerRadii.EMPTY, Insets.EMPTY);
        rootPane.setBackground(new Background(bgFill));
    }
}
