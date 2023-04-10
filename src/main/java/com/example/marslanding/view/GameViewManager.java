package com.example.marslanding.view;

import com.example.marslanding.model.ShipType;
import com.example.marslanding.model.*;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * Represents GameViewManager.
 * The GameViewManager class manages the game view and contains methods for creating the game scene,
 * setting up key listeners, and managing game elements such as the spaceship, landing zone, and score.
 * @author Olga Zimina, Shuyi Liu
 * @version 1
 */
public class GameViewManager {
    // The width of the game view.
    private static final int WIDTH = 1024;

    //The height of the game view.
    private static final int HEIGHT = 648;

    // The starting x-coordinate of the spaceship.
    private static final int SPACE_SHIP_STARTING_X = WIDTH / 2;

    // The starting y-coordinate of the spaceship.
    private static final int SPACE_SHIP_STARTING_Y = HEIGHT / 4;

    // The initial force of the spaceship.
    private static final double INITIAL_FORCE = 0.2;

    // The maximum force allowed to reach the landing zone successfully.
    private static final double GOAL_FORCE_MAX = 1.5;

    // The maximum force of the spaceship.
    private static final double MAX_FORCE = 2.5;

    // The acceleration of the spaceship.
    private static final double ACCELERATION = 0.05;

    // The maximum thrust of the spaceship.
    private static final double MAX_THRUST = 2;

    // The filename of the image of the star.
    private static final String STAR = "star.png";

    // The filename of the image of the space background.
    private static final String SPACE_BACKGROUND_IMAGE = "space.jpg";

    // The filename of the image of the explosion.
    private static final  String EXPLOSION = "crash.png";

    // The message shown when the player successfully completes a level.
    private static final  String SUCCESS_MSG = """
            Congratulations!\s
            You have completed the challenge.\s
            Would you like to continue playing?""";

    // The  message shown when the player crashes the spaceship.
    private static final String CRASH_MSG = "Oops! Your ship crashed. \nWould you like to try again?";

    // The label of the button used for continuing to the next level.
    private static final String CONTINUE_PLAYING_BTN_LABEL = "Next Level";

    // The label of the button used for retrying the level.
    private static final String RETRY_BTN_LABEL = "Retry";

    // The scale of the landing zone.
    private double landingZoneScale = 1;

    // The current force of the spaceship.
    private double force = INITIAL_FORCE;

    // The current thrust value of the spaceship.
    private double thrustValue = INITIAL_FORCE;

    // a AnchorPane object for the anchor pane of the game view.
    private AnchorPane actionPane;

    // a Scene object for the scene of the game view.
    private Scene actionScene;

    // a Stage object for the stage of the game view.
    private Stage actionStage;

    // a Stage object for the stage of the menu view.
    private Stage menuStage;

    // a ImageView object for the image view of the spaceship.
    private ImageView spaceShipImage;

    // a SpaceShip object for the spaceship model.
    private SpaceShip spaceShip;

    // Boolean flag to indicate if the spaceship is thrusting or not.
    private boolean thrust;

    // Boolean flag to indicate if the left arrow key is currently pressed.
    private boolean leftKeyFlag;

    // Boolean flag to indicate if the right arrow key is currently pressed.
    private boolean rightKeyFlag;

    // The player's current score.
    private int score;

    // The AnimationTimer object used to update the game state.
    private AnimationTimer timer;

    // The ImageView object used to display the explosion animation.
    private ImageView explosionImage;

    // The ImageView object used to display the landing area background image.
    private ImageView landingArea;

    // The LandingZone object representing the landing zone on the game board.
    private LandingZone landingZone;

    /**
     * Constructs a new GameViewManager object and creates the action stage and key listeners.
     */
    public GameViewManager() {
        createActionStage();
        createKeyListeners();
    }

    // Creates the action stage and scene.
    private void createActionStage() {
        actionPane = new AnchorPane();
        actionScene = new Scene(actionPane, WIDTH, HEIGHT);
        actionStage = new Stage();
        actionStage.setScene(actionScene);
    }

    // Creates the key listeners for the action scene.
    private void createKeyListeners() {
        actionScene.setOnKeyPressed(keyEvent -> {
            KeyCode keyboardKey = keyEvent.getCode();
            if (keyboardKey == KeyCode.LEFT) {
                leftKeyFlag = true;
            } else if (keyboardKey == KeyCode.RIGHT) {
                rightKeyFlag = true;
            } else if (keyboardKey == KeyCode.UP) {
                thrust = true;
            }
        });

        actionScene.setOnKeyReleased(keyEvent -> {
            KeyCode keyboardKey = keyEvent.getCode();
            if (keyboardKey == KeyCode.LEFT) {
                leftKeyFlag = false;
            } else if (keyboardKey == KeyCode.RIGHT) {
                rightKeyFlag = false;
            } else {
                thrust = false;
            }
        });
    }

    /**
     * Creates the space background image.
     */
    public void createSpaceBackground() {
        // get image file
        Image background = new Image(SPACE_BACKGROUND_IMAGE, WIDTH, HEIGHT, false, true);
        // create background image
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, null);
        actionPane.setBackground(new Background(backgroundImage));
    }

    /**
     * Creates a new game with the specified menu stage and ship type.
     *
     * @param menuStage the menu stage to hide
     * @param type the ship type to create
     */
    public void createNewGame(final Stage menuStage, final ShipType type) {
        force = INITIAL_FORCE;
        this.menuStage = menuStage;
        this.menuStage.hide();
        createSpaceBackground();
        createGameElements(type);
        createGameLoop();
        actionStage.show();
    }


    // Creates the spaceship image.
    // @param type the ship type to create for future improvement
    private void createSpaceShip(final ShipType type) {
        spaceShip = new SpaceShip(type,
                WIDTH,
                HEIGHT);
        spaceShipImage = spaceShip.getShipImage();
        actionPane.getChildren().add(spaceShipImage);
    }

    // Removes the spaceship image.
    private void removeSpaceShip() {
        actionPane.getChildren().remove(spaceShipImage);
    }

    // Creates the game elements.
    // @param type the ship type to create
    private void createGameElements(final ShipType type) {
        score = 0;
        readScore();
        System.out.println("Current score is: " + score);

        ImageView star = new ImageView(STAR);
        star.setFitHeight(40);
        star.setFitWidth(40);
        star.setLayoutX(800);
        star.setLayoutY(20);
        actionPane.getChildren().add(star);
        SmallInfoLabel pointsLabel = new SmallInfoLabel("SCORE : " + score);
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

    // Creates the game loop.
    private void createGameLoop() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                moveSpaceShip();
            }
        };
        timer.start();
    }

    /**
     * Moves the spaceship.
     */
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


     // Checks the position of the spaceship and landing zone.
     // @param ship the spaceship, left for future use.
     // @param landingSite the landing zone
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

    // Shows a success popup with a message and continue playing button.
    // Calls the nextLevel method on continue playing button click.
    private void showSuccessPopup() {
        displayActionPopup(SUCCESS_MSG, CONTINUE_PLAYING_BTN_LABEL, this::nextLevel);
    }

    // Shows a failure popup with a message and retry button.
    // Calls the retry method on retry button click.
    private void showFailurePopup() {
        displayActionPopup(CRASH_MSG, RETRY_BTN_LABEL, this::retry);
    }

    // Saves the score to a file with the name "score.txt".
    // @param score The score to be saved.
    private void saveScoreToFile(final int score) {
        try {
            FileWriter writer = new FileWriter("score.txt");
            writer.write(Integer.toString(score));
            writer.close();
        } catch (IOException e) {
            System.out.println("Cannot write score in file!");
        }
    }

    // Closes the action stage and shows the menu stage.
    private void goToMenu() {
        actionStage.close();
        menuStage.show();
    }

    // Restarts the game with the same spaceship type after a crash.
    private void retry() {
        ShipType type = spaceShip.getType();
        actionPane.getChildren().remove(explosionImage);
        createNewGame(menuStage, ShipType.FALCON9);
    }

    // Starts the next level of the game with a smaller landing zone.
    private void nextLevel() {
        ShipType type = spaceShip.getType();
        double LANDING_ZONE_SCALE_MIN = 0.2;
        if (landingZoneScale > LANDING_ZONE_SCALE_MIN) {
            double LANDING_ZONE_SCALE_STEP = 0.1;
            landingZoneScale -= LANDING_ZONE_SCALE_STEP;
        }

        landingZone.setScaleX(landingZoneScale);
        removeSpaceShip();
        actionPane.getChildren().remove(landingArea);
        createNewGame(menuStage, type);
    }

    // Displays an action popup with a message and buttons.
    // @param message The message to be displayed.
    // @param label The label for the action button.
    // @param action The action to be performed on button click.
    private void displayActionPopup(final String message, final String label, final Runnable action) {
        ActionPopup popup = new ActionPopup(message);
        MenuButton playButton = createReplayButton(popup, label, action);
        MenuButton exitButton = createExitButton(popup);
        popup.buildPopUp(playButton, exitButton);
        popup.show(actionStage);
    }

    /**
     Creates an exit button for a popup.
     @param popup The popup to be closed on button click.
     @return The exit button.
     */
    public MenuButton createExitButton(Popup popup) {
        MenuButton exitBtn = new MenuButton("Exit");
        ButtonBar.setButtonData(exitBtn, ButtonBar.ButtonData.FINISH);
        exitBtn.setOnAction(event -> {
            popup.hide();
            goToMenu();
        });
        return exitBtn;
    }

    /**
     Creates a replay button for a popup.
     @param popup The popup to be closed on button click.
     @param label The label for the replay button.
     @param action The action to be performed on button click.
     @return The replay button.
     */
    public MenuButton createReplayButton(Popup popup, String label, Runnable action) {
        MenuButton replayBtn = new MenuButton(label);
        ButtonBar.setButtonData(replayBtn, ButtonBar.ButtonData.OK_DONE);
        replayBtn.setOnAction(event -> {
            popup.hide();
            action.run();
        });
        return replayBtn;
    }
    /**
     Reads the score from the file with the name "score.txt".
     If the file does not exist, sets the score to 0.
     */
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


    /**
     * Compares this GameViewManager object to the given object.
     *
     * @param o The object that is compared to this GameViewManager object
     * @return True - if the given object is of type GameViewManager with the same state as this object,
     * false - otherwise
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameViewManager that = (GameViewManager) o;
        return Double.compare(that.landingZoneScale, landingZoneScale) == 0
                && Double.compare(that.force, force) == 0
                && Double.compare(that.thrustValue, thrustValue) == 0
                && thrust == that.thrust
                && leftKeyFlag == that.leftKeyFlag
                && rightKeyFlag == that.rightKeyFlag
                && score == that.score
                && Objects.equals(actionPane, that.actionPane)
                && Objects.equals(actionScene, that.actionScene)
                && Objects.equals(actionStage, that.actionStage)
                && Objects.equals(menuStage, that.menuStage)
                && Objects.equals(spaceShipImage, that.spaceShipImage)
                && Objects.equals(spaceShip, that.spaceShip)
                && Objects.equals(timer, that.timer)
                && Objects.equals(explosionImage, that.explosionImage)
                && Objects.equals(landingArea, that.landingArea)
                && Objects.equals(landingZone, that.landingZone);
    }

    /**
     * Returns a hash code for this GameViewManager object.
     * @return A hash code value for this GameViewManager object
     */
    @Override
    public int hashCode() {
        return Objects.hash(landingZoneScale, force, thrustValue, actionPane, actionScene, actionStage, menuStage, spaceShipImage, spaceShip, thrust, leftKeyFlag, rightKeyFlag, score, timer, explosionImage, landingArea, landingZone);
    }
    /**
     * Returns a string representation of the GameViewManager object.
     *
     * @return A string representation of the GameViewManager object.
     */
    @Override
    public String toString() {
        return "GameViewManager{"
                + "LANDING_ZONE_SCALE=" + landingZoneScale
                + ", force=" + force
                + ", thrustValue=" + thrustValue
                + ", actionPane=" + actionPane
                + ", actionScene=" + actionScene
                + ", actionStage=" + actionStage
                + ", menuStage=" + menuStage
                + ", spaceShipImage=" + spaceShipImage
                + ", spaceShip=" + spaceShip
                + ", thrust=" + thrust
                + ", leftKeyFlag=" + leftKeyFlag
                + ", rightKeyFlag=" + rightKeyFlag
                + ", score=" + score
                + ", timer=" + timer
                + ", explosionImage=" + explosionImage
                + ", landingArea=" + landingArea
                + ", landingZone=" + landingZone
                + '}';
    }
}

