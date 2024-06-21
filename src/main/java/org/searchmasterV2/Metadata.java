package org.searchmasterV2;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Metadata {
    private boolean onlineCourse;
    private boolean takeAgain;
    private boolean forCredit;
    private boolean useTextbooks;
    private boolean mandatory;
    private String grade;

    // Constructor to initialize metadata from HTML elements
    public Metadata(Elements elements) {
        // Iterate through each HTML element to extract relevant metadata
        for (Element element : elements) {
            String text = element.text(); // Get the text content of the element

            // Parse different attributes based on text content
            if (text.contains("For Credit: Yes")) {
                this.forCredit = true;
            } else if (text.contains("For Credit: No")) {
                this.forCredit = false;
            }

            if (text.contains("Attendance: Mandatory")) {
                this.mandatory = true;
            } else if (text.contains("Attendance: Not Mandatory")) {
                this.mandatory = false;
            }

            if (text.contains("Would Take Again: Yes")) {
                this.takeAgain = true;
            } else if (text.contains("Would Take Again: No")) {
                this.takeAgain = false;
            }

            if (text.contains("Textbook: Yes")) {
                this.useTextbooks = true;
            } else if (text.contains("Textbook: No")) {
                this.useTextbooks = false;
            }

            if (text.contains("Grade: ")) {
                this.grade = text.substring(7).trim(); // Extract grade, remove leading text
            } else if (text.contains("Not sure yet")) {
                this.grade = "Not sure yet";
            } else if (text.contains("Audit/No Grade")) {
                this.grade = "Audit/No Grade";
            } else if (text.contains("Drop/Withdrawal")) {
                this.grade = "Drop/Withdrawal";
            } else if (text.contains("Incomplete")) {
                this.grade = "Incomplete";
            } else if (text.contains("Rather not say")) {
                this.grade = "Rather not say";
            }

            if (text.contains("Online Class: Yes")) {
                this.onlineCourse = true;
            } else if (text.contains("Online Class: No")) {
                this.onlineCourse = false;
            }
        }
    }

    // Getters for metadata attributes
    public boolean isOnlineCourse() {
        return onlineCourse;
    }

    public boolean isTakeAgain() {
        return takeAgain;
    }

    public boolean isForCredit() {
        return forCredit;
    }

    public boolean isUseTextbooks() {
        return useTextbooks;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public String getGrade() {
        return grade;
    }

    // Method to convert grade to a numerical representation
    public double getGradeAsDouble() {
        if (this.grade == null) {
            return 0.0; // Return 0.0 if grade is not set
        }

        // Convert grade string to a numerical representation
        switch (this.grade.toUpperCase()) {
            case "A+":
            case "A":
                return 4.0;
            case "A-":
                return 3.7;
            case "B+":
                return 3.3;
            case "B":
                return 3.0;
            case "B-":
                return 2.7;
            case "C+":
                return 2.3;
            case "C":
                return 2.0;
            case "C-":
                return 1.7;
            case "D+":
                return 1.3;
            case "D":
                return 1.0;
            case "D-":
                return 0.7;
            case "F":
                return 0.0;
            default:
                return 0.0; // Return 0.0 if grade is unrecognized
        }
    }
}