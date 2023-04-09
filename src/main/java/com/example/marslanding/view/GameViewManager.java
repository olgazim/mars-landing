package com.example.marslanding.view;

import com.example.marslanding.model.*;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

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
    private final static String SUCCESS_MSG = "Congratulations! \nYou have completed the challenge. "
            + "\nWould you like to continue playing?";
    private final static String CRASH_MSG = "Oops! Your ship crashed. \nWould you like to try again?";
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
    private LandingZone landingZone;

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
        score = 0;
        star = new ImageView(STAR);
        star.setFitHeight(40);
        star.setFitWidth(40);
        star.setLayoutX(800);
        star.setLayoutY(20);
        actionPane.getChildren().add(star);
        pointsLabel = new SmallInfoLabel("SCORE : 0");
        pointsLabel.setLayoutX(850);
        pointsLabel.setLayoutY(20);
        actionPane.getChildren().add(pointsLabel);

        if (landingArea == null) {
            landingZone = new LandingZone(
                    LANDING_AREA,
                    WIDTH,
                    0,
                    540,
                    540
            );
            landingArea = landingZone.getImageView();
            actionPane.getChildren().add(landingArea);
        }

        landingZone.setRandomPosition();
    }

    private void nextLevel() {
        if (LANDING_ZONE_SCALE > LANDING_ZONE_SCALE_MIN) {
            LANDING_ZONE_SCALE -= LANDING_ZONE_SCALE_STEP;
        }

        landingZone.setScaleX(LANDING_ZONE_SCALE);

        removeSpaceShip();
        createNewGame(menuStage);
    }

    private double getRandomNumber(final int min, final int max) {
        return (double) ((Math.random() * (max - min)) + min);
    }
    private void setLandingAreaPosition(final ImageView image) {
        if (LANDING_ZONE_SCALE > LANDING_ZONE_SCALE_MIN) {
            LANDING_ZONE_SCALE -= LANDING_ZONE_SCALE_STEP;
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
            displayActionPopup(CRASH_MSG);
        }


        // When landing successfully
        if (landingArea.getBoundsInParent().intersects(shipBounds)) {
            timer.stop();
            score += 10;
            String textToSet = "SCORE : ";
            pointsLabel.setText(textToSet + score);
            displayActionPopup(SUCCESS_MSG);
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

    private void displayActionPopup(final String message) {
        ActionPopup popup = new ActionPopup(message);
        MenuButton playButton = createReplayButton(popup);
        MenuButton exitButton = createExitButton(popup);
        popup.buildPopUp(playButton, exitButton);
        popup.show(actionStage);
    }

    public MenuButton createExitButton(Popup popup) {
        MenuButton exitBtn = new MenuButton("Exit");
        ButtonBar.setButtonData(exitBtn, ButtonBar.ButtonData.FINISH);
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                popup.hide();
                goToMenu();
            }
        });
        return exitBtn;
    }

    public MenuButton createReplayButton(Popup popup) {
        MenuButton replayBtn = new MenuButton("Replay");
        ButtonBar.setButtonData(replayBtn, ButtonBar.ButtonData.FINISH);
        replayBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                popup.hide();
                resetGame();
            }
        });
        return replayBtn;
    }

}

