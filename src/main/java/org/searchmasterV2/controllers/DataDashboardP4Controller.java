package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.searchmasterV2.Loader;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class DataDashboardP4Controller implements Initializable {

    @FXML
    private ScatterChart<String, Double> scatterChart;

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

        CategoryAxis xAxis = (CategoryAxis) scatterChart.getXAxis();
        xAxis.setLabel("SENTIMENT (0 = NEGATIVE, 1 = NEUTRAL, 2 = POSITIVE)");
        xAxis.setTickLabelFont(Font.font("Arial", 14));
        xAxis.setTickLabelFill(Color.DARKGREY);

        Axis<Double> yAxis = scatterChart.getYAxis();
        yAxis.setLabel("GRADE (4.0 SCALE)");
        yAxis.setTickLabelFont(Font.font("Arial", 14));
        yAxis.setTickLabelFill(Color.DARKGREY);

        scatterChart.getData().add(series1);
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