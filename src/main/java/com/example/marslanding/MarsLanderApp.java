package com.example.marslanding;

import com.example.marslanding.view.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class MarsLanderApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ViewManager viewManager = new ViewManager();
        stage = viewManager.getStage();
        stage.show();
    }

    private static class Sprite extends Rectangle {
        boolean dead = false;

    }

    public static void main(String[] args) {
        launch();
    }
}