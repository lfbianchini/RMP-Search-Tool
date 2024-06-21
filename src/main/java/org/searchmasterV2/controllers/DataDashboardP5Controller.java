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
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;

public class DataDashboardP5Controller implements Initializable {

    @FXML
    private LineChart<String, Integer> lineChart;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
        ArrayList<Review> reviews = Loader.getRatings();
        reviews.sort(Comparator.comparing(Review::getDate));
        List<String> reviewSentiments = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Review r : reviews) {
            String date = r.getDate().format(formatter);
            List<Long> sentiments = r.getConsolidatedSentiment();
            if (!sentiments.isEmpty()) {
                Long sentiment = Collections.max(sentiments);
                int sentimentStr = sentiments.indexOf(sentiment);
//                reviewSentiments.add(sentimentStr);
                series1.getData().add(new XYChart.Data<>(date, sentimentStr));
            }
        }
        lineChart.getData().add(series1);
    }

    @FXML
    public void pageFourClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0 ");
        stage.setScene(new Scene(loadFXML("searchmasterDataDashP4")));
    }
}