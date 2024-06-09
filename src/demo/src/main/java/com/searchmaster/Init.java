package com.searchmaster;

import java.io.IOException;
import java.util.*;
import java.util.Arrays;

public class Init {
    public static void main(String[] args) throws IOException {
        /* App.initializeDriver("517854");
        System.out.println(App.getProfessorId("1600", "karen bouwer"));
        System.out.println(App.getProfessorRating("517854"));
        System.out.println(App.getProfessorReviews("256109"));
        System.out.println(Arrays.toString(App.getMetadata("517854").toArray()));
        System.out.println(Arrays.toString(App.getGrades(App.getMetadata("256109")).toArray()));
        ArrayList<String> grades = new ArrayList<>(Arrays.asList(
            "A+", "A", "A-", "C", "C+", "B-", "D", "D+", "F", "B",
            "B+", "A-", "A+", "A+", "B+", "A+", "A", "A+", "B+", "B",
            "D", "D-", "A+", "A+", "C-", "B-", "B-", "A-", "A+", "C+"
        ));
        System.out.println(App.averageGrade(grades));
        System.out.println(App.averageProfGrade("2231495")); */
        long startTime = System.currentTimeMillis();
        System.out.println(Arrays.toString(App.getAverageProfSentiments("517854")));
        long endTime = System.currentTimeMillis();
        System.out.println("it took " + (endTime - startTime) + "ms");
    }
}
