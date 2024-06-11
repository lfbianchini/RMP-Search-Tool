package org.searchmasterV2;

import java.io.IOException;
import java.util.*;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.ejml.simple.SimpleMatrix;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;


public class Functionality {
    static WebDriver driver;
    static StanfordCoreNLP pipeline;
    static {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, pos, parse, ssplit, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public static void initializeDriver(String professorID) {
        loadEntirePage(driver, professorID);
    }

    public static void loadEntirePage(WebDriver driver, String professorID) {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
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
                Actions actions = new Actions(driver);
                actions.scrollByAmount(0, 4000);
                WebElement button = driver.findElement(buttonLocator);
                button.click();
            } catch (org.openqa.selenium.NoSuchElementException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
                break;
            } catch (ElementClickInterceptedException e) {
                Actions actions = new Actions(driver);
                WebElement button;
                try {
                    button = driver.findElement(new By.ByCssSelector("#IL_SR_X1 > svg:nth-child(1) > g:nth-child(1) > path:nth-child(4)"));
                } catch (org.openqa.selenium.NoSuchElementException e1) {
                    button = driver.findElement(new By.ByCssSelector("#IL_SR_X2 > svg:nth-child(1) > g:nth-child(1) > path:nth-child(4)"));
                }
                button.click();
                continue;
            } catch(StaleElementReferenceException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
                return;
            }
        }
    }

    public static HashMap<String, String> getUniversityID(String name) throws IOException {
        String clean = formatNameForQuery(name);
        String query = "https://www.ratemyprofessors.com/search/schools?q=" + clean; //format url
        HashMap<String, String> map = new HashMap<>();
        Document page = Jsoup.connect(query).timeout(3000).userAgent("Mozilla/126.0").get(); //mozilla agent to connect to the page
        for(int i=1; i<4; i++) {
            Elements pageElements = page.select("a.SchoolCard__StyledSchoolCard-sc-130cnkk-0:nth-child(" + i + ")");
            String id = pageElements.get(0).attributes().get("href").substring(8);
            String uniName = Objects.requireNonNull(Objects.requireNonNull(pageElements.first()).selectFirst("div:nth-child(2) > div:nth-child(1)")).text();
            map.put(uniName, id);
        }
        return map;
    }

    public static String getProfessorId(String universityID, String name) throws IOException {
        String clean = formatNameForQuery(name); //clean the name
        String query = "https://www.ratemyprofessors.com/search/professors/" + universityID + "?q=" + clean; //format url
        driver.get(query); //go to the query site
        Cookie cookie2 = new Cookie("ccpa-notice-viewed-02", "true",".ratemyprofessors.com", "/", null, true, false, "None");
        driver.manage().addCookie(cookie2);
        driver.get(query);
        driver.manage().window().minimize();

        Document page = Jsoup.parse(driver.getPageSource()); //hand selenium driver's source back to jsoup
        Elements pageElements = page.select("a.TeacherCard__StyledTeacherCard-syjs0d-0:nth-child(1)");
        // ^ query the html element containing the href for the first professor shown on the page
        String id = pageElements.get(0).attributes().get("href"); //get the ID from the href
        return id.substring(11);
    }

    public static String getProfessorRating(String professorID) throws IOException {
        String query = "https://www.ratemyprofessors.com/professor/" + professorID;
        Document page = Jsoup.connect(query).get();
        Elements pageElements = page.select("#root > div > div > div.PageWrapper__StyledPageWrapper-sc-3p8f0h-0.lcpsHk > div.TeacherRatingsPage__TeacherBlock-sc-1gyr13u-1.jMpSNb > div.TeacherInfo__StyledTeacher-ti1fio-1.kFNvIp > div:nth-child(1) > div.RatingValue__AvgRating-qw8sqy-1.gIgExh > div > div.RatingValue__Numerator-qw8sqy-2.liyUjw");
        return Objects.requireNonNull(pageElements.first()).text();
    }

    public static ArrayList<String> getProfessorReviews(String professorID) throws IOException {
        Document page = Jsoup.parse(driver.getPageSource());
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
    //returns professors overall sentiment values ex: [25,25,25,26,24]
    public static long[] getAverageProfSentiments(String professorID) throws IOException {
        ArrayList<String> professorReviews = getProfessorReviews(professorID);
        long[] avgArr = new long[5];
        long[] sentimentArr;
        int count = 1;
        for(String review : professorReviews) {
            sentimentArr = getSentiments(review);
            for(int i=0; i<avgArr.length; i++) {
                avgArr[i] += sentimentArr[i];
            }
            System.out.println("review " + count + " done");
            count++;
        }
        return Arrays.stream(avgArr)
            .map(value -> value/professorReviews.size())
            .toArray();
    }

    public static long[] getSentiments(String paragraph) {
        Annotation document = pipeline.process(paragraph);
        Collection<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        int length = sentences.size();
        long[][] sentiments = new long[length][5];
        long[] avgSentiments = new long[5];
        Tree tree = null;
        SimpleMatrix sm = null;
        Iterator<CoreMap> sentenceIterator = sentences.iterator();
        for(int i=0; i<length; i++) {
            CoreMap sentence = sentenceIterator.next();
            tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            sm = RNNCoreAnnotations.getPredictions(tree);
            for(int j=0; j<5; j++) {
                sentiments[i][j] = Math.round(sm.get(j) * 100d);
                avgSentiments[j] += sentiments[i][j];
            }
        }
        return Arrays.stream(avgSentiments)
            .map(value -> value/length)
            .toArray();
    }

    public static ArrayList<Element> getMetadata(String professorID) throws IOException {
        Document page = Jsoup.parse(driver.getPageSource());
        String query = "https://www.ratemyprofessors.com/professor/" + professorID;
        Elements classList = Objects.requireNonNull(page.selectFirst("#ratingsList")).children();
        ArrayList<Element> metadata = new ArrayList<>();
        for(Element classElement : classList) {
            if (!Objects.requireNonNull(classElement.selectFirst("div:nth-child(1)")).id().equals("ad-controller")) {
                Elements reviewMeta = Objects.requireNonNull(classElement.selectFirst("div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2)")).children();
                metadata.addAll(reviewMeta);
            }
            continue;
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
}
