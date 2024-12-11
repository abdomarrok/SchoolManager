package com.marrok.schoolmanager.controllers.dashboard;

import com.marrok.schoolmanager.model.User;
import com.marrok.schoolmanager.utils.GeneralUtil;
import com.marrok.schoolmanager.utils.database.UserDbHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class DashboardController {
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
    }
    @FXML
    public void goStudentView(ActionEvent event) {
        logger.info("Go student view");
        GeneralUtil.loadScene("/com/marrok/schoolmanager/views/student/student_view.fxml",event,true);
    }
}
