package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import org.searchmasterV2.App;

import java.io.IOException;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class DataDashboardController {

    @FXML
    private Button backButton2;

    @FXML
    public void backButton2Clicked(ActionEvent event) throws IOException {
        stage.setScene(new Scene(loadFXML("searchmasterProfessorDropdown")));
    }
}
