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

    public Metadata(Elements elements) {
        for (Element element : elements) {
            if (element.text().contains("For Credit: Yes")) {
                this.forCredit = true;
            }
            else if (element.text().contains("For Credit: No")) {
                this.forCredit = false;
            }
            if (element.text().contains("Attendance: Mandatory")) {
                this.mandatory = true;
            }
            else if (element.text().contains("Attendance: Not Mandatory")) {
                this.mandatory = false;
            }
            if (element.text().contains("Would Take Again: Yes")) {
                this.takeAgain = true;
            }
            else if (element.text().contains("Would Take Again: No")) {
                this.takeAgain = false;
            }
            if (element.text().contains("Textbook: Yes")) {
                this.useTextbooks = true;
            }
            else if (element.text().contains("Textbook: No")) {
                this.useTextbooks = false;
            }
            if (element.text().contains("Grade: ")) {
                this.grade = element.text().substring(7);
            }
            else if (element.text().contains("Not sure yet")) {
                this.grade = "Not sure yet";
            }
            else if (element.text().contains("Audit/No Grade")) {
                this.grade = "Audit/No Grade";
            }
            else if (element.text().contains("Drop/Withdrawal")) {
                this.grade = "Drop/Withdrawal";
            }
            else if (element.text().contains("Incomplete")) {
                this.grade = "Incomplete";
            }
            else if (element.text().contains("Rather not say")) {
                this.grade = "Rather not say";
            }
            if (element.text().contains("Online Class: Yes")) {
                this.onlineCourse = true;
            }
            else if (element.text().contains("Online Class: No")) {
                this.onlineCourse = false;
            }
        }
    }

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
}