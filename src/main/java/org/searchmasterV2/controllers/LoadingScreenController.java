package org.searchmasterV2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;

public class LoadingScreenController {

    @FXML
    private ProgressIndicator progressIndicator;

    public void initialize() {
        // Initialization code if needed
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
    }
}
