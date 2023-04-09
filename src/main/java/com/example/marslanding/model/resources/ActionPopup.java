package com.example.marslanding.model.resources;

import com.example.marslanding.model.MenuButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.scene.control.Label;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ActionPopup extends Popup {
    private final String FONT_PATH = "src/main/java/com/example/marslanding/model/resources/kenvector_future.ttf";
    private final String DEFAULT_FONT = "Verdana";
    private final String BACKGROUND_COLOR = "-fx-background-color: #E2F3EC;";
    private final String CORNER_RADIUS = "-fx-background-radius: 10;";
    private final int FONT_SIZE = 18;
    private final int POPUP_WIDTH = 700;
    private final int POPUP_HEIGHT = 160;
    private final int VGAP = 20;
    private GridPane popupGridPane;
    private Label label;
    private ButtonBar popupButtonBar;

    public ActionPopup(final String message) {
        createPopupGridPane();
        createLabel(message);
        popupButtonBar = new ButtonBar();
    }

    private void createPopupGridPane() {
        popupGridPane = new GridPane();
        popupGridPane.setVgap(VGAP);
        popupGridPane.setStyle(BACKGROUND_COLOR + CORNER_RADIUS);
        popupGridPane.setPrefSize(POPUP_WIDTH, POPUP_HEIGHT);
    }

    private void createLabel(final String message) {
        label = new Label(message);
        setLabelFont();
        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(450);
        GridPane.setConstraints(label, 0, 0);
        popupGridPane.getChildren().add(label);
    }

    private void setLabelFont() {
        try {
            label.setFont(Font.loadFont(new FileInputStream(FONT_PATH), FONT_SIZE));
        } catch (FileNotFoundException ex) {
            label.setFont(Font.font(DEFAULT_FONT, FONT_SIZE));
        }
    }

    private void buildButtonBar(final MenuButton playBtn, final MenuButton exitBtn) {
//        playBtn.setPrefWidth(POPUP_WIDTH/2);
//        exitBtn.setPrefWidth(POPUP_WIDTH/2);
        popupButtonBar.getButtons().addAll(playBtn, exitBtn);
        popupButtonBar.setPadding(new Insets(0, 0, 0, -20));
    }

    public void buildPopUp(final MenuButton playButton, final MenuButton exitButton) {
        buildButtonBar(playButton, exitButton);
        GridPane.setConstraints(popupButtonBar, 0, 1);
        popupGridPane.getChildren().add(popupButtonBar);
        getContent().add(popupGridPane);
        popupGridPane.setAlignment(Pos.BOTTOM_CENTER);
    }
}
