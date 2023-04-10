package com.example.marslanding.view;

import com.example.marslanding.model.ShipType;
import com.example.marslanding.model.*;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class GameViewManager {
    private final String FONT_PATH = "src/main/java/com/example/marslanding/model/resources/kenvector_future.ttf";
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 648;
    private static final int SPACE_SHIP_STARTING_X = WIDTH / 2;
    private static final int SPACE_SHIP_STARTING_Y = HEIGHT / 4;
    private static final double INITIAL_FORCE = 0.2;
    private static final double GOAL_FORCE_MAX = 1.5;
    private static final double MAX_FORCE = 2.5;
    private static final double ACCELERATION = 0.05;
    private static final double MAX_THRUST = 2;
    private double LANDING_ZONE_SCALE = 1;
    private double LANDING_ZONE_SCALE_MIN = 0.2;
    private double LANDING_ZONE_SCALE_STEP = 0.1;
     private static final String STAR = "star.png";
    private static final String SPACE_BACKGROUND_IMAGE = "space.jpg";
    private final static String EXPLOSION = "crash.png";
    private final static String SUCCESS_MSG = "Congratulations! \nYou have completed the challenge. "
            + "\nWould you like to continue playing?";
    private final static String CRASH_MSG = "Oops! Your ship crashed. \nWould you like to try again?";
    private final static String continuePlayingBtnLabel = "Next Level";
    private final static String retryBtnLabel = "Retry";
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

    public void createNewGame(final Stage menuStage, final ShipType type) {
        force = INITIAL_FORCE;
        this.menuStage = menuStage;
        this.menuStage.hide();
        createSpaceBackground();
        createGameElements(type);
        createGameLoop();
        actionStage.show();
    }
    private void createSpaceShip(final ShipType type) {
        spaceShip = new SpaceShip(ShipType.FALCON9,
                WIDTH,
                HEIGHT);
        spaceShipImage = spaceShip.getShipImage();
        actionPane.getChildren().add(spaceShipImage);
    }

    private void removeSpaceShip() {
        actionPane.getChildren().remove(spaceShipImage);
    }
    private void createGameElements(ShipType type) {
        score = 0;
        readScore();
        System.out.println("Current score is: " + score);

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

        if (landingArea == null) {
            landingZone = new LandingZone(
                    WIDTH,
                    0,
                    540,
                    540
            );
            landingArea = landingZone.getImageView();
        }
        actionPane.getChildren().add(landingArea);
        landingZone.setRandomPosition();
        createSpaceShip(type);
    }
    private double getRandomNumber(final int min, final int max) {
        return (double) ((Math.random() * (max - min)) + min);
    }
    private void setLandingAreaPosition(final ImageView image) {
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
        spaceShip.moveShipBy(deltaX, deltaY);
        checkPosition(spaceShip, landingZone);
    }

    private void checkPosition(final SpaceShip ship, final LandingZone landingSite) {
        final double landingZoneBaseline = landingSite.getBaseLine();
        final double shipBottomLine = spaceShip.findBottomLine();
        final boolean hasShipReachedLandingZone =  shipBottomLine >= landingZoneBaseline;
        if (hasShipReachedLandingZone) {
            final Bounds landingZoneBounds = landingSite.getBounds();
            final double landingZoneMaxX = landingZoneBounds.getMaxX();
            final double landingZoneMinX = landingZoneBounds.getMinX();

            final Bounds shipBounds = spaceShip.getShipImage().getBoundsInParent();
            final double vesselMaxX = shipBounds.getMaxX();
            final double vesselMinX = shipBounds.getMinX();

            final boolean isXPositionInRange = vesselMaxX <= landingZoneMaxX && vesselMinX >= landingZoneMinX;
            final boolean isForceInRange = force <= GOAL_FORCE_MAX;
            final boolean isLanded = isXPositionInRange && isForceInRange;
            final boolean isCrashed = !isXPositionInRange || !isForceInRange;

            if (isLanded) {
                timer.stop();
                score += 10;
                saveScoreToFile(score);
                showSuccessPopup();
            }
            if (isCrashed) {
                timer.stop();
                score = 0;
                saveScoreToFile(score);
                explosionImage = new ImageView(EXPLOSION);
                explosionImage.setLayoutX(SPACE_SHIP_STARTING_X / 2.0);
                explosionImage.setLayoutY(SPACE_SHIP_STARTING_Y);

                removeSpaceShip();
                actionPane.getChildren().remove(landingArea);
                actionPane.getChildren().add(explosionImage);

                showFailurePopup();
            }
        }
    }

    private void showSuccessPopup() {
        displayActionPopup(SUCCESS_MSG, continuePlayingBtnLabel, this::nextLevel);
    }

    private void showFailurePopup() {
        displayActionPopup(CRASH_MSG, retryBtnLabel, this::retry);
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

    private void retry() {
        ShipType type = spaceShip.getType();
        actionPane.getChildren().remove(explosionImage);
        createNewGame(menuStage, type);
    }

    private void nextLevel() {
        ShipType type = spaceShip.getType();
        if (LANDING_ZONE_SCALE > LANDING_ZONE_SCALE_MIN) {
            LANDING_ZONE_SCALE -= LANDING_ZONE_SCALE_STEP;
        }

        landingZone.setScaleX(LANDING_ZONE_SCALE);
        removeSpaceShip();
        actionPane.getChildren().remove(landingArea);
        createNewGame(menuStage, type);
    }

    private void displayActionPopup(final String message, String label, Runnable action) {
        ActionPopup popup = new ActionPopup(message);
        MenuButton playButton = createReplayButton(popup, label, action);
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

    public MenuButton createReplayButton(Popup popup, String label, Runnable action) {
        MenuButton replayBtn = new MenuButton(label);
        ButtonBar.setButtonData(replayBtn, ButtonBar.ButtonData.OK_DONE);
        replayBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                popup.hide();
                action.run();
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

