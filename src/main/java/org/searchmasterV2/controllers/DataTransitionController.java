package org.searchmasterV2.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;
import org.searchmasterV2.App;

import java.io.IOException;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class DataTransitionController {
    @FXML
    private ProgressIndicator progressIndicator2;

    @FXML
    private Button backButton2;

    @FXML
    public void backButton2Clicked(ActionEvent event) throws IOException {
        stage.setScene(new Scene(loadFXML("searchmasterProfessorDropdown")));
    }

    public void initialize() {
        progressIndicator2.setStyle("-fx-progress-color: black;");
        progressIndicator2.setProgress(-1);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.5), event -> {
                    try {
                        stage.setScene(new Scene(loadFXML("searchmasterDataDash")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
        );
        timeline.play();
    }
}