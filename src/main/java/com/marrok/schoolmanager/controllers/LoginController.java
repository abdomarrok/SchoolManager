package com.marrok.schoolmanager.controllers;

import com.marrok.schoolmanager.utils.GeneralUtil;
import com.marrok.schoolmanager.utils.database.UserDbHelper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class LoginController {
    UserDbHelper userDbHelper= new UserDbHelper();
    Logger logger = Logger.getLogger(LoginController.class);

    @FXML
    private TextField userId;
    @FXML
    private PasswordField password;

    public LoginController() throws SQLException {
    }

    @FXML
    public void processLogin(ActionEvent event) {
        logger.info("Processing Login Event");
        handleLogin(event);
    }

    @FXML
    public void processLogin2(KeyEvent keyEvent) {
        logger.info("Processing Login KeyEvent");
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleLogin(keyEvent);
        }
    }

    private void handleLogin(Event event) {
        logger.info("Handling Login Event");
        String username = userId.getText();
        String pass = password.getText();
        // Validate input
        if (GeneralUtil.isNullOrEmpty(username) || GeneralUtil.isNullOrEmpty(pass)) {
            GeneralUtil.showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter both username and password.", 0);
            return;
        }
        // Check if the password matches the required format
//        if (!GeneralUtil.isValidPassword(pass)) {
//            GeneralUtil.showAlert(Alert.AlertType.ERROR, "Invalid Password",
//                    "Password must contain at least one number, one uppercase letter, one special character, and be 8-15 characters long.", 0);
//            return;
//        }
        boolean isAuthenticated = userDbHelper.authenticateUser(username, pass);

        if (isAuthenticated) {
            logger.info("Login successful");
            if (event instanceof ActionEvent) {
                GeneralUtil.loadScene("/com/marrok/schoolmanager/views/test.fxml", (ActionEvent) event, true);
            } else if (event instanceof KeyEvent) {
                // Handle scene change differently if needed, or provide a fallback
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                GeneralUtil.loadScene("/com/marrok/schoolmanager/views/test.fxml", new ActionEvent(source, stage), true);
            }
        } else {
            logger.info("Login failed");
        }
    }

}
