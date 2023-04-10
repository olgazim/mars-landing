package com.example.marslanding.model;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
/**
 * Represents a custom MenuButton class.
 * A custom Popup class that represents a button with custom styles and behaviors for a menu.
 * @author Olga Zimina
 * @version 1
 */
public class MenuButton extends Button {
    // The path of the font file to be used for the label text
    private static final String FONT_PATH = "src/main/java/com/example/marslanding/model/"
            + "resources/kenvector_future.ttf";
    // The default font to be used for the label text
    private static final String DEFAULT_FONT = "Verdana";
    // The style for the button when it is pressed
    private static final String BUTTON_PRESSED_STYLE = "-fx-background-color: #B7DEF1;";
    // The style for the button when it is not pressed
    private static final String BUTTON_STYLE = "-fx-background-color: #E2F3EC; -fx-text-fill: #102F53";
    // The height of the button
    private static  final int BUTTON_HEIGHT = 60;
    // The preferred height of the button
    private static  final int BUTTON_PREF_HEIGHT = 55;
    // The width of the button
    private static  final int BUTTON_WIDTH = 300;
    // The font size for the button text
    private static final int FONT_SIZE = 25;

    /**
     * Constructs a new MenuButton with the given label text.
     * @param label the text to be displayed on the button
     */
    public MenuButton(final String label) {
        setText(label);
        setButtonFont();
        setPrefWidth(BUTTON_WIDTH);
        setPrefHeight(BUTTON_PREF_HEIGHT);
        setStyle(BUTTON_STYLE);
        initializeButtonListeners();
    }
    /*
    Sets the font of the button text to the font loaded from the FONT_PATH file.
    If the file is not found, the default font is used instead.
     */
    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), FONT_SIZE));
        } catch (FileNotFoundException ex) {
            setFont(Font.font(DEFAULT_FONT, FONT_SIZE));
        }
    }
    /*
    Sets the CSS style of the button to the pressed style and adjusts the height of the button.
     */
    private void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(BUTTON_HEIGHT);
        setLayoutY(getLayoutY());
    }
    /*
    Sets the CSS style of the button to the default style and adjusts the height of the button.
     */
    private void setButtonReleasedStyle() {
        setStyle(BUTTON_STYLE);
        setPrefHeight(BUTTON_HEIGHT);
        setLayoutY(getLayoutY());
    }
    /*
    Initializes the button listeners for mouse presses, releases, enters, and exits.
     */
    private void initializeButtonListeners() {
        setOnMousePressed(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
            }
        });

        setOnMouseReleased(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                setButtonReleasedStyle();
            }
        });
        setOnMouseEntered(event -> setStyle(BUTTON_PRESSED_STYLE));
        setOnMouseExited(event -> setStyle(BUTTON_STYLE));
    }
}
