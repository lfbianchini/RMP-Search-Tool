package org.searchmasterV2.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.searchmasterV2.Loader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class DataDashboardP3Controller implements Initializable {
    @FXML
    private BarChart<String, Number> barChartTwo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        long[] sentiments = Loader.getAverageProfessorSentiments();

        CategoryAxis xAxis = (CategoryAxis) barChartTwo.getXAxis();
        xAxis.setLabel("SENTIMENT");
        xAxis.setTickLabelFont(Font.font("Arial", 14));
        xAxis.setTickLabelFill(Color.DARKGREY);

        NumberAxis yAxis = (NumberAxis) barChartTwo.getYAxis();
        yAxis.setLabel("% OF SENTIMENT");
        yAxis.setTickLabelFont(Font.font("Arial", 14));
        yAxis.setTickLabelFill(Color.DARKGREY);
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);
        yAxis.setTickUnit(10);

        barChartTwo.setTitle("SENTIMENT TOWARDS PROFESSOR (%)");
        barChartTwo.setLegendVisible(false);

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("SENTIMENT");

        XYChart.Data<String, Number> veryNegative = new XYChart.Data<>("Very Negative", sentiments[0]);
        XYChart.Data<String, Number> negative = new XYChart.Data<>("Negative", sentiments[1]);
        XYChart.Data<String, Number> neutral = new XYChart.Data<>("Neutral", sentiments[2]);
        XYChart.Data<String, Number> positive = new XYChart.Data<>("Positive", sentiments[3]);
        XYChart.Data<String, Number> veryPositive = new XYChart.Data<>("Very Positive", sentiments[4]);

        series1.getData().add(veryNegative);
        series1.getData().add(negative);
        series1.getData().add(neutral);
        series1.getData().add(positive);
        series1.getData().add(veryPositive);

        barChartTwo.getData().addAll(series1);

        for (XYChart.Data<String, Number> data : series1.getData()) {
            StackPane stackPane = (StackPane) data.getNode();
            Label label = new Label(data.getYValue().toString());
            label.setFont(Font.font("Arial", 14));
            label.setTextFill(Color.WHITE);
            stackPane.getChildren().add(label);

            data.getNode().setScaleY(0);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(data.getNode().scaleYProperty(), 1);
            KeyFrame kf = new KeyFrame(Duration.millis(800), kv);
            timeline.getKeyFrames().add(kf);
            timeline.play();
        }
    }

    @FXML
    public void pageTwoClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("searchmasterDataDashP2")));
    }
}
