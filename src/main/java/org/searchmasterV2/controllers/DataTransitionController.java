package org.searchmasterV2.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;
import org.searchmasterV2.Loader;
import java.io.IOException;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;
import static org.searchmasterV2.controllers.ProfessorPromptControllerPrimary.professorID;

public class DataTransitionController {

    @FXML
    private ProgressIndicator progressIndicator2;

    @FXML
    private Button backButton2;

    public static Loader data;

    static {
        try {
            data = new Loader(professorID);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize DataTransitionController", e);
        }
    }

    public DataTransitionController() {

    }

    @FXML
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

    @FXML
    public void backButton2Clicked(ActionEvent event) throws IOException {
        stage.setScene(new Scene(loadFXML("searchmasterProfessorDropdown")));
    }
}
