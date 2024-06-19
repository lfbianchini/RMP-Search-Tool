package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import org.searchmasterV2.Loader;
import org.searchmasterV2.Review;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;
import static org.searchmasterV2.Professor.sentimentList;

public class DataDashboardP4Controller implements Initializable {

    @FXML
    private LineChart<String, String> lineChartOne;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Review> sentiments = Loader.getReviewList();
        lineChartOne.getXAxis().setLabel("MONTHS (#)");
        lineChartOne.getYAxis().setLabel("SENTIMENT");
        XYChart.Series series = new XYChart.Series();
////        for(Review review : sentimentList) {
////            series.getData().add(new XYChart.Data("") {
////            });
////        }
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
