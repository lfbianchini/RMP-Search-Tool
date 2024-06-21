package org.searchmasterV2.controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        long[] sentiments = Loader.getAverageProfessorSentiments();

        pieChartOne.setTitle("SENTIMENT TOWARDS PROFESSOR (%)");

        PieChart.Data veryNegative = new PieChart.Data("Very Negative", sentiments[0]);
        PieChart.Data negative = new PieChart.Data("Negative", sentiments[1]);
        PieChart.Data neutral = new PieChart.Data("Neutral", sentiments[2]);
        PieChart.Data positive = new PieChart.Data("Positive", sentiments[3]);
        PieChart.Data veryPositive = new PieChart.Data("Very Positive", sentiments[4]);

        pieChartOne.getData().addAll(veryNegative, negative, neutral, positive, veryPositive);

        pieChartOne.setLegendVisible(true);
        pieChartOne.setLabelsVisible(true);
        pieChartOne.setLabelLineLength(10);

        for (PieChart.Data data : pieChartOne.getData()) {
            data.setName(data.getName() + " (" + String.format("%.1f%%", data.getPieValue()) + ")");
        }

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), pieChartOne);
        pieChartOne.setOpacity(0);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @FXML
    public void pageTwoClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("DataDashP2")));
    }

    @FXML
    public void pageFourClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("DataDashP4")));
    }
}