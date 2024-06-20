package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import org.searchmasterV2.Loader;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class DataDashboardP4Controller implements Initializable {

    @FXML
    private ScatterChart<String, Double> scatterChartOne;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Double> series1 = new XYChart.Series<>();
        Map<List<Long>, Double> map = Loader.getConsolidatedSentiments();
        for (Map.Entry<List<Long>, Double> entry : map.entrySet()) {
            if (entry.getValue() == null || entry.getValue() == 0) {
                continue;
            }
            Long max = Collections.max(entry.getKey());
            String indexOfMax = String.valueOf(entry.getKey().indexOf(max)); // Convert to String
            series1.getData().add(new XYChart.Data<>(indexOfMax, entry.getValue()));
        }
        scatterChartOne.getData().add(series1);
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