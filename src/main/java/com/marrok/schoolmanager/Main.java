package com.marrok.schoolmanager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private static final Logger logger = LogManager.getLogger(Main.class);

    @Override
    public void start(Stage stage) {
        logger.info("Starting the School Manager Application");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/marrok/schoolmanager/views/login/login.fxml"));
            Parent root = fxmlLoader.load();

            // Enable dragging of undecorated stage
            makeStageDraggable(root, stage);

            Scene scene = new Scene(root);
           // scene.getStylesheets().add(Main.class.getResource("/com/marrok/schoolmanager/styles/style.css").toExternalForm());
            stage.setTitle("School Manager");
            stage.setScene(scene);
            stage.setResizable(false);

            // Handle application exit
            stage.setOnCloseRequest(event -> cleanExit());

            stage.show();
            logger.info("UI is ready and displayed");

        } catch (IOException e) {
            logger.error("Failed to initialize the application", e);
            cleanExit();
        }
    }

    private void makeStageDraggable(Parent root, Stage stage) {
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            double newX = event.getScreenX() - xOffset;
            double newY = event.getScreenY() - yOffset;
            stage.setX(Math.max(0, newX));
            stage.setY(Math.max(0, newY));
        });
    }

    public static void run() {
        logger.info("Launching JavaFX Application");
        launch();
    }

    private static void cleanExit() {
        logger.info("Shutting down the application");
        Platform.exit();
        System.exit(0);
    }
}
