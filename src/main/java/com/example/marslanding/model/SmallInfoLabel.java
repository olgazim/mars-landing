package com.example.marslanding.model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
/**
 * Represents a SmallInfoLabel.
 * A customized label with a specific background and font.
 * @author Shuyi Liu, Olga Zimina
 * @version 1
 */
public class SmallInfoLabel extends Label {
    // The path to the custom font file for the label text
    private static final String FONT_PATH = "src/main/java/com/example/marslanding/model/"
            + "resources/kenvector_future.ttf";
    // The default font to be used for the label text
    private static final String DEFAULT_FONT = "Verdana";
    // The font size for the label text
    private static final int FONT_SIZE = 15;
    // The width of the label
    private static final int LABEL_WIDTH = 130;
    // The height of the label
    private static final int LABEL_HEIGHT = 50;
    // The padding of the label
    private static final int PADDING = 10;

    /**
     * Constructs a new small information label with the specified text.
     * If the font file is not found, the default font is used instead.
     * @param text the text to be displayed on the label, of type String
     */
    public SmallInfoLabel(final String text) {
        setPrefWidth(LABEL_WIDTH);
        setPrefHeight(LABEL_HEIGHT);
        BackgroundImage backgroundImage = new BackgroundImage(new Image("red_info_label.png",
                LABEL_WIDTH,
                LABEL_HEIGHT,
                false,
                false),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        setBackground(new Background(backgroundImage));
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        setLabelFont();
        setText(text);
    }
    /*
       Sets the font for the label text.
    */
    private void setLabelFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), FONT_SIZE));
        } catch (FileNotFoundException ex) {
            setFont(Font.font(DEFAULT_FONT, FONT_SIZE));
        }
    }
}
