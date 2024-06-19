package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.searchmasterV2.Loader;
import org.searchmasterV2.Professor;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class barChartOneController implements Initializable {
    @FXML BarChart<String, Number> barChartOne;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Long> sentiments = Loader.getConsolidatedAvgProfessorSentiments();
        barChartOne.setTitle("Frequency of Sentiment Classes");
        barChartOne.getXAxis().setLabel("Sentiments");
        barChartOne.getYAxis().setLabel("Frequency");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Sentiment Classes");
        series1.getData().add(new XYChart.Data<>("Negative", sentiments.get(0)));
        series1.getData().add(new XYChart.Data<>("Neutral", sentiments.get(1)));
        series1.getData().add(new XYChart.Data<>("Positive", sentiments.get(2)));

        barChartOne.getData().addAll(series1);
    }

    @FXML
    public void pageOneClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0");
        stage.setScene(new Scene(loadFXML("searchmasterDataDash")));
    }

    @FXML
    public void pageThreeClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("searchmasterDataDashP3")));
    }
}
