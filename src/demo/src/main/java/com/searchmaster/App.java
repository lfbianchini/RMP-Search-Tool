package com.searchmaster;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class App {
        //input university name and return its ID
        //uses jsoup to find the university id, doesn't need selenium because page has no loading phase/popup
        public static String getUniversityID(String name) throws IOException {
            String clean = formatNameForQuery(name);
            String query = "https://www.ratemyprofessors.com/search/schools?q=" + clean; //format url

            Document page = Jsoup.connect(query).timeout(3000).userAgent("Mozilla/126.0").get(); //mozilla agent to connect to the page
            Elements pageElements = page.select("#root > div > div > div:nth-child(3) > div.SearchResultsPage__StyledSearchResultsPage-vhbycj-0.bgplVn");
            String id = pageElements.get(0).attributes().get("href"); //gets the id
            return id;
        }


        //input university id and the name of the professor, return's the professor's ID
        //uses selenium to wait for page to load and hit escape when the modal pops up
        //then uses jsoup to extract the professor's id from the html
        public static String getProfessorId(String universityID, String name) throws IOException { 
            String clean = formatNameForQuery(name); //clean the name
            String query = "https://www.ratemyprofessors.com/search/professors/" + universityID + "?q=" + clean; //format url
            WebDriver driver = new FirefoxDriver(); //emulates browser w/ selenium
            driver.get(query); //go to the query site

            Actions action = new Actions(driver);
            action.sendKeys(Keys.ESCAPE).perform(); //hit esc to close out of the modal (popup)

            Document page = Jsoup.parse(driver.getPageSource()); //hand selenium driver's source back to jsoup
            Elements pageElements = page.select("#root > div > div > div:nth-child(3) > div.SearchResultsPage__StyledSearchResultsPage-vhbycj-0.bgplVn > div.SearchResultsPage__SearchResultsWrapper-vhbycj-1.gxbBpy > div:nth-child(4) > a:nth-child(1)");
            // ^ query the html element containing the href for the first professor shown on the page
            String id = pageElements.get(0).attributes().get("href"); //get the ID from the href 
            driver.close();
            return id;
        }

        public static String formatNameForQuery(String name) { //method for formatting names into query form eg "university    of washington" => "university%20of%20washington"
            String clean = name.trim().replaceAll(" +", " "); //remove all trailing/leading w.s. + any extra in middle
            if(clean.contains(" ")) {
                clean = clean.replace(" ", "%20");
            }
            return clean;
        }
        public static void main(String[] args) throws IOException {
            System.out.println(App.getUniversityID("university of san francisco"));
            System.out.println(App.getProfessorId("1600", "karen bouwer"));
        }
}
