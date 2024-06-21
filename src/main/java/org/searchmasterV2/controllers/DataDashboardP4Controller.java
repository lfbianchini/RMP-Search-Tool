// Controller for DataDashP4 Scene

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

    // Initializes the controller after FXML file is loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Retrieves consolidated sentiments from Loader
        XYChart.Series<String, Double> series1 = new XYChart.Series<>();
        Map<List<Long>, Double> map = Loader.getConsolidatedSentiments();

        // Iterates over sentiment data to populate the scatter chart
        for (Map.Entry<List<Long>, Double> entry : map.entrySet()) {
            // Skip null or zero values
            if (entry.getValue() == null || entry.getValue() == 0) {
                continue;
            }

            // Finds the max sentiment value and its index
            Long max = Collections.max(entry.getKey());
            String indexOfMax = String.valueOf(entry.getKey().indexOf(max)); // Convert to String

            // Adds data point to the series
            series1.getData().add(new XYChart.Data<>(indexOfMax, entry.getValue()));
        }

        // Customizes X axis properties
        CategoryAxis xAxis = (CategoryAxis) scatterChart.getXAxis();
        xAxis.setLabel("SENTIMENT (0 = NEGATIVE, 1 = NEUTRAL, 2 = POSITIVE)");
        xAxis.setTickLabelFont(Font.font("Arial", 14));
        xAxis.setTickLabelFill(Color.DARKGREY);

        // Customizes Y axis properties
        Axis<Double> yAxis = scatterChart.getYAxis();
        yAxis.setLabel("GRADE (4.0 SCALE)");
        yAxis.setTickLabelFont(Font.font("Arial", 14));
        yAxis.setTickLabelFill(Color.DARKGREY);

        // Adds series to the scatter chart
        scatterChart.getData().add(series1);
    }

    // Action method for switching to DataDashP3 scene
    @FXML
    public void pageThreeClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("DataDashP3")));
    }

    // Action method for switching to DataDashP5 scene
    @FXML
    public void pageFiveClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("DataDashP5")));
    }
}