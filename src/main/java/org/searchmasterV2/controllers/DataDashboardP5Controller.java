package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import org.searchmasterV2.Loader;
import org.searchmasterV2.Review;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class DataDashboardP5Controller implements Initializable {

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Review> reviews = Loader.getRatings();
        reviews.sort(Comparator.comparing(Review::getDate));


    }

    @FXML
    public void pageFourClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("searchmasterDataDashP4")));
    }
}