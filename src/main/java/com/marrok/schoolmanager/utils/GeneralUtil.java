package com.marrok.schoolmanager.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import java.util.regex.Pattern;

public class GeneralUtil {
    private  static  final Logger logger = Logger.getLogger("GeneralUtil");
    public static boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,8}$";
        return password != null && password.matches(passwordPattern);
    }


    public static boolean isDiffString(String a, String b) {
        return !a.equals(b);
    }

    public static String hash(String str) throws NoSuchAlgorithmException {
        str = str.replaceAll("\\s+", "");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(str.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean containsIgnoreCase(String str, String subString) {
        return str.toLowerCase().contains(subString.toLowerCase());
    }




    public static void loadScene(String resourcePath, ActionEvent event, boolean isResizable) {
        logger.info("loadScene called" + resourcePath);
        FXMLLoader loader = new FXMLLoader(GeneralUtil.class.getResource(resourcePath));

        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setCursor(Cursor.HAND);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getIcons().add(new Image(GeneralUtil.class.getResourceAsStream("/com/marrok/schoolmanager/img/logo.png")));

            // Check if the resource path is the login view; if so, don't set dimensions to maximize
            if (resourcePath.contains("login")) {
                logger.info("loadScene called with login-view");
                // Let the stage size itself based on the scene’s preferred size
                stage.sizeToScene();
            } else {
                // For other views, maximize the stage
//                logger.info(" maximize the stage For other views");
//                Screen screen = Screen.getPrimary();
//                Rectangle2D bounds = screen.getVisualBounds();
//                stage.setX(bounds.getMinX());
//                stage.setY(bounds.getMinY());
//                stage.setWidth(bounds.getWidth());
//                stage.setHeight(bounds.getHeight());


            }
            stage.setScene(scene);
            stage.setResizable(isResizable);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ex) {
            logger.error( "Error loading scene: " + resourcePath, ex);

        }
    }

    public static void showAlert(Alert.AlertType alertType, String title, String content, int timeoutMillis) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);

        if (alertType == Alert.AlertType.INFORMATION && timeoutMillis > 0) {
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(timeoutMillis),
                    event -> alert.close()
            ));
            timeline.setCycleCount(1);
            timeline.play();
        }

        alert.showAndWait();
    }

    public static void showAlert(Alert.AlertType alertType, String title, String content) {
        logger.info("showAlert called with alert type " + alertType);
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        if (alertType == Alert.AlertType.INFORMATION) {
            logger.info("showAlert called with information");
            // Create a timeline to close the alert after the specified timeout
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(500),
                    event -> alert.close()
            ));
            timeline.setCycleCount(1);
            timeline.play();
        }
        alert.showAndWait();

    }

}
