package com.example.marslanding.view;

import com.example.marslanding.model.SpaceShip;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameViewManager {
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
    private double force = INITIAL_FORCE;
    private double thrustValue = INITIAL_FORCE;
    private AnchorPane actionPane;
    private Scene actionScene;
    private Stage actionStage;
    private Stage menuStage;
    private ImageView spaceShipImage;

    private SpaceShip spaceShip;
    private  boolean thrust;
    private  boolean leftKeyFlag;
    private  boolean rightKeyFlag;
    private int angle;
    private AnimationTimer timer;

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
                if(keyboardKey == KeyCode.LEFT) {
                    leftKeyFlag = true;
                } else if (keyboardKey == KeyCode.RIGHT) {
                    rightKeyFlag = true;
                } else {
                    thrust = true;
                }
            }
        });

        actionScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {
                KeyCode keyboardKey = keyEvent.getCode();
                if(keyboardKey == KeyCode.LEFT) {
                    leftKeyFlag = false;
                } else if (keyboardKey == KeyCode.RIGHT) {
                    rightKeyFlag = false;
                } else {
                    thrust = false;
                }
            }
        });
    }
//    TODO: will need to pass here the tyype of our ship and based on that choose picture
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
        createSpaceShip();
        createGameLoop();
        actionStage.show();
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
            deltaX += 1;
        }

        if (rightKeyFlag) {
            deltaX -= 1;
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
        final boolean isXValid = x - centerX >= 0 && x + centerX <= WIDTH;
        final boolean isYValid = y - centerY >= 0 && y + centerY <= HEIGHT;

        if (isXValid && isYValid) {
            spaceShipImage.relocate(x - centerX, y - centerY);
        }

    }

}
