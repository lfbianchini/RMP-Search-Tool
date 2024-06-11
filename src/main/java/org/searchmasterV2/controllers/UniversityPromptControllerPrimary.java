package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import org.searchmasterV2.App;
import org.searchmasterV2.Functionality;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import static org.searchmasterV2.App.stage;


public class UniversityPromptControllerPrimary {

    @FXML
    private TextField universityTextField;
    public static Set<String> universitySet;
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
    public void universityButtonErrorClicked(ActionEvent event) throws IOException {
        try {
            HashMap<String, String> map = Functionality.getUniversityID(universityTextField.getText());
            stage.setScene(new Scene(App.loadFXML("searchmasterDropdown")));
            Set<String> set = map.keySet();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
