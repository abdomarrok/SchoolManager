package com.marrok.schoolmanager.controllers.dashboard;

import com.marrok.schoolmanager.model.User;
import com.marrok.schoolmanager.utils.GeneralUtil;
import com.marrok.schoolmanager.utils.database.UserDbHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

import java.sql.SQLException;

public class DashboardController {
    public Button minimizeButton;
    public Button maximizeButton;
    public Button exitButton;
    private boolean isMaximized = false;
    UserDbHelper userDbHelper=new UserDbHelper();
    private String username="";
    private int user_id=-1;
    public Label current_user;
    private static final Logger logger = LogManager.getLogger(DashboardController.class);
    public DashboardController() throws SQLException {
        user_id =UserDbHelper.getActiveUserId();
        username=userDbHelper.getUserNameById(user_id);


    }
    @FXML
    public void initialize() {
        current_user.setText(username);
        setupBarbtn();
    }
    @FXML
    public void goStudentView(ActionEvent event) {
        logger.info("Go student view");
        GeneralUtil.loadScene("/com/marrok/schoolmanager/views/student/student_view.fxml",event,true);
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

        // Maximize/Restore Button Action
        maximizeButton.setOnAction(event -> {
            Stage stage = (Stage) maximizeButton.getScene().getWindow();
            if (stage != null) {
                FontIcon maximizeIcon = (FontIcon) maximizeButton.getGraphic(); // Get current icon

                if (isMaximized) {
                    stage.setMaximized(false); // Restore to normal
                    maximizeIcon.setIconLiteral("fa-square-o"); // Set restore icon (FontAwesome icon for square)
                } else {
                    stage.setMaximized(true); // Maximize
                    maximizeIcon.setIconLiteral("fa-window-restore"); // Set maximize icon (FontAwesome icon for window-restore)
                }

                isMaximized = !isMaximized;
            }
        });
    }
}
