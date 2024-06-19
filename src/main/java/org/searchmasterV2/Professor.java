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
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SourceType;

public class Professor {
    static WebDriver driver;
    static StanfordCoreNLP pipeline;
    static Document loadedPage;
    static Connection jsoupConnection;
    public static Map<List<Long>, String> sentimentList;
    static ArrayList<Review> reviewList;

    static {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, pos, parse, ssplit, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public static void initializeDriver(String professorID) {
        loadedPage = loadEntirePage(driver, professorID);
        driver.quit();
    }

    public static Document loadEntirePage(WebDriver driver, String professorID) {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver.get("https://www.ratemyprofessors.com/professor/" + professorID);
        Cookie cookie2 = new Cookie("ccpa-notice-viewed-02", "true", ".ratemyprofessors.com", "/", null, true, false, "None");
        driver.manage().addCookie(cookie2);
        driver.get("https://www.ratemyprofessors.com/professor/" + professorID);

        By buttonLocator = By.cssSelector(".Buttons__Button-sc-19xdot-1");

        while (true) {
            try {
                Actions actions = new Actions(driver);
                actions.scrollByAmount(0, 4000);
                WebElement button = driver.findElement(buttonLocator);
                button.click();
            } catch (NoSuchElementException | StaleElementReferenceException e) {
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
            }
        }
        return Jsoup.parse(driver.getPageSource());
    }

    public static HashMap<String, String> getUniversityID(String name) throws IOException {
        String clean = formatNameForQuery(name);
        String query = "https://www.ratemyprofessors.com/search/schools?q=" + clean;
        HashMap<String, String> map = new HashMap<>();
        jsoupConnection = Jsoup.connect(query);
        Document page = jsoupConnection.get();
        Elements pageElements = page.select("a.SchoolCard__StyledSchoolCard-sc-130cnkk-0");
        for (Element element : pageElements) {
            String href = element.attributes().get("href");
            if (href.startsWith("/")) {
                String id = href.substring(8);
                String uniName = Objects.requireNonNull(element.selectFirst("div:nth-child(2) > div:nth-child(1)")).text();
                map.put(uniName, id);
            }
        }
        return map;
    }

    public static HashMap<String, String> getProfessorId(String universityID, String name) throws IOException {
        String clean = formatNameForQuery(name);
        String query = "https://www.ratemyprofessors.com/search/professors/" + universityID + "?q=" + clean;
        driver.get(query);
        Cookie cookie2 = new Cookie("ccpa-notice-viewed-02", "true", ".ratemyprofessors.com", "/", null, true, false, "None");
        driver.manage().addCookie(cookie2);
        driver.get(query);
        driver.manage().window().minimize();
        HashMap<String, String> map = new HashMap<>();
        Document page = Jsoup.parse(driver.getPageSource());
        Elements pageElements = page.select("a.TeacherCard__StyledTeacherCard-syjs0d-0");
        for (Element pageElement : pageElements) {
            String id = pageElement.attributes().get("href").substring(11);
            String profName = Objects.requireNonNull(Objects.requireNonNull(pageElement).selectFirst("div:nth-child(1) > div:nth-child(2) > div:nth-child(1)")).text();
            map.put(profName, id);
        }
        return map;
    }

    public static String getProfessorRating(String professorID) throws IOException {
        String query = "https://www.ratemyprofessors.com/professor/" + professorID;
        Document page = jsoupConnection.newRequest(query).get();
        Elements pageElements = page.select("#root > div > div > div.PageWrapper__StyledPageWrapper-sc-3p8f0h-0.lcpsHk > div.TeacherRatingsPage__TeacherBlock-sc-1gyr13u-1.jMpSNb > div.TeacherInfo__StyledTeacher-ti1fio-1.kFNvIp > div:nth-child(1) > div.RatingValue__AvgRating-qw8sqy-1.gIgExh > div > div.RatingValue__Numerator-qw8sqy-2.liyUjw");
        return Objects.requireNonNull(pageElements.first()).text();
    }

    public static String getProfessorWouldTakeAgain(String professorID) throws IOException {
        String query = "https://www.ratemyprofessors.com/professor/" + professorID;
        Document page = jsoupConnection.newRequest(query).get();
        Elements pageElements = page.select("div.FeedbackItem__StyledFeedbackItem-uof32n-0:nth-child(1) > div:nth-child(1)");
        return Objects.requireNonNull(pageElements.first()).text();
    }

    public static String getProfessorDifficulty(String professorID) throws IOException {
        String query = "https://www.ratemyprofessors.com/professor/" + professorID;
        Document page = jsoupConnection.newRequest(query).get();
        Elements pageElements = page.select("div.FeedbackItem__StyledFeedbackItem-uof32n-0:nth-child(2) > div:nth-child(1)");
        return Objects.requireNonNull(pageElements.first()).text();
    }

    public static void getProfessorReviews(String professorID) throws IOException {
        Elements reviewListRaw = Objects.requireNonNull(loadedPage.selectFirst("#ratingsList")).children();
        ArrayList<Review> reviews = new ArrayList<>();
        for (Element review : reviewListRaw) {
            if (!Objects.requireNonNull(review.selectFirst("div:nth-child(1)")).id().equals("ad-controller")) {
                reviews.add(new Review(review));
            }
        }
        reviewList = reviews;
    }

    public static long[] getAverageProfessorSentiments(String professorID) throws IOException {
        sentimentList = Collections.synchronizedMap(new HashMap<>());
        reviewList.parallelStream().map(review -> {
            sentimentList.put(getSentiments(review.getText()), review.getMetadata().getGrade());
            return review;
        }).forEachOrdered(review -> System.out.println("review"));
        long[] avgArr = new long[5];
        int arrIndex = 0;
        int count = 1;
        for (List<Long> review : sentimentList.keySet()) {
            for (Long reviewScore : review) {
                avgArr[arrIndex] += reviewScore;
                arrIndex++;
            }
            System.out.println("review " + count + " done");
            arrIndex = 0;
            count++;
        }
        System.out.println(sentimentList.entrySet());
        return Arrays.stream(avgArr)
                .map(value -> value / reviewList.size())
                .toArray();
    }

    public static List<Long> consolidateReview(List<Long> review) {
        review.set(1, review.get(1) + review.get(0));
        review.set(3, review.get(3) + review.get(4));
        review.remove(0);
        review.remove(3);
        return review;
    }

    public static Map<List<Long>, String> consolidateSentimentList() {
        Map<List<Long>, String> sentimentListTwo = sentimentList;
        for (List<Long> review : sentimentListTwo.keySet()) {
            consolidateReview(review);
        }
        return sentimentListTwo;
    }

    public static List<Long> getSentimentListFrequency() {
        List<Long> frequencyList = Arrays.asList(0L, 0L, 0L);
        for (List<Long> review : sentimentList.keySet()) {
            Long max = Collections.max(review);
            if (review.indexOf(max) == 0) {
                frequencyList.set(0, frequencyList.get(0) + 1);
            } else if (review.indexOf(max) == 1) {
                frequencyList.set(1, frequencyList.get(1) + 1);
            } else if (review.indexOf(max) == 2) {
                frequencyList.set(2, frequencyList.get(2) + 1);
            }
        }
        return frequencyList;
    }

    public static List<Long> getSentiments(String paragraph) {
        Annotation document = pipeline.process(paragraph);
        Collection<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        int length = sentences.size();
        long[][] sentiments = new long[length][5];
        long[] avgSentiments = new long[5];
        Tree tree = null;
        SimpleMatrix sm = null;
        Iterator<CoreMap> sentenceIterator = sentences.iterator();
        for (int i = 0; i < length; i++) {
            CoreMap sentence = sentenceIterator.next();
            tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            sm = RNNCoreAnnotations.getPredictions(tree);
            for (int j = 0; j < 5; j++) {
                sentiments[i][j] = Math.round(sm.get(j) * 100d);
                avgSentiments[j] += sentiments[i][j];
            }
        }
        List<Long> avgSentimentsList = new ArrayList<>();
        for (long sentiment : avgSentiments) {
            avgSentimentsList.add(sentiment / length);
        }
        return avgSentimentsList;
    }

    public static String formatNameForQuery(String name) {
        String clean = name.trim().replaceAll(" +", " ");
        if (clean.contains(" ")) {
            clean = clean.replace(" ", "%20");
        }
        return clean;
    }

    public static ArrayList<String> getGrades() {
        ArrayList<String> grades = new ArrayList<>();
        for (Review review : reviewList) {
            grades.add(review.getMetadata().getGrade());
        }
        return grades;
    }

    private static ArrayList<String> filterGrades(ArrayList<String> grades) {
        ArrayList<String> filteredGrades = new ArrayList<>();
        for (String grade : grades) {
            try {
                if (grade.length() <= 2) {
                    filteredGrades.add(grade);
                }
            } catch (Exception e) {
                continue;
            }
        }
        return filteredGrades;
    }

    public static String averageGrade(ArrayList<String> grades) {
        double avgWeight = 0;
        grades = filterGrades(grades);
        for (String grade : grades) {
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

        return avgWeightLetter;
    }

    public static String averageProfGrade(String professorID) throws IOException {
        return averageGrade(filterGrades(getGrades()));
    }

    public static void main(String[] args) throws IOException {

    }
}