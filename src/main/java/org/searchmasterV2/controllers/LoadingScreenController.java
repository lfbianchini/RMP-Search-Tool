package org.searchmasterV2.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    double progress = progressIndicator.getProgress();
                    if (progress < 1.0) {
                        progress += 0.01;
                        progressIndicator.setProgress(progress);
                    } else {
                        timeline.stop();
                        try {
                            stage.setScene(new Scene(loadFXML("searchmaster")));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }),
                new KeyFrame(Duration.seconds(0.02))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}