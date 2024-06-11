package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.searchmasterV2.App;
import org.searchmasterV2.Functionality;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static org.searchmasterV2.App.stage;

import static org.searchmasterV2.controllers.UniversityPromptControllerPrimary.universitySet;
public class UniversityPromptControllerSecondary {
    @FXML
    private TextField universityTextField;
    @FXML
    private MenuButton universityMenuButton;

    @FXML
    public void universityNameClicked(ActionEvent event) throws IOException {
        try {
            HashMap<String, String> map = Functionality.getUniversityID(universityTextField.getText());
            stage.setScene(new Scene(App.loadFXML("searchmasterDropdown")));
            universitySet = map.keySet();
        } catch (IndexOutOfBoundsException e) {
            stage.setScene(new Scene(App.loadFXML("searchmasterError")));
        }
    }
    @FXML
    public void universityMenuButtonClicked(MouseEvent event) throws IOException {
        universityMenuButton.getItems().clear();
        universityMenuButton.setDisable(false);
        for(String str : universitySet) {
            MenuItem menuItem = new MenuItem(str);
            universityMenuButton.getItems().add(menuItem);
        }
    }
}
