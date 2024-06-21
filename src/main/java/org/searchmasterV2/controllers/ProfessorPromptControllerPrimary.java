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

    private static HashMap<String, String> professorMap;
    public static String professorID;
    public static String professorName;
    private boolean isLoading = false;
    private static boolean isFirst = true;

    @FXML
    public void initialize() {
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

            task.setOnSucceeded(e -> {
                professorMap = task.getValue();
                try {
                    stage.setScene(new Scene(loadFXML("searchmasterProfessorDropdown")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    setLoading(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            task.setOnFailed(e -> {
                try {
                    stage.setScene(new Scene(loadFXML("searchmasterProfessorError")));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    setLoading(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            new Thread(task).start();
        }
    }

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

    private void initializeProgressIndicator() {
        professorProgress.setPrefSize(25, 25);
        professorProgress.setStyle("-fx-progress-color: black; -fx-opacity: 1.0;");
        professorProgress.setProgress(-1);
    }

    @FXML
    public void professorMenuButtonClicked(MouseEvent event) throws IOException {
        professorMenuButton.getItems().clear();
        for (String str : professorMap.keySet()) {
            MenuItem menuItem = new MenuItem(str);
            menuItem.setOnAction(actionEvent -> {
                professorName = str;
                professorID = professorMap.get(str);
                try {
                    stage.setScene(new Scene(loadFXML("searchMasterDataTransition")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            professorMenuButton.getItems().add(menuItem);
        }
        professorMenuButton.show();
    }

    @FXML
    public void backButtonClicked(ActionEvent event) throws IOException {
        stage.setScene(new Scene(loadFXML("searchmasterDropdown")));
    }
}