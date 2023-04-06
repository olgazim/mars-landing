package com.example.marslanding.view;

import com.example.marslanding.model.MarsLanderSubScene;
import com.example.marslanding.model.MenuButton;
import javafx.event.ActionEvent;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ViewManager {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 648;
    private static final int MENU_BUTTON_START_X = 100;
    private static final int MENU_BUTTON_START_Y = 200;
    private static final int LOGO_HEIGHT = 120;
    private static final int LOGO_WIDTH = 100;
    private static final int LOGO_X_POSITION = 900;
    private static final int LOGO_Y_POSITION = 230;

    private AnchorPane anchorPane;
    private Scene scene;
    private Stage stage;
    private List<MenuButton> menuButtonList;
    private MarsLanderSubScene scoreSubScene;

    public ViewManager() {
        anchorPane = new AnchorPane();
        scene = new Scene(anchorPane, WIDTH, HEIGHT);
        stage = new Stage();
        menuButtonList = new ArrayList<>();
        stage.setScene(scene);
        createLogo();
        createMenuButtons();
        createBackground();
        createScoreSubScenes();
    }

    public Stage getStage() {
        return stage;
    }

    private void createLogo() {
        ImageView astronaut = new ImageView("astronaut.png");
        astronaut.setFitHeight(LOGO_HEIGHT);
        astronaut.setFitWidth(LOGO_WIDTH);
        astronaut.setLayoutX(LOGO_X_POSITION);
        astronaut.setLayoutY(LOGO_Y_POSITION);
        astronaut.setEffect(new DropShadow(10, Color.rgb(72, 83, 95)));
        astronaut.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                astronaut.setEffect(new DropShadow(10, Color.rgb(6, 18, 33)));
            }
        });
        astronaut.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                 astronaut.setEffect(new DropShadow(10, Color.rgb(72, 83, 95)));
            }
        });
        anchorPane.getChildren().add(astronaut);
    }

    private void createPlayButton() {
        MenuButton playBtn = new MenuButton("Play");
        addMenuButtons(playBtn);

        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                GameViewManager gameManager = new GameViewManager();
                gameManager.createNewGame(stage);
            }
        });
    }

    private void createExitButton() {
        MenuButton exitBtn = new MenuButton("Exit");
        addMenuButtons(exitBtn);
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                stage.close();
            }
        });
    }
    private void createScoreButton() {
        MenuButton scoreBtn = new MenuButton("Score");
        addMenuButtons(scoreBtn);
        scoreBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                scoreSubScene.moveSubScene();
            }
        });
    }

    private void createMenuButtons() {
        createPlayButton();
        createScoreButton();
        createExitButton();
    }

    private void addMenuButtons(final MenuButton button) {
        button.setLayoutX(MENU_BUTTON_START_X);
        button.setLayoutY(MENU_BUTTON_START_Y + menuButtonList.size() * 100);
        menuButtonList.add(button);
        anchorPane.getChildren().add(button);

    }

//TODO: fix image path so we could put image to resources
    private void createBackground() {
        // get image file
        Image background = new Image("galaxy_background.png", 1024, 648, false, true);
        // create background image
        BackgroundImage backgroundImage = new BackgroundImage(background,BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, null);
        anchorPane.setBackground(new Background(backgroundImage));
    }

    private void createScoreSubScenes() {
        scoreSubScene = new MarsLanderSubScene();
        anchorPane.getChildren().add(scoreSubScene);
    }

}
