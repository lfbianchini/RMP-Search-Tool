package org.searchmasterV2;

import org.jsoup.nodes.Element;
import java.time.LocalDate;
import java.util.Objects;

public class Review {
    private String text;
    private Date date;
    private Metadata metadata;

    public Review(Element review) {
        if(!Objects.requireNonNull(review.selectFirst("div:nth-child(1)")).id().equals("ad-controller")) {
            this.text = review.text();
            //div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(2).text()
            this.date = new Date("may");
            this.metadata = new Metadata(Objects.requireNonNull(review.selectFirst("div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2)")).children());
        }
    }
    //May 22, 2024
    private static void dateHelper(String datePlainText) {
        String month = datePlainText.substring(0,3);
        String day = datePlainText.substring(3,5);
//        try {
//            String day = datePlainText.substring(4, datePlainText.indexOf("t"));
//        } catch(StringIndexOutOfBoundsException e) {
//            String day = datePlainText.substring(4, datePlainText.indexOf("n"));
//        }
        String year = datePlainText.substring(datePlainText.indexOf(',')+1);
        System.out.println(month + " " + day + " " + year);
    }
}