module org.searchmasterV2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.seleniumhq.selenium.api;
    requires org.seleniumhq.selenium.firefox_driver;
    requires org.seleniumhq.selenium.chrome_driver;
    requires dev.failsafe.core;
    requires edu.stanford.nlp.corenlp;
    requires ejml.simple;
    requires org.jsoup;
    requires java.desktop;
    requires lucene.core;

    opens org.searchmasterV2 to javafx.fxml;
    exports org.searchmasterV2;
    exports org.searchmasterV2.controllers;
    opens org.searchmasterV2.controllers to javafx.fxml;
}
