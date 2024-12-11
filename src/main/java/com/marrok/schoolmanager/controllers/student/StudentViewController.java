package com.marrok.schoolmanager.controllers.student;

import com.marrok.schoolmanager.model.Student;
import com.marrok.schoolmanager.utils.DatabaseConnection;
import com.marrok.schoolmanager.utils.GeneralUtil;
import com.marrok.schoolmanager.utils.database.UserDbHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentViewController {
    private static final Logger logger = LogManager.getLogger(StudentViewController.class);
    UserDbHelper userDbHelper=new UserDbHelper();
    private String username="";
    private int user_id=-1;
    public Label current_user;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, Integer> idColumn;
    @FXML
    private TableColumn<Student, String> fnameColumn;
    @FXML
    private TableColumn<Student, String> lnameColumn;
    @FXML
    private TableColumn<Student, Integer> codeColumn;
    @FXML
    private TableColumn<Student, Long> fileNumberColumn;
    @FXML
    private TableColumn<Student, String> birthDateColumn;
    @FXML
    private TableColumn<Student, String> parentNameColumn;
    @FXML
    private TableColumn<Student, String> addressColumn;
    @FXML
    private TableColumn<Student, String> phoneColumn;
    @FXML
    private TableColumn<Student, String> parentPhoneColumn;
    @FXML
    private TableColumn<Student, Integer> levelColumn;
    @FXML
    private TableColumn<Student, String> remarkColumn;
    @FXML
    private TableColumn<Student, String> studentSchoolNameColumn;
    @FXML
    private TableColumn<Student, Integer> remiseColumn;
    @FXML
    private TableColumn<Student, Boolean> exemptionColumn;
    @FXML
    private TableColumn<Student, Boolean> genderColumn;

    public StudentViewController() throws SQLException {
        user_id =UserDbHelper.getActiveUserId();
        username=userDbHelper.getUserNameById(user_id);
    }

    @FXML
    public void initialize() {

        current_user.setText(username);
    }



    public void goDashboard(ActionEvent event) {
        logger.info("Go dashboard");
        GeneralUtil.loadScene("/com/marrok/schoolmanager/views/dashboard/dashboard.fxml", event, true);
    }
}
