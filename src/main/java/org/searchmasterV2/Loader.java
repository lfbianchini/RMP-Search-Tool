package org.searchmasterV2;

import java.io.IOException;
import java.util.*;

public class Loader {

    private String professorRating;
    private ArrayList<String> ratings = new ArrayList<>();
    private String averageProfessorGrade;
    private long[] averageProfessorSentiments;
    private long[] getSentiments;

    public Loader(String professorID) throws IOException {
        this.professorRating = Functionality.getProfessorRating(professorID);
        this.ratings = Functionality.getProfessorReviews(professorID);
        this.averageProfessorGrade = Functionality.averageProfGrade(professorID);
        this.averageProfessorSentiments = Functionality.getAverageProfSentiments(professorID);
        this.getSentiments = Functionality.getSentiments(professorID);
    }

    public String getProfessorRating() {
        return professorRating;
    }

    public void setProfessorRating(String professorRating) {
        this.professorRating = professorRating;
    }

    public ArrayList<String> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<String> ratings) {
        this.ratings = ratings;
    }

    public String getAverageProfessorGrade() {
        return averageProfessorGrade;
    }

    public void setAverageProfessorGrade(String averageProfessorGrade) {
        this.averageProfessorGrade = averageProfessorGrade;
    }

    public long[] getAverageProfessorSentiments() {
        return averageProfessorSentiments;
    }

    public void setAverageProfessorSentiments(long[] averageProfessorSentiments) {
        this.averageProfessorSentiments = averageProfessorSentiments;
    }

    public long[] getGetSentiments() {
        return getSentiments;
    }

    public void setGetSentiments(long[] getSentiments) {
        this.getSentiments = getSentiments;
    }

}