package com.marrok.schoolmanager.controllers.login;

import com.marrok.schoolmanager.utils.GeneralUtil;
import com.marrok.schoolmanager.utils.database.SchoolDbHelper;
import com.marrok.schoolmanager.utils.database.UserDbHelper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
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
    SchoolDbHelper schoolDbHelper= new SchoolDbHelper();
    Logger logger = Logger.getLogger(LoginController.class);

    @FXML
    private TextField userId;
    @FXML
    private PasswordField password;

    public LoginController() throws SQLException {
    }

    @FXML
    public void processLogin(ActionEvent event) throws SQLException {
        logger.info("Processing Login Event");
        handleLogin(event);
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

    private boolean checkSchoolexist() throws SQLException {
        logger.info("Checking School Manager");
       return schoolDbHelper.isSchoolInfoExists();

    }

}
