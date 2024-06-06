package com.searchmaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class App {
        //input university name and return its ID
        //uses jsoup to find the university id, doesn't need selenium because page has no loading phase/popup
        public static String getUniversityID(String name) throws IOException {
            String clean = formatNameForQuery(name);
            String query = "https://www.ratemyprofessors.com/search/schools?q=" + clean; //format url

            Document page = Jsoup.connect(query).timeout(3000).userAgent("Mozilla/126.0").get(); //mozilla agent to connect to the page
            Elements pageElements = page.select("a.SchoolCard__StyledSchoolCard-sc-130cnkk-0:nth-child(1)");

            String id = pageElements.get(0).attributes().get("href"); //gets the id
            return id.substring(8);

        }


        //input university id and the name of the professor, return's the professor's ID
        //uses selenium to wait for page to load and hit escape when the modal pops up
        //then uses jsoup to extract the professor's id from the html
        public static String getProfessorId(String universityID, String name) throws IOException {
            String clean = formatNameForQuery(name); //clean the name
            String query = "https://www.ratemyprofessors.com/search/professors/" + universityID + "?q=" + clean; //format url
            WebDriver driver = new FirefoxDriver(); //emulates browser w/ selenium
            driver.get(query); //go to the query site
            Cookie cookie2 = new Cookie("ccpa-notice-viewed-02", "true",".ratemyprofessors.com", "/", null, true, false, "None");
            driver.manage().addCookie(cookie2);
            driver.get(query);
            driver.manage().window().minimize();

            Document page = Jsoup.parse(driver.getPageSource()); //hand selenium driver's source back to jsoup
            Elements pageElements = page.select("a.TeacherCard__StyledTeacherCard-syjs0d-0:nth-child(1)");
            // ^ query the html element containing the href for the first professor shown on the page
            String id = pageElements.get(0).attributes().get("href"); //get the ID from the href
            driver.close();
            return id.substring(11);
        }

        public static String getProfessorRating(String professorID) throws IOException {
            String query = "https://www.ratemyprofessors.com/professor/" + professorID;
            Document page = Jsoup.connect(query).get();
            Elements pageElements = page.select("#root > div > div > div.PageWrapper__StyledPageWrapper-sc-3p8f0h-0.lcpsHk > div.TeacherRatingsPage__TeacherBlock-sc-1gyr13u-1.jMpSNb > div.TeacherInfo__StyledTeacher-ti1fio-1.kFNvIp > div:nth-child(1) > div.RatingValue__AvgRating-qw8sqy-1.gIgExh > div > div.RatingValue__Numerator-qw8sqy-2.liyUjw");
            return Objects.requireNonNull(pageElements.first()).text();
        }

        public static void getProfessorReviews(String professorID) throws IOException {
            String query = "https://www.ratemyprofessors.com/professor/" + professorID;
            Document page = Jsoup.connect(query).get();
            Elements reviewList = Objects.requireNonNull(page.selectFirst("#ratingsList")).children();
            int count = 1;
            for(Element review : reviewList) {
                if(!Objects.requireNonNull(review.selectFirst("div:nth-child(1)")).id().equals("ad-controller")) {
                    System.out.println("Review number " + count + ": " + review.select("div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(3)").text());
                } else {
                    System.out.println("ad");
                    continue;
                }
                count++;
            }
        }

        public static String formatNameForQuery(String name) { //method for formatting names into query form eg "university    of washington" => "university%20of%20washington"
            String clean = name.trim().replaceAll(" +", " "); //remove all trailing/leading w.s. + any extra in middle
            if(clean.contains(" ")) {
                clean = clean.replace(" ", "%20");
            }
            return clean;
        }
        public static void main(String[] args) throws IOException {
//            System.out.println(App.getUniversityID("university of san francisco"));
//            System.out.println(App.getProfessorId("1600", "karen bouwer"));
//            System.out.println(getProfessorRating("517854"));
            getProfessorReviews("517854");
        }
}
