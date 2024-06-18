package org.searchmasterV2;

import java.io.IOException;
import java.util.*;

public class Loader {

    private final String professorRating;
    private final String wouldTakeAgain;
    private final String levelOfDifficulty;
    private final ArrayList<Review> reviewList;
    private final String averageProfessorGrade;
    private final long[] averageProfessorSentiments;
    private long[] getSentiments;

    public Loader(String professorID) throws IOException {
        Professor.initializeDriver(professorID);
        this.professorRating = Professor.getProfessorRating(professorID);
        this.wouldTakeAgain = Professor.getProfessorWouldTakeAgain(professorID);
        this.levelOfDifficulty = Professor.getProfessorDifficulty(professorID);
        this.reviewList = Professor.getProfessorReviews(professorID);
        this.averageProfessorGrade = Professor.averageProfGrade(professorID);
        this.averageProfessorSentiments = Professor.getAverageProfSentiments(professorID);
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

    public ArrayList<Review> getRatings() {
        return reviewList;
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