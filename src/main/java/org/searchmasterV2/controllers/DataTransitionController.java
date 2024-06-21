// Controller for DataTransition Scene

package org.searchmasterV2.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
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

    // Static variable to hold Loader instance
    public static Loader data;

    // Constructor
    public DataTransitionController() {
    }

    // Initializes the controller after FXML file is loaded
    @FXML
    public void initialize() {
        // Sets style and initial progress for progress indicator
        progressIndicator2.setStyle("-fx-progress-color: black;");
        progressIndicator2.setProgress(-1);

        // Background task to load data asynchronously
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Loads data using Loader class
                data = new Loader(professorID);
                return null;
            }

            @Override
            protected void succeeded() {
                // Switches scene to DataDashP1 upon successful data loading
                try {
                    stage.setScene(new Scene(loadFXML("DataDashP1")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        // Starts the background task
        new Thread(task).start();
    }
}