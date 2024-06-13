package org.searchmasterV2.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingScreenController implements Initializable {

    @FXML
    private ProgressIndicator progressIndicator;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization code if needed
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
    }
}
