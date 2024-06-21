// Main app

package org.searchmasterV2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene; // Scene object to hold the current view
    public static Stage stage; // Stage object representing the application window

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage; // Initialize the primary stage
        scene = new Scene(loadFXML("LoadingScreen")); // Load the initial scene from FXML
        stage.setScene(scene); // Set the scene on the stage
        stage.setOnCloseRequest(event -> Professor.driver.quit()); // Ensure WebDriver is quit on application close
        stage.setTitle("SMV1.0"); // Set the title of the stage
        stage.setResizable(false); // Disable stage resizing
        stage.show(); // Display the stage
    }

    // Method to set the root scene to a different FXML view
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // Method to load an FXML file and return its root node
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(); // Launch the JavaFX application
    }
}