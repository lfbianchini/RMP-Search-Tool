package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;
import static org.searchmasterV2.controllers.DataTransitionController.data;

public class DataDashboardController {

    @FXML
    private Button backButton2;

    @FXML
    public void backButton2Clicked(ActionEvent event) throws IOException {
        stage.setScene(new Scene(loadFXML("searchmasterProfessorDropdown")));

        //data testing using this button bc im lazy
        System.out.println("Professor Rating: " + data.getProfessorRating());

        ArrayList<String> ratings = data.getRatings();
        System.out.println("Ratings:");
        for (String rating : ratings) {
            System.out.println("- " + rating);
        }

        System.out.println("Average Professor Grade: " + data.getAverageProfessorGrade());

        long[] averageSentiments = data.getAverageProfessorSentiments();
        System.out.println("Average Professor Sentiments:");
        System.out.println(Arrays.toString(averageSentiments));

        long[] sentiments = data.getGetSentiments();
        System.out.println("Professor Sentiments:");
        System.out.println(Arrays.toString(sentiments));

    }
}
