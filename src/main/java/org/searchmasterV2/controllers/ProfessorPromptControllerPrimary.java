// Controller for Professor portion of GUI

package org.searchmasterV2.controllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import org.searchmasterV2.Professor;

import java.io.IOException;
import java.util.HashMap;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;
import static org.searchmasterV2.controllers.UniversityPromptControllerPrimary.universityID;

public class ProfessorPromptControllerPrimary {

    // UI elements
    @FXML
    private TextField professorTextField;

    @FXML
    private MenuButton professorMenuButton;

    @FXML
    private Button professorNameButton;

    @FXML
    private Button backButton;

    @FXML
    private ProgressIndicator professorProgress;

    // Data management
    private static HashMap<String, String> professorMap;
    public static String professorID;
    public static String professorName;

    // State control
    private boolean isLoading = false;
    private static boolean isFirst = true;

    // Initialization method for the controller
    @FXML
    public void initialize() {
        // Handle Enter key press in text field
        professorTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    professorNameSubmitClicked(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Handle submission of professor name search
    @FXML
    public void professorNameSubmitClicked(ActionEvent event) throws IOException {
        if (!isLoading) {
            setLoading(true);
            Task<HashMap<String, String>> task = new Task<HashMap<String, String>>() {
                @Override
                protected HashMap<String, String> call() throws Exception {
                    HashMap<String, String> map = Professor.getProfessorId(universityID, professorTextField.getText());
                    if (map.isEmpty()) {
                        throw new Exception();
                    }
                    return map;
                }
            };

            // Handle successful task completion
            task.setOnSucceeded(e -> {
                professorMap = task.getValue();
                try {
                    stage.setScene(new Scene(loadFXML("ProfessorDropdown")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    setLoading(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            // Handle task failure
            task.setOnFailed(e -> {
                try {
                    stage.setScene(new Scene(loadFXML("ProfessorError")));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    setLoading(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            new Thread(task).start(); // Start the task in a new thread
        }
    }

    // Control UI state during loading
    private void setLoading(boolean loading) throws IOException {
        isLoading = loading;
        if (loading) {
            if (isFirst) {
                initializeProgressIndicator();
            }
            professorTextField.setDisable(true);
            professorNameButton.setDisable(true);
            professorNameButton.setStyle("-fx-opacity: 0.4;");
        } else {
            if (isFirst) {
                professorProgress.setStyle("-fx-opacity: 0.0;");
                isFirst = false;
            }
            professorTextField.setDisable(false);
            professorNameButton.setDisable(false);
            professorNameButton.setStyle("");
        }
    }

    // Initialize progress indicator appearance
    private void initializeProgressIndicator() {
        professorProgress.setPrefSize(25, 25);
        professorProgress.setStyle("-fx-progress-color: black; -fx-opacity: 1.0;");
        professorProgress.setProgress(-1);
    }

    // Handle menu button click to select professor
    @FXML
    public void professorMenuButtonClicked(MouseEvent event) throws IOException {
        professorMenuButton.getItems().clear();
        for (String str : professorMap.keySet()) {
            MenuItem menuItem = new MenuItem(str);
            menuItem.setOnAction(actionEvent -> {
                professorName = str;
                professorID = professorMap.get(str);
                try {
                    stage.setScene(new Scene(loadFXML("DataTransition")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            professorMenuButton.getItems().add(menuItem);
        }
        professorMenuButton.show();
    }

    // Handle back button click
    @FXML
    public void backButtonClicked(ActionEvent event) throws IOException {
        stage.setScene(new Scene(loadFXML("UniversityDropdown")));
    }
}