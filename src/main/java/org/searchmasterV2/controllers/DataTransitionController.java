package org.searchmasterV2.controllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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

    public static Loader data;

    public DataTransitionController() {
    }

    @FXML
    public void initialize() {
        progressIndicator2.setStyle("-fx-progress-color: black;");
        progressIndicator2.setProgress(-1);

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                data = new Loader(professorID);
                return null;
            }

            @Override
            protected void succeeded() {
                try {
                    stage.setScene(new Scene(loadFXML("searchmasterDataDash")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };

        new Thread(task).start();
    }

    @FXML
    public void backButton2Clicked(ActionEvent event) throws IOException {
        stage.setScene(new Scene(loadFXML("searchmasterProfessorDropdown")));
    }
}