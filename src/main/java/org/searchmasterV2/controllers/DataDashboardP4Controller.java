package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class DataDashboardP4Controller implements Initializable {

    @FXML
    private LineChart<String, String> lineChartOne;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void pageThreeClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("searchmasterDataDashP3")));
    }

    @FXML
    public void pageFiveClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("searchmasterDataDashP5")));
    }

}
