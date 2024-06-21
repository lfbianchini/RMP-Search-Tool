// Controller for DataDashP3 Scene

package org.searchmasterV2.controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.util.Duration;
import org.searchmasterV2.Loader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class DataDashboardP3Controller implements Initializable {

    @FXML
    private PieChart pieChartOne;

    // Initializes the controller after FXML file is loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Fetches average professor sentiments
        long[] sentiments = Loader.getAverageProfessorSentiments();

        // Sets title for the pie chart
        pieChartOne.setTitle("SENTIMENT TOWARDS PROFESSOR (%)");

        // Adds data to the pie chart
        PieChart.Data veryNegative = new PieChart.Data("Very Negative", sentiments[0]);
        PieChart.Data negative = new PieChart.Data("Negative", sentiments[1]);
        PieChart.Data neutral = new PieChart.Data("Neutral", sentiments[2]);
        PieChart.Data positive = new PieChart.Data("Positive", sentiments[3]);
        PieChart.Data veryPositive = new PieChart.Data("Very Positive", sentiments[4]);

        pieChartOne.getData().addAll(veryNegative, negative, neutral, positive, veryPositive);

        // Customizes pie chart properties
        pieChartOne.setLegendVisible(true);
        pieChartOne.setLabelsVisible(true);
        pieChartOne.setLabelLineLength(10);

        // Adds percentage labels to pie chart slices
        for (PieChart.Data data : pieChartOne.getData()) {
            data.setName(data.getName() + " (" + String.format("%.1f%%", data.getPieValue()) + ")");
        }

        // Fades in the pie chart using a fade transition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), pieChartOne);
        pieChartOne.setOpacity(0);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    // Action method for switching to DataDashP2 scene
    @FXML
    public void pageTwoClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("DataDashP2")));
    }

    // Action method for switching to DataDashP4 scene
    @FXML
    public void pageFourClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("DataDashP4")));
    }
}