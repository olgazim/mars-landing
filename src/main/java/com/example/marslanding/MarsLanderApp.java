package com.example.marslanding;

import com.example.marslanding.view.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * Represents a MarsLanderApp.
 * The class should only be used as a starting point for the program.
 * @author Olga Zimina
 * @version 1
 */
public class MarsLanderApp extends Application {
    /**
     * Creates and shows the main stage for the application.
     * @param stage The primary stage for the application.
     */
    @Override
    public void start(Stage stage) {
        ViewManager viewManager = new ViewManager();
        stage = viewManager.getStage();
        stage.setTitle("Mars Lander");
        stage.show();
    }
    /**
     * Launches the JavaFX application.
     * @param args the command line arguments passed to the application.
     */
    public static void main(final String[] args) {
        launch();
    }
}
