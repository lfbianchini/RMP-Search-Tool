package org.searchmasterV2;

import java.util.ArrayList;

public class Grade {
    private final String grade;
    private static ArrayList<String> grades = new ArrayList<>();

    public Grade(String grade) {
        grades.add(grade);
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public static String averageGrade() {
        double avgWeight = getAvgWeight();
        return getString(avgWeight);
    }

    static String getString(double avgWeight) {
        String avgWeightLetter = "";
        if (avgWeight >= 3.85 && avgWeight <= 4.0) {
            avgWeightLetter = "A";
        } else if (avgWeight >= 3.50 && avgWeight < 3.85) {
            avgWeightLetter = "A-";
        } else if (avgWeight >= 3.15 && avgWeight < 3.50) {
            avgWeightLetter = "B+";
        } else if (avgWeight >= 2.85 && avgWeight < 3.15) {
            avgWeightLetter = "B";
        } else if (avgWeight >= 2.50 && avgWeight < 2.85) {
            avgWeightLetter = "B-";
        } else if (avgWeight >= 2.15 && avgWeight < 2.50) {
            avgWeightLetter = "C+";
        } else if (avgWeight >= 1.85 && avgWeight < 2.15) {
            avgWeightLetter = "C";
        } else if (avgWeight >= 1.50 && avgWeight < 1.85) {
            avgWeightLetter = "C-";
        } else if (avgWeight >= 1.15 && avgWeight < 1.50) {
            avgWeightLetter = "D+";
        } else if (avgWeight >= 0.85 && avgWeight < 1.15) {
            avgWeightLetter = "D";
        } else if (avgWeight >= 0.70 && avgWeight < 0.85) {
            avgWeightLetter = "D-";
        } else if (avgWeight >= 0.0 && avgWeight < 0.70) {
            avgWeightLetter = "F";
        }
        return avgWeightLetter;
    }

    private static double getAvgWeight() {
        double avgWeight = 0;
        for (String grade: grades) {
            switch (grade) {
                case "A+":
                case "A":
                    avgWeight += 4;
                    break;
                case "A-":
                    avgWeight += 3.7;
                    break;
                case "B+":
                    avgWeight += 3.3;
                    break;
                case "B":
                    avgWeight += 3;
                    break;
                case "B-":
                    avgWeight += 2.7;
                    break;
                case "C+":
                    avgWeight += 2.3;
                    break;
                case "C":
                    avgWeight += 2.0;
                    break;
                case "C-":
                    avgWeight += 1.7;
                    break;
                case "D+":
                    avgWeight += 1.3;
                    break;
                case "D":
                    avgWeight += 1.0;
                    break;
                case "D-":
                    avgWeight += 0.7;
                    break;
                case "F":
                    avgWeight += 0.0;
                    break;
            }
        }
        avgWeight = avgWeight / grades.size();
        return avgWeight;
    }
}
