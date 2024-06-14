package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
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

    private static HashMap<String, String> professorMap;
    private String professorID;

    @FXML
    public void professorNameSubmitClicked(MouseEvent event) throws IOException {
        try {
            professorMap = Functionality.getProfessorId(universityID, professorTextField.getText());
            stage.setScene(new Scene(loadFXML("searchmasterProfessorDropdown")));
        } catch (IndexOutOfBoundsException e) {
            stage.setScene(new Scene(loadFXML("searchmasterProfessorError")));
        }
    }

    @FXML
    public void professorMenuButtonClicked(MouseEvent event) throws IOException {
        professorMenuButton.getItems().clear();
        for(String str : professorMap.keySet()) {
            MenuItem menuItem = new MenuItem(str);
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    professorID = professorMap.get(str);
                }
            });
            professorMenuButton.getItems().add(menuItem);
        }
        professorMenuButton.show();
    }
}
