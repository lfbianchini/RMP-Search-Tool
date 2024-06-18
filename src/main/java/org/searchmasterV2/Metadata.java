package org.searchmasterV2;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Metadata {
    private String courseCode;
    private boolean onlineCourse;
    private int professorRating;
    private int professorDifficulty;
    private boolean takeAgain;
    private boolean forCredit;
    private boolean useTextbooks;
    private boolean attendance;
    private Grade grade;

    public Metadata(Elements elements) {

    }
}
