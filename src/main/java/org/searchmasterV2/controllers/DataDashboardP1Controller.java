// Controller for DataDashP1 Scene

package org.searchmasterV2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.searchmasterV2.App.loadFXML;
import static org.searchmasterV2.App.stage;
import static org.searchmasterV2.controllers.DataTransitionController.data;
import static org.searchmasterV2.controllers.ProfessorPromptControllerPrimary.professorName;
import static org.searchmasterV2.controllers.ProfessorPromptControllerPrimary.professorID;

public class DataDashboardP1Controller {

    // Injected FXML elements
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
    private Hyperlink professorLink = new Hyperlink();

    // Initializes the controller after FXML file is loaded
    public void initialize() throws IOException {
        // Populate UI elements if stage title is "SMV1.0"
        if (stage.getTitle().equals("SMV1.0")) {
            professorNameText.setText(professorName.toUpperCase());
            ratingText.setText(data.getProfessorRating() + "/5");
            wouldTakeAgainText.setText(data.getProfessorWouldTakeAgain());
            averageGradeText.setText(data.getAverageProfessorGrade());
            difficultyText.setText(data.getProfessorLevelOfDifficulty() + "/5");
        }

        // Action for clicking professorLink to open RateMyProfessors link
        professorLink.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.ratemyprofessors.com/professor/" + professorID));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    // Action method for switching to DataDashP2 scene
    @FXML
    public void pageTwoClicked(ActionEvent event) throws IOException {
        stage.setTitle("SMV1.0");
        stage.setScene(new Scene(loadFXML("DataDashP2")));
    }
}