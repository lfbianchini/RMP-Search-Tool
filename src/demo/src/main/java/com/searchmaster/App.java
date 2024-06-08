package com.searchmaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class App {

        public static WebDriver loadReviews(String professorID) {

            FirefoxOptions options = new FirefoxOptions();

            // Initialize WebDriver
            options.addArguments("--headless");
            WebDriver driver = new FirefoxDriver(options);

            driver.get("https://www.ratemyprofessors.com/professor/" + professorID); // replace with the actual
            Cookie cookie2 = new Cookie("ccpa-notice-viewed-02", "true",".ratemyprofessors.com", "/", null, true, false, "None");
            driver.manage().addCookie(cookie2);
            driver.get("https://www.ratemyprofessors.com/professor/" + professorID);

            // Locator for the button
            By buttonLocator = By.cssSelector(".Buttons__Button-sc-19xdot-1"); // replace with the actual locator (e.g., id, class, xpath)
            // Loop to click the button until it is no longer present
            while (true) {
                try {
                    // Find the button
                    WebElement button = driver.findElement(buttonLocator);
                    button.click();
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    break;
                } catch (ElementClickInterceptedException e) {
                    continue;
                }
            }
            return driver;
        }

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
            FirefoxOptions options = new FirefoxOptions ();
            options.addArguments("--headless");
            WebDriver driver = new FirefoxDriver (options);//emulates browser w/ selenium
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

        public static ArrayList<String> getProfessorReviews(String professorID) throws IOException {
            Document page = Jsoup.parse(loadReviews(professorID).getPageSource());
            String query = "https://www.ratemyprofessors.com/professor/" + professorID;
            Elements reviewList = Objects.requireNonNull(page.selectFirst("#ratingsList")).children();
            ArrayList<String> reviews = new ArrayList<>();
            for(Element review : reviewList) {
                if(!Objects.requireNonNull(review.selectFirst("div:nth-child(1)")).id().equals("ad-controller")) {
                    reviews.add(review.select("div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(3)").text());
                }
                continue;
            }
            return reviews;
        }

        public static ArrayList<Element> getMetadata(String professorID) throws IOException {
            Document page = Jsoup.parse(loadReviews(professorID).getPageSource());
            String query = "https://www.ratemyprofessors.com/professor/" + professorID;
            Elements classList = Objects.requireNonNull(page.selectFirst("#ratingsList")).children();
            ArrayList<Element> metadata = new ArrayList<>();
            for(Element classElement : classList) {
                if(!Objects.requireNonNull(classElement.selectFirst("div:nth-child(1)")).id().equals("ad-controller")) {
                    Elements reviewMeta = Objects.requireNonNull(classElement.selectFirst("div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2)")).children();
                    metadata.addAll(reviewMeta);
                }
            }
            return metadata;
        }

        public static ArrayList<String> getGrades(ArrayList<Element> metadata) {
            ArrayList<String> grades = new ArrayList<>();
            for (Element meta: metadata) {
                if(meta.text().contains("Grade: ") && !meta.text().contains("Not sure yet")) {
                    grades.add(meta.text().substring(7));
                }
            }
            return grades;
        }

        public static String averageGrade(ArrayList<String> grades) {
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

            return avgWeightLetter + ": " + String.valueOf(avgWeight);
        }

        public static String averageProfGrade(String professorID) throws IOException {
            return averageGrade(getGrades(getMetadata(professorID)));
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
            System.out.println(getProfessorReviews("483530"));
//            System.out.println(Arrays.toString(getMetadata("517854").toArray()));
//            System.out.println(Arrays.toString(getGrades(getMetadata("517854")).toArray()));
//            ArrayList<String> grades = new ArrayList<>(Arrays.asList(
//                  "A+", "A", "A-", "C", "C+", "B-", "D", "D+", "F", "B",
//                  "B+", "A-", "A+", "A+", "B+", "A+", "A", "A+", "B+", "B",
//                  "D", "D-", "A+", "A+", "C-", "B-", "B-", "A-", "A+", "C+"
//            ));
//            System.out.println(averageGrade(grades));
              System.out.println(averageProfGrade("483530"));
        }
}
