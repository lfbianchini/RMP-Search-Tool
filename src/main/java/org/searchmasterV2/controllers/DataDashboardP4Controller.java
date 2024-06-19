package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;
import static org.searchmasterV2.Professor.sentimentList;

public class DataDashboardP4Controller implements Initializable {

    @FXML
    private ScatterChart<Number, String> scatterChartOne;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<Number, String> series1 = new XYChart.Series<>();
        Map<List<Long>, String> map = sentimentList;
        for(Map.Entry<List<Long>, String> entry : map.entrySet()){
            if(entry.getValue() == null || entry.getValue().length() > 2) {
                continue;
            }
            Long max = Collections.max(entry.getKey());
            int indexOfMax = entry.getKey().indexOf(max);
            series1.getData().add(new XYChart.Data<>(indexOfMax, entry.getValue()));
        }
        scatterChartOne.getData().addAll(series1);
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