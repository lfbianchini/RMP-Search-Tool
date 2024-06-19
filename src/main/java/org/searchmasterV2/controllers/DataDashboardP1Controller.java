package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import java.io.IOException;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;
import static org.searchmasterV2.controllers.DataTransitionController.data;
import static org.searchmasterV2.controllers.ProfessorPromptControllerPrimary.professorName;

public class DataDashboardP1Controller {

    @FXML
    private Text professorNameText;

    @FXML
    private Text ratingText;

    @FXML
    private Text wouldTakeAgainText;

    @FXML
    private Text averageGradeText;

    @FXML
    private Text difficultyText;

    @FXML
    public void initialize() throws IOException {
        if (stage.getTitle().equals("SMV1.0")) {
            professorNameText.setText(professorName.toUpperCase());
            ratingText.setText(data.getProfessorRating());
            wouldTakeAgainText.setText(data.getProfessorWouldTakeAgain());
            averageGradeText.setText(data.getAverageProfessorGrade());
            difficultyText.setText(data.getProfessorLevelOfDifficulty());
        }
    }

    @FXML
    public void pageTwoClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0");
        stage.setScene(new Scene(loadFXML("searchmasterDataDashP2")));

    }

    @FXML
    public void backButton2Clicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0");
        stage.setScene(new Scene(loadFXML("searchmasterProfessorDropdown")));
    }

    @FXML
    public void resetButtonClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0");
        stage.setScene(new Scene(loadFXML("searchmaster")));
    }
}