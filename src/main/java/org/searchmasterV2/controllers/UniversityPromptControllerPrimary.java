package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.searchmasterV2.App;
import org.searchmasterV2.Functionality;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import static javafx.application.Application.STYLESHEET_CASPIAN;
import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;


public class UniversityPromptControllerPrimary {
    @FXML
    private TextField universityTextField;
    @FXML
    private MenuButton universityMenuButton;
    @FXML
    private Text universitySelectText;

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
    public void universityMenuButtonClicked(MouseEvent event) throws IOException {
        universityMenuButton.getItems().clear();
        for(String str : universitySet) {
            MenuItem menuItem = new MenuItem(str);
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                }
            });
            universityMenuButton.getItems().add(menuItem);
        }
        universityMenuButton.show();
    }


}