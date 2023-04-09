package com.example.marslanding.view;

import com.example.marslanding.model.MenuButton;
import com.example.marslanding.model.SmallInfoLabel;
import com.example.marslanding.model.SpaceShip;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class GameViewManager {
    private final String FONT_PATH = "src/main/java/com/example/marslanding/model/resources/kenvector_future.ttf";
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 648;
    private static final int SPACE_SHIP_STARTING_X = WIDTH / 2;
    private static final int SPACE_SHIP_STARTING_Y = HEIGHT / 4;
    private static final int SMALL_SPACE_SHIP_SIZE = 50;
    private static final double INITIAL_FORCE = 0.2;
    private static final double GOAL_FORCE_MIN = 0;
    private static final double GOAL_FORCE_MAX = 1.5;
    private static final double MAX_FORCE = 2.5;
    private static final double ACCELERATION = 0.05;
    private static final double MAX_THRUST = 2;
    private double LANDING_ZONE_SCALE = 1;
    private double LANDING_ZONE_SCALE_MIN = 0.2;
    private double LANDING_ZONE_SCALE_STEP = 0.1;
     private static final String STAR = "star.png";
    private static final String SPACE_BACKGROUND_IMAGE = "space.jpg";
    private static final String LANDING_AREA = "landing_area.png";
    private final static String EXPLOSION = "crash.png";
    private final static String SUCCESS_MSG = "Congratulations! You have completed the challenge. "
            + "Would you like to continue playing?";
    private final static String CRASH_MSG = "Oops! Your ship crashed. Would you like to try again?";
    private double force = INITIAL_FORCE;
    private double thrustValue = INITIAL_FORCE;
    private AnchorPane actionPane;
    private Scene actionScene;
    private Stage actionStage;
    private Stage menuStage;
    private ImageView spaceShipImage;
    private ImageView star;
    private SmallInfoLabel pointsLabel;
    private SpaceShip spaceShip;
    private boolean thrust;
    private boolean leftKeyFlag;
    private boolean rightKeyFlag;
    private int angle;
    private int score;
    private AnimationTimer timer;

    private ImageView explosionImage;
    private ImageView landingArea;

    public GameViewManager() {
        createActionStage();
        createKeyListeners();
    }

    private void createActionStage() {
        actionPane = new AnchorPane();
        actionScene = new Scene(actionPane, WIDTH, HEIGHT);
        actionStage = new Stage();
        actionStage.setScene(actionScene);
    }

    private void createKeyListeners() {
        actionScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {
                KeyCode keyboardKey = keyEvent.getCode();
                if (keyboardKey == KeyCode.LEFT) {
                    leftKeyFlag = true;
                } else if (keyboardKey == KeyCode.RIGHT) {
                    rightKeyFlag = true;
                } else if (keyboardKey == KeyCode.UP) {
                    thrust = true;
                }
            }
        });

        actionScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {
                KeyCode keyboardKey = keyEvent.getCode();
                if (keyboardKey == KeyCode.LEFT) {
                    leftKeyFlag = false;
                } else if (keyboardKey == KeyCode.RIGHT) {
                    rightKeyFlag = false;
                } else {
                    thrust = false;
                }
            }
        });
    }

    public void createSpaceBackground() {
        // get image file
        Image background = new Image(SPACE_BACKGROUND_IMAGE, WIDTH, HEIGHT, false, true);
        // create background image
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, null);
        actionPane.setBackground(new Background(backgroundImage));
    }

    //    TODO: will need to pass here the type of our ship and based on that choose picture
    public void createSpaceShip() {
        spaceShipImage = new ImageView("small_space_ship.png");
        spaceShipImage.setFitHeight(SMALL_SPACE_SHIP_SIZE);
        spaceShipImage.setFitWidth(SMALL_SPACE_SHIP_SIZE);
        spaceShipImage.setLayoutX(SPACE_SHIP_STARTING_X);
        spaceShipImage.setLayoutY(SPACE_SHIP_STARTING_Y);
        actionPane.getChildren().add(spaceShipImage);
    }

    public void createNewGame(final Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        createSpaceBackground();
        createGameElements();
        createSpaceShip();
        createGameLoop();
        actionStage.show();
    }

    private void createGameElements() {
        readScore();
        star = new ImageView(STAR);
        star.setFitHeight(40);
        star.setFitWidth(40);
        star.setLayoutX(800);
        star.setLayoutY(20);
        actionPane.getChildren().add(star);
        pointsLabel = new SmallInfoLabel("SCORE : " + score);
        pointsLabel.setLayoutX(850);
        pointsLabel.setLayoutY(20);
        actionPane.getChildren().add(pointsLabel);

        landingArea = new ImageView(LANDING_AREA);
        setLandingAreaPosition(landingArea);
        actionPane.getChildren().add(landingArea);
    }


    private double getRandomNumber(int min, int max) {
        return (double) ((Math.random() * (max - min)) + min);
    }
    private void setLandingAreaPosition(ImageView image) {
        if (LANDING_ZONE_SCALE > LANDING_ZONE_SCALE_MIN) {
            LANDING_ZONE_SCALE -= LANDING_ZONE_SCALE_STEP;
        }

        if (score == 0){
            LANDING_ZONE_SCALE = 1;
        }

        image.setScaleX(LANDING_ZONE_SCALE);
        int imageWidth = (int) image.getImage().getWidth();

        image.setX(getRandomNumber(0, WIDTH - imageWidth));
        image.setY(540);
    }

    private void createGameLoop() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                moveSpaceShip();
            }
        };
        timer.start();
    }

    public void moveSpaceShip() {
        double deltaX = 0, deltaY = 0;

        if (thrust) {
            if (thrustValue < MAX_THRUST) thrustValue += ACCELERATION;

            force -= INITIAL_FORCE;
            deltaY -= thrustValue;
        }

        if (!thrust) {
            if (force < MAX_FORCE) {
                force += ACCELERATION;
            }

            if (thrustValue >= MAX_THRUST) {
                thrustValue -= ACCELERATION;
            } else {
                thrustValue = INITIAL_FORCE;
            }
        }

        if (leftKeyFlag) {
            deltaX -= 1;
        }

        if (rightKeyFlag) {
            deltaX += 1;
        }

        deltaY += force;

        moveShipBy(deltaX, deltaY);
    }

    private void moveShipBy(final double dx, final double dy) {
        if (dx == 0 && dy == 0) {
            return;
        }
        final double centerX = spaceShipImage.getBoundsInLocal().getWidth() / 2;
        final double centerY = spaceShipImage.getBoundsInLocal().getHeight() / 2;

        double x = centerX + spaceShipImage.getLayoutX() + dx;
        double y = centerY + spaceShipImage.getLayoutY() + dy;

        moveShipTo(x, y);
    }

    private void moveShipTo(final double x, final double y) {
        final double vesselYPosition = spaceShipImage.getLayoutY();
        final double vesselHeight = spaceShipImage.getBoundsInLocal().getHeight();
        final double centerX = spaceShipImage.getBoundsInLocal().getWidth() / 2;
        final double centerY = spaceShipImage.getBoundsInLocal().getHeight() / 2;

        final double shipBoundsWidth = spaceShipImage.getBoundsInLocal().getWidth() - 50;
        final double shipBoundsHeight = spaceShipImage.getBoundsInLocal().getHeight() - 23;
        final double shipBoundsX = x - centerX - 25;
        final double shipBoundsY = y - centerY - 25;
        final Bounds shipBounds = new BoundingBox(shipBoundsX, shipBoundsY, shipBoundsWidth, shipBoundsHeight);

        final boolean isXValid = x - centerX >= 0 && x + centerX <= WIDTH;
        final boolean isYValid = y - centerY >= 0 && y + centerY <= HEIGHT;

        if (isXValid && isYValid) {
            spaceShipImage.relocate(x - centerX, y - centerY);
        }

        // When crash
        if (shipBoundsY >= 550) {
            explosionImage = new ImageView(EXPLOSION);
            explosionImage.setLayoutX(SPACE_SHIP_STARTING_X/2.0);
            explosionImage.setLayoutY(SPACE_SHIP_STARTING_Y);
            actionPane.getChildren().remove(spaceShipImage);
            actionPane.getChildren().remove(landingArea);
            actionPane.getChildren().add(explosionImage);
            timer.stop();
            score = 0;
            String textToSet = "SCORE : ";
            pointsLabel.setText(textToSet + score);
            showPopup(CRASH_MSG);
            saveScoreToFile(score);
        }

        // When landing successfully
        if (landingArea.getBoundsInParent().intersects(shipBounds)) {
            timer.stop();
            score += 10;
            String textToSet = "SCORE : ";
            pointsLabel.setText(textToSet + score);
            showPopup(SUCCESS_MSG);
            saveScoreToFile(score);
        }
    }

    private void saveScoreToFile(int score) {
        try {
            FileWriter writer = new FileWriter("score.txt");
            writer.write(Integer.toString(score));
            writer.close();
        } catch (IOException e) {
            System.out.println("Cannot write score in file!");
        }
    }


    private void goToMenu() {
        actionStage.close();
        menuStage.show();
    }

    private void resetGame() {
        actionPane.getChildren().remove(landingArea);
        actionPane.getChildren().remove(spaceShipImage);
        actionPane.getChildren().remove(explosionImage);
        createNewGame(menuStage);
    }


    private void showPopup(String message) {
        Popup popup = new Popup();

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: #E2F3EC;");
        anchorPane.setPrefSize(200, 100);

        Label label = new Label(message);
        label.setFont(Font.font("Verdana", 12));
        label.setLayoutY(0);
        label.setLayoutX(85);

        ButtonBar buttonBar = new ButtonBar();
        MenuButton retryButton = createReplayButton(buttonBar, popup);
        MenuButton menuButton = createExitButton(buttonBar, popup);
        buttonBar.getButtons().addAll(menuButton, retryButton);
        buttonBar.setLayoutX(10);
        buttonBar.setLayoutY(45);
        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(buttonBar);
        popup.getContent().add(anchorPane);
        popup.show(actionStage);
    }

    public MenuButton createExitButton(ButtonBar buttonBar, Popup popup) {
        MenuButton exitBtn = new MenuButton("Exit");
        buttonBar.setButtonData(exitBtn, ButtonBar.ButtonData.FINISH);
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                popup.hide();
                goToMenu();
            }
        });
        return exitBtn;
    }

    public MenuButton createReplayButton(ButtonBar buttonBar, Popup popup) {
        MenuButton replayBtn = new MenuButton("Replay");
        buttonBar.setButtonData(replayBtn, ButtonBar.ButtonData.FINISH);
        replayBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                popup.hide();
                resetGame();
            }
        });
        return replayBtn;
    }

    private void readScore() {
        File scoreFile = new File("score.txt");
        if (scoreFile.exists()) {
            try {
                Scanner scanner = new Scanner(scoreFile);
                score = scanner.nextInt();
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            score = 0;
        }
    }
}

