package com.example.marslanding.view;

import com.example.marslanding.model.MarsLanderSubScene;
import com.example.marslanding.model.MenuButton;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ViewManager {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 648;
    private static final double MENU_BUTTON_START_X = 100;
    private static final double MENU_BUTTON_START_Y = 200;
    private AnchorPane anchorPane;
    private Scene scene;
    private Stage stage;
    private List<MenuButton> menuButtonList;

    public ViewManager() {
        anchorPane = new AnchorPane();
        scene = new Scene(anchorPane, WIDTH, HEIGHT);
        stage = new Stage();
        menuButtonList = new ArrayList<>();
        stage.setScene(scene);
        createLogo();
        createMenuButtons();
        createBackground();
        MarsLanderSubScene subScene = new MarsLanderSubScene();
        subScene.setLayoutX(450);
        subScene.setLayoutY(180);
        anchorPane.getChildren().add(subScene);
    }

    public Stage getStage() {
        return stage;
    }

    private void createLogo() {
        ImageView astronaut = new ImageView("astronaut.png");
        astronaut.setFitHeight(120);
        astronaut.setFitWidth(100);
        astronaut.setLayoutX(900);
        astronaut.setLayoutY(230);
        astronaut.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                astronaut.setEffect(new DropShadow(10, Color.rgb(99, 57, 226)));
            }
        });
        astronaut.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                astronaut.setEffect(null);
            }
        });
        anchorPane.getChildren().add(astronaut);
    }

    private void createPlayButton() {
        MenuButton playBtn = new MenuButton("Play");
        addMenuButtons(playBtn);
    }

    private void createExitButton() {
        MenuButton exitBtn = new MenuButton("Exit");
        addMenuButtons(exitBtn);
    }
    private void createScoreButton() {
        MenuButton scoreBtn = new MenuButton("Score");
        addMenuButtons(scoreBtn);
    }

    private void createMenuButtons() {
        createPlayButton();
        createScoreButton();
        createExitButton();
    }

    private void addMenuButtons(MenuButton button) {
        button.setLayoutX(MENU_BUTTON_START_X);
        button.setLayoutY(MENU_BUTTON_START_Y + menuButtonList.size() * 100);
        menuButtonList.add(button);
        anchorPane.getChildren().add(button);

    }

//TODO: fix image path so we could put image to resources
    private void createBackground() {
        // get image file
        Image background = new Image("galaxy_background.jpg", 1024, 648, false, true);
        // create background image
        BackgroundImage backgroundImage = new BackgroundImage(background,BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, null);
        anchorPane.setBackground(new Background(backgroundImage));
    }

}
