package org.searchmasterV2;

import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Review {
    private String text;
    private LocalDate date;
    private Metadata metadata;
    private List<Long> sentiment;
    private List<Long> consolidatedSentiment;

    // Constructor to initialize review attributes from HTML element
    public Review(Element review) {
        // Check if review element is not an advertisement
        if (!Objects.requireNonNull(review.selectFirst("div:nth-child(1)")).id().equals("ad-controller")) {
            this.text = Objects.requireNonNull(review.selectFirst("div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(3)")).text();
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

    public void setSentiment(List<Long> sentiment) {
        this.sentiment = sentiment;
        this.consolidatedSentiment = Professor.consolidateReview(sentiment);
    }

    public List<Long> getConsolidatedSentiment() {
        return this.consolidatedSentiment;
    }

    // Helper method to parse date text into LocalDate
    private static LocalDate dateHelper(String datePlainText) {
        String[] parts = datePlainText.split("\\s+");
        String month = parts[0].substring(0, 3).toLowerCase();
        int monthInt;

        switch (month) {
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
            default:
                monthInt = 1;
                break;
        }

        String day = parts[1].replaceAll("[^\\d]", "");
        String year = parts[2];

        return LocalDate.of(Integer.parseInt(year), monthInt, Integer.parseInt(day));
    }
}
