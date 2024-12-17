package com.marrok.schoolmanager.controllers.login;

import com.marrok.schoolmanager.utils.GeneralUtil;
import com.marrok.schoolmanager.utils.database.SchoolDbHelper;
import com.marrok.schoolmanager.utils.database.UserDbHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

import java.sql.SQLException;

public class LoginController {
    UserDbHelper userDbHelper= new UserDbHelper();
    SchoolDbHelper schoolDbHelper= new SchoolDbHelper();
    Logger logger = Logger.getLogger(LoginController.class);

    public TextField userId;
    public PasswordField password;
    public Button minimizeButton;
    public Button exitButton;
    private boolean isMaximized = false;

    public LoginController() throws SQLException {
    }

    @FXML
    public void processLogin(ActionEvent event) throws SQLException {
        logger.info("Processing Login Event");
        handleLogin(event);
    }

    @FXML
    public void initialize() {
        setupBarbtn();
    }

    private void handleLogin(ActionEvent event) throws SQLException {
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
            if(checkSchoolexist()){


                GeneralUtil.loadScene("/com/marrok/schoolmanager/views/dashboard/dashboard.fxml", event, true);

            }else{
                GeneralUtil.loadScene("/com/marrok/schoolmanager/views/school/school_form_view.fxml", event, true);
            }
        } else {
            logger.info("Login failed");
        }
    }

    private void setupBarbtn() {
        // Exit Button Action
        exitButton.setOnAction(event -> Platform.exit()); // Close the app

        // Minimize Button Action
        minimizeButton.setOnAction(event -> {
            Stage stage = (Stage) minimizeButton.getScene().getWindow();
            if (stage != null) {
                stage.setIconified(true); // Minimize window
            }
        });


    }

    private boolean checkSchoolexist() throws SQLException {
        logger.info("Checking School Manager");
       return schoolDbHelper.isSchoolInfoExists();

    }

}
