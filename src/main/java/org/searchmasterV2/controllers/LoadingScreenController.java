// Controller for LoadingScreen Scene

package org.searchmasterV2.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class LoadingScreenController implements Initializable {

    @FXML
    private ProgressIndicator progressIndicator;

    private Timeline timeline;

    // Initializes the controller after FXML file is loaded
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Creates a timeline for animating the progress indicator
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    double progress = progressIndicator.getProgress();
                    if (progress < 1.0) {
                        progress += 0.01;
                        progressIndicator.setProgress(progress);
                    } else {
                        // Stops timeline and switches scene upon completion
                        timeline.stop();
                        try {
                            stage.setScene(new Scene(loadFXML("UniversityPrompt")));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }),
                new KeyFrame(Duration.seconds(0.02))
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeats indefinitely
        timeline.play(); // Starts the timeline animation
    }
}