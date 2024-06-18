package org.searchmasterV2;

import org.jsoup.nodes.Element;
import java.time.LocalDate;
import java.util.Objects;

public class Review {
    private String text;
    private LocalDate date;
    private Metadata metadata;

    public Review(Element review) {
        if(!Objects.requireNonNull(review.selectFirst("div:nth-child(1)")).id().equals("ad-controller")) {
            this.text = review.text();
            this.date = dateHelper(Objects.requireNonNull(review.select("div:nth-child(1) > div:nth-child(1) > " +
                    "div:nth-child(3) > div:nth-child(1) > div:nth-child(2)").text()));
            this.metadata = new Metadata(Objects.requireNonNull(review.selectFirst("div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2)")).children());
        }
    }

    public String getText() {
        return this.text;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    private static LocalDate dateHelper(String datePlainText) {
        String month = datePlainText.substring(0,3).toLowerCase();
        int monthInt = 0;
        switch(month) {
            case "jan":
                monthInt = 1;
                break;
            case "feb":
                monthInt = 2;
                break;
            case "mar":
                monthInt = 3;
                break;
            case "apr":
                monthInt = 4;
                break;
            case "may":
                monthInt = 5;
                break;
            case "jun":
                monthInt = 6;
                break;
            case "jul":
                monthInt = 7;
                break;
            case "aug":
                monthInt = 8;
                break;
            case "sep":
                monthInt = 9;
                break;
            case "oct":
                monthInt = 10;
                break;
            case "nov":
                monthInt = 11;
                break;
            case "dec":
                monthInt = 12;
                break;
        }
        String day;
        try {
            day = datePlainText.substring(4, datePlainText.indexOf("t"));
        } catch(StringIndexOutOfBoundsException e) {
            day = datePlainText.substring(4, datePlainText.indexOf("n"));
        }
        String year = datePlainText.substring(datePlainText.indexOf(',')+2);
        LocalDate date = LocalDate.of(Integer.parseInt(year), monthInt, Integer.parseInt(day));
        return date;
    }
}