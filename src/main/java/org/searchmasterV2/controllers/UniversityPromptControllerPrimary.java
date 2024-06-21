package org.searchmasterV2.controllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import org.searchmasterV2.App;
import org.searchmasterV2.Professor;

import java.io.IOException;
import java.util.HashMap;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class UniversityPromptControllerPrimary {

    @FXML
    private TextField universityTextField;

    @FXML
    private MenuButton universityMenuButton;

    @FXML
    private Button universityNameButton;

    @FXML
    private ProgressIndicator universityProgress;

    public static HashMap<String, String> universitySet;
    public static String universityID;
    private boolean isLoading = false;
    private static boolean isFirst = true;

    @FXML
    public void initialize() {
        universityTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    universityNameClicked(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void universityNameClicked(ActionEvent event) throws IOException {
        if (!isLoading) {
            setLoading(true);
            Task<HashMap<String, String>> task = new Task<HashMap<String, String>>() {
                @Override
                protected HashMap<String, String> call() throws Exception {
                    HashMap<String, String> map = Professor.getUniversityID(universityTextField.getText());
                    if (map.isEmpty()) {
                        throw new Exception();
                    }
                    return map;
                }
            };
            task.setOnSucceeded(e -> {
                HashMap<String, String> map = task.getValue();
                universitySet = map;
                try {
                    stage.setScene(new Scene(App.loadFXML("UniversityDropdown")));
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
                    stage.setScene(new Scene(App.loadFXML("UniversityError")));
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
            universityTextField.setDisable(true);
            universityNameButton.setDisable(true);
            universityNameButton.setStyle("-fx-opacity: 0.4;");
        } else {
            if (isFirst) {
                universityTextField.setStyle("-fx-opacity: 0.0;");
                isFirst = false;
            }
            universityTextField.setDisable(false);
            universityNameButton.setDisable(false);
            universityNameButton.setStyle("");
        }
    }

    private void initializeProgressIndicator() {
        universityProgress.setPrefSize(25, 25);
        universityProgress.setStyle("-fx-progress-color: black; -fx-opacity: 1.0;");
        universityProgress.setProgress(-1);
    }

    @FXML
    public void universityMenuButtonClicked(MouseEvent event) throws IOException {
        universityMenuButton.getItems().clear();
        for (String str : universitySet.keySet()) {
            MenuItem menuItem = new MenuItem(str);
            menuItem.setOnAction(actionEvent -> {
                universityID = universitySet.get(str);
                try {
                    stage.setScene(new Scene(loadFXML("ProfessorPrompt")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            universityMenuButton.getItems().add(menuItem);
        }
        universityMenuButton.show();
    }
}