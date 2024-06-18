package org.searchmasterV2;

import java.io.IOException;
import java.util.*;

public class Loader {

    private String professorRating;
    private String wouldTakeAgain;
    private String levelOfDifficulty;
    private ArrayList<String> ratings = new ArrayList<>();
    private String averageProfessorGrade;
    private long[] averageProfessorSentiments;
    private long[] getSentiments;

    public Loader(String professorID) throws IOException {
        Functionality.initializeDriver(professorID);
        this.professorRating = Functionality.getProfessorRating(professorID);
        this.wouldTakeAgain = Functionality.getProfessorWouldTakeAgain(professorID);
        this.levelOfDifficulty = Functionality.getProfessorDifficulty(professorID);
        this.ratings = Functionality.getProfessorReviews(professorID);
        this.averageProfessorGrade = Functionality.averageProfGrade(professorID);
        this.averageProfessorSentiments = Functionality.getAverageProfSentiments(professorID);
        //this.getSentiments = Functionality.getSentiments(professorID);
    }

    public String getProfessorRating() {
        return professorRating;
    }

    public String getProfessorWouldTakeAgain() {
        return wouldTakeAgain;
    }

    public String getProfessorLevelOfDifficulty() {
        return levelOfDifficulty;
    }

    public ArrayList<String> getRatings() {
        return ratings;
    }

    public String getAverageProfessorGrade() {
        return averageProfessorGrade;
    }

    public long[] getAverageProfessorSentiments() {
        return averageProfessorSentiments;
    }

    public long[] getGetSentiments() {
        return getSentiments;
    }

}