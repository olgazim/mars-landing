package com.example.marslanding.view;

import com.example.marslanding.model.ShipType;
import com.example.marslanding.model.MarsLanderSubScene;
import com.example.marslanding.model.MenuButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
/**
 This class represents the view manager for the Mars Lander game, which creates and manages
 the user interface components of the game.
 */
public class ViewManager {
    // width of the game screen
    private static final int WIDTH = 1024;
    // height of the game screen
    private static final int HEIGHT = 648;
    // the start x coordinate of menu button
    private static final int MENU_BUTTON_START_X = 100;
    // the start y coordinate of menu button
    private static final int MENU_BUTTON_START_Y = 200;
    // logo's height
    private static final int LOGO_HEIGHT = 120;
    // logo's width
    private static final int LOGO_WIDTH = 100;
    // logo's x coordinate
    private static final int LOGO_X_POSITION = 900;
    // logo's y coordinate
    private static final int LOGO_Y_POSITION = 230;
    // path to the font used in the game
    private final String FONT_PATH = "src/main/java/com/example/marslanding/model/resources/kenvector_future.ttf";
    // an AnchorPane object
    private AnchorPane anchorPane;
    // a Scene object
    private Scene scene;
    // a Stage object
    private Stage stage;
    // a list of MenuButton objects
    private List<MenuButton> menuButtonList;
    // a MarsLanderSubScene object scoreSubScene
    private MarsLanderSubScene scoreSubScene;

    /**
     * Constructs a new ViewManager object.
     */
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

    /**
     * Returns the stage of the view manager.
     * @return The stage of the view manager.
     */
    public Stage getStage() {
        return stage;
    }

    // Creates the logo for the game, with an astronaut image and a drop shadow effect. When
    // the mouse enters or exits the image, the drop shadow effect changes.
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

    // Creates the "Play" button for the game, which starts a new game when clicked.
    private void createPlayButton() {
        MenuButton playBtn = new MenuButton("Play");
        addMenuButtons(playBtn);

        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                GameViewManager gameManager = new GameViewManager();
                gameManager.createNewGame(stage, ShipType.FALCON9);
            }
        });
    }
    // Creates the "Exit" button for the game, which exits the application when clicked.
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
    // Creates the "Score" button for the game, which shows the player's score when clicked.
    // The score is read from a file named "score.txt" and displayed on the screen.
    private void createScoreButton() {
        MenuButton scoreBtn = new MenuButton("Score");
        addMenuButtons(scoreBtn);
        scoreBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                scoreSubScene.moveSubScene();
                File scoreFile = new File("score.txt");
                if (scoreFile.exists()) {
                    try {
                        Scanner scanner = new Scanner(scoreFile);
                        int score = scanner.nextInt();
                        Text scoreLabel = new Text("Score: " + score);
                        scoreLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 25));
                        scoreLabel.setLayoutX(630);
                        scoreLabel.setLayoutY(330);
                        anchorPane.getChildren().add(scoreLabel);
                    } catch (FileNotFoundException e) {
                        System.out.println("Cannot find score.txt!");
                    }
                }
            }
        });
    }

    // Creates all menu buttons for the game.
    private void createMenuButtons() {
        createPlayButton();
        createScoreButton();
        createExitButton();
    }

    // Adds a menu button to the list of buttons and to the game scene.
    // @param button the button to add
    private void addMenuButtons(final MenuButton button) {
        button.setLayoutX(MENU_BUTTON_START_X);
        button.setLayoutY(MENU_BUTTON_START_Y + menuButtonList.size() * 100);
        menuButtonList.add(button);
        anchorPane.getChildren().add(button);

    }

    // Creates the game's background image and sets it as the game's background.
    // The image is read from a file named "galaxy_background.png".
    private void createBackground() {
        // get image file
        Image background = new Image("galaxy_background.png", 1024, 648, false, true);
        // create background image
        BackgroundImage backgroundImage = new BackgroundImage(background,BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, null);
        anchorPane.setBackground(new Background(backgroundImage));
    }

    // Creates the score sub-scene, which is used to display the player's score.
    private void createScoreSubScenes() {
        scoreSubScene = new MarsLanderSubScene();
        anchorPane.getChildren().add(scoreSubScene);
    }

    /**
     * Returns a string representation of the ViewManager object.
     *
     * @return A string representation of the ViewManager object.
     */
    @Override
    public String toString() {
        return "ViewManager{" +
                "FONT_PATH='" + FONT_PATH + '\'' +
                ", anchorPane=" + anchorPane +
                ", scene=" + scene +
                ", stage=" + stage +
                ", menuButtonList=" + menuButtonList +
                ", scoreSubScene=" + scoreSubScene +
                '}';
    }

    /**
     * Compares this ViewManager object to the given object.
     *
     * @param o The object that is compared to this ViewManager object
     * @return True - if the given object is of type ViewManager with the same state as this object,
     * false - otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewManager that = (ViewManager) o;
        return Objects.equals(FONT_PATH, that.FONT_PATH) && Objects.equals(anchorPane, that.anchorPane) && Objects.equals(scene, that.scene) && Objects.equals(stage, that.stage) && Objects.equals(menuButtonList, that.menuButtonList) && Objects.equals(scoreSubScene, that.scoreSubScene);
    }

    /**
     * Returns a hash code for this ViewManager object.
     * @return A hash code value for this ViewManager object
     */
    @Override
    public int hashCode() {
        return Objects.hash(FONT_PATH, anchorPane, scene, stage, menuButtonList, scoreSubScene);
    }
}
