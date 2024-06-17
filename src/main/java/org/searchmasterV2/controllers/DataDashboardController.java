package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;
import org.searchmasterV2.App;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.searchmasterV2.controllers.DataTransitionController.data;

public class DataDashboardController {

    @FXML
    private Pagination pagination;

    private final Map<Integer, Node> pageCache = new HashMap<>();

    @FXML
    public void initialize() {
        pagination.setPageCount(5);
    //    pagination.setPageFactory(this::createPage);
    }

    @FXML
    public void backButton2Clicked(ActionEvent event) throws IOException {
        App.setRoot("searchmasterProfessorDropdown");

        // Data testing using this button for demonstration
        System.out.println("Professor Rating: " + data.getProfessorRating());

        data.getRatings().forEach(rating -> System.out.println("- " + rating));

        System.out.println("Average Professor Grade: " + data.getAverageProfessorGrade());

        System.out.println("Average Professor Sentiments: " + Arrays.toString(data.getAverageProfessorSentiments()));

        System.out.println("Professor Sentiments: " + Arrays.toString(data.getGetSentiments()));
    }
}