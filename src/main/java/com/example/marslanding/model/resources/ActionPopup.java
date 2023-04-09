package com.example.marslanding.model.resources;

import com.example.marslanding.model.MenuButton;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.scene.control.Label;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
/**
 * Represents a ActionPopup.
 * A custom Popup class that displays a message along with two MenuButton objects.
 * @author Olga Zimina
 * @version 1
 */
public class ActionPopup extends Popup {
    // The path to the custom font file for the label text
    private static final String FONT_PATH = "src/main/java/com/example/marslanding/model/resources/"
            + "kenvector_future.ttf";
    // The default font to be used for the label text
    private static final String DEFAULT_FONT = "Verdana";
    // The background color for the popup grid pane
    private static final String BACKGROUND_COLOR = "-fx-background-color: #E2F3EC;";
    // The corner radius for the popup grid pane
    private static final String CORNER_RADIUS = "-fx-background-radius: 10;";
    // The font size for the label text
    private static final int FONT_SIZE = 18;
    // The preferred width for the popup
    private static final int POPUP_WIDTH = 700;
    // The preferred height for the popup
    private static final int POPUP_HEIGHT = 160;
    // The preferred width for the label
    private static final int LABEL_WIDTH = 450;
    // The vertical gap between nodes in the popup grid pane
    private static final int VGAP = 20;
    // The grid pane that holds the label and button bar for the popup
    private GridPane popupGridPane;
    // The label that displays the message to the user
    private Label label;
    // The button bar that holds the MenuButton objects for the user to take an action
    private final ButtonBar popupButtonBar;
    /**
     * Constructs a new ActionPopup object with the given message to display.
     * @param message the message to display in the label of type String
     */
    public ActionPopup(final String message) {
        createPopupGridPane();
        createLabel(message);
        popupButtonBar = new ButtonBar();
    }
    /*
    Creates the grid pane that holds the label and button bar for the popup.
     */
    private void createPopupGridPane() {
        popupGridPane = new GridPane();
        popupGridPane.setVgap(VGAP);
        popupGridPane.setStyle(BACKGROUND_COLOR + CORNER_RADIUS);
        popupGridPane.setPrefSize(POPUP_WIDTH, POPUP_HEIGHT);
    }
    /*
     Creates the label that displays the message to the user.
     */
    private void createLabel(final String message) {
        label = new Label(message);
        setLabelFont();
        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(LABEL_WIDTH);
        GridPane.setConstraints(label, 0, 0);
        popupGridPane.getChildren().add(label);
    }
    /*
    Sets the font for the label text.
     */
    private void setLabelFont() {
        try {
            label.setFont(Font.loadFont(new FileInputStream(FONT_PATH), FONT_SIZE));
        } catch (FileNotFoundException ex) {
            label.setFont(Font.font(DEFAULT_FONT, FONT_SIZE));
        }
    }
    /*
    Builds a button bar with the given play and exit buttons and adds it to the popup.
     */
    private void buildButtonBar(final MenuButton playBtn, final MenuButton exitBtn) {
        popupButtonBar.getButtons().addAll(playBtn, exitBtn);
    }
    /**
     * Builds a popup with the given play and exit buttons.
     * Method adds provided buttons to the button bar
     * and positions the button bar and label in a grid layout, and then adds the
     * grid layout to the content of the popup.
     *
     * @param playButton The button to be added to the button bar for playing, of type MenuButton
     * @param exitButton The button to be added to the button bar for exiting, of type MenuButton
     */
    public void buildPopUp(final MenuButton playButton, final MenuButton exitButton) {
        buildButtonBar(playButton, exitButton);
        GridPane.setConstraints(popupButtonBar, 0, 1);
        popupGridPane.getChildren().add(popupButtonBar);
        getContent().add(popupGridPane);
        popupGridPane.setAlignment(Pos.BOTTOM_CENTER);
    }
    /**
     * Compares this ActionPopup object to the given object.
     *
     * @param obj The object that is compared to this ActionPopup object
     * @return True - if the given object is of type ActionPopup with the same state as this object,
     * false - otherwise
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ActionPopup popup = (ActionPopup) obj;
        return popupButtonBar.equals(popup.popupButtonBar)
                && label.equals(popup.label)
                && popupGridPane.equals(popup.popupGridPane);
    }
    /**
     * Returns a hash code for this ActionPopup object.
     * @return A hash code value of type int for this ActionPopup object
     */
    @Override
    public int hashCode() {
        final int prime = 17;
        final int additionValue = 37;
        int result = prime;
        result = additionValue * result + popupGridPane.hashCode();
        result = additionValue * result + label.hashCode();
        result = additionValue * result + popupButtonBar.hashCode();
        return result;
    }
    /**
     * Returns a String representation of this ActionPopup.
     * @return description as a String
     */
    @Override
    public String toString() {
        return "ActionPopup{"
                + "popupGridPane=" + popupGridPane
                + ", label=" + label
                + ", popupButtonBar=" + popupButtonBar
                + '}';
    }
}
