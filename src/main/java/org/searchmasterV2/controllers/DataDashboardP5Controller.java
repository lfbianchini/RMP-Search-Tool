// Controller for DataDashP5 Scene

package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class DataDashboardP5Controller implements Initializable {

    // Initializes the controller after FXML file is loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // Action method for switching to DataDashP4 scene
    @FXML
    public void pageFourClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("DataDashP4")));
    }
}