package com.example.marslanding.model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MenuButton extends Button {
    private final String FONT_PATH = "src/main/java/com/example/marslanding/model/resources/kenvector_future.ttf";
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: #564962;";
    private final String BUTTON_STYLE = "-fx-background-color: #6C5B7B;";

    public MenuButton (String label) {
        setText(label);
        setButtonFont();
        setPrefWidth(300); // should be equal to image width
        setPrefHeight(55); // should be equal to image height
        setStyle(BUTTON_STYLE);
        initialiseButtonListeners();
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 25));
        } catch (FileNotFoundException ex) {
            setFont(Font.font("Verdana", 25));
        }
    }

    private void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(60);
        setLayoutY(getLayoutY());
    }

    private void setButtonReleasedStyle() {
        setStyle(BUTTON_STYLE);
        setPrefHeight(60);
        setLayoutY(getLayoutY());
    }

    private void initialiseButtonListeners() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonReleasedStyle();
                }
            }

        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow(10, Color.rgb(99, 57, 226)));
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }
}
