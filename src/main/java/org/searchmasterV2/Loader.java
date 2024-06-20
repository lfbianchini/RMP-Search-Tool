package org.searchmasterV2;

import java.io.IOException;
import java.util.*;

public class Loader {

    private final String professorRating;
    private final String wouldTakeAgain;
    private final String levelOfDifficulty;
    private static ArrayList<Review> reviewList = null;
    private final String averageProfessorGrade;
    private static long[] averageProfessorSentiments = new long[0];
    private static Map<List<Long>, String> consolidatedSentiments = new HashMap<>();
    private static List<Long> consolidatedAvgProfessorSentiments = List.of();
    private long[] getSentiments;

    public Loader(String professorID) throws IOException {
        Professor.initializeDriver(professorID);
        this.professorRating = Professor.getProfessorRating(professorID);
        this.wouldTakeAgain = Professor.getProfessorWouldTakeAgain(professorID);
        this.levelOfDifficulty = Professor.getProfessorDifficulty(professorID);
        Professor.getProfessorReviews(professorID);
        reviewList = Professor.reviewList;
        this.averageProfessorGrade = Professor.averageProfGrade(professorID);
        averageProfessorSentiments = Professor.getAverageProfessorSentiments(professorID);
        consolidatedAvgProfessorSentiments = Professor.getSentimentListFrequency();
        consolidatedSentiments = Professor.consolidateSentimentList();
    }

    public static List<Long> getConsolidatedAvgProfessorSentiments() {
        return consolidatedAvgProfessorSentiments;
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

    public static ArrayList<Review> getRatings() {
        return reviewList;
    }

    public String getAverageProfessorGrade() {
        return averageProfessorGrade;
    }

    public static long[] getAverageProfessorSentiments() {
        return averageProfessorSentiments;
    }

    public long[] getGetSentiments() {
        return getSentiments;
    }

    public static Map<List<Long>, String> getConsolidatedSentiments() { return consolidatedSentiments; }

}