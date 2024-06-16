package org.searchmasterV2.controllers;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import org.searchmasterV2.Functionality;
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

    private static HashMap<String, String> professorMap;
    private String professorID;

    private boolean isLoading = false;

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
                    return Functionality.getProfessorId(universityID, professorTextField.getText());
                }
            };

            task.setOnSucceeded(e -> {
                professorMap = task.getValue();
                try {
                    stage.setScene(new Scene(loadFXML("searchmasterProfessorDropdown")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                setLoading(false);
            });

            task.setOnFailed(e -> {
                try {
                    stage.setScene(new Scene(loadFXML("searchmasterProfessorError")));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                setLoading(false);
            });

            new Thread(task).start();
        }
    }

    private void setLoading(boolean loading) {
        isLoading = loading;
        if (loading) {
            professorTextField.setDisable(true);
            professorNameButton.setDisable(true);
            professorNameButton.setStyle("-fx-opacity: 0.4;");
        } else {
            professorTextField.setDisable(false);
            professorNameButton.setDisable(false);
            professorNameButton.setStyle("");
        }
    }

    @FXML
    public void professorMenuButtonClicked(MouseEvent event) throws IOException {
        professorMenuButton.getItems().clear();
        for (String str : professorMap.keySet()) {
            MenuItem menuItem = new MenuItem(str);
            menuItem.setOnAction(actionEvent -> {
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