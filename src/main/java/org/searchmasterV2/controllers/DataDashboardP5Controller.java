package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;

import java.io.IOException;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class DataDashboardP5Controller {

    @FXML
    public void pageFourClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("searchmasterDataDashP4")));
    }
}
