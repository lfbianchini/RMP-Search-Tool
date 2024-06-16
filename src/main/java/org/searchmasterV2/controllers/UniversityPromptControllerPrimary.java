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
import org.searchmasterV2.App;
import org.searchmasterV2.Functionality;
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

    public static HashMap<String, String> universitySet;
    public static String universityID;

    private boolean isLoading = false;

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
                    return Functionality.getUniversityID(universityTextField.getText());
                }
            };

            task.setOnSucceeded(e -> {
                HashMap<String, String> map = task.getValue();
                universitySet = map;
                try {
                    stage.setScene(new Scene(App.loadFXML("searchmasterDropdown")));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                setLoading(false);
            });

            task.setOnFailed(e -> {
                try {
                    stage.setScene(new Scene(App.loadFXML("searchmasterError")));
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
            universityTextField.setDisable(true);
            universityNameButton.setDisable(true);
            universityNameButton.setStyle("-fx-opacity: 0.4;");
        } else {
            universityTextField.setDisable(false);
            universityNameButton.setDisable(false);
            universityNameButton.setStyle("");
        }
    }

    @FXML
    public void universityMenuButtonClicked(MouseEvent event) throws IOException {
        universityMenuButton.getItems().clear();
        for (String str : universitySet.keySet()) {
            MenuItem menuItem = new MenuItem(str);
            menuItem.setOnAction(actionEvent -> {
                universityID = universitySet.get(str);
                try {
                    stage.setScene(new Scene(loadFXML("searchmasterProfessorPrompt")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            universityMenuButton.getItems().add(menuItem);
        }
        universityMenuButton.show();
    }
}