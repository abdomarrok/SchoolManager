package com.marrok.schoolmanager.controllers.student;

import com.marrok.schoolmanager.model.Student;
import com.marrok.schoolmanager.utils.GeneralUtil;
import com.marrok.schoolmanager.utils.database.StudentDbHelper;
import com.marrok.schoolmanager.utils.database.UserDbHelper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentViewController {
    public Button minimizeButton;
    public Button maximizeButton;
    public Button exitButton;

    private boolean isMaximized = false;
    private static final Logger logger = LogManager.getLogger(StudentViewController.class);
    UserDbHelper userDbHelper=new UserDbHelper();
    StudentDbHelper studentDbHelper=new StudentDbHelper();
    private String username="";
    private int user_id=-1;
    public Button current_user;

    public TableView<Student> studentTable;

    public TableColumn<Student, Integer> idColumn;
    public TableColumn<Student, String> fnameColumn;
    public TableColumn<Student, String> lnameColumn;
    public TableColumn<Student, Integer> codeColumn;
    public TableColumn<Student, Long> fileNumberColumn;
    public TableColumn<Student, String> birthDateColumn;
    public TableColumn<Student, String> parentNameColumn;
    public TableColumn<Student, String> addressColumn;
    public TableColumn<Student, String> phoneColumn;
    public TableColumn<Student, String> parentPhoneColumn;
    public TableColumn<Student, Integer> levelColumn;
    public TableColumn<Student, String> remarkColumn;
    public TableColumn<Student, String> studentSchoolNameColumn;
    public TableColumn<Student, Integer> remiseColumn;
    public TableColumn<Student, Boolean> exemptionColumn;
    public TableColumn<Student, Boolean> genderColumn;
    private static ObservableList<Student> studentsList;
    public StudentViewController() throws SQLException {
        user_id =UserDbHelper.getActiveUserId();
        username=userDbHelper.getUserNameById(user_id);
    }

    @FXML
    public void initialize() {
        logger.info("Initializing StudentViewController");
        current_user.setText(username);
        loadData();
        initializeColumns();
        setupBarbtn();
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



    private void loadData() {
        logger.info("Loading Student data");
        try {
            // Fetch the list of students from the database
            List<Student> students = studentDbHelper.getStudents();
            // Convert the list into an ObservableList for the TableView
            studentsList = FXCollections.observableArrayList(students);
            // Set the ObservableList to the TableView
            studentTable.setItems(studentsList);
        } catch (SQLException e) {
            logger.error("Error loading student data", e);
        }
    }


    private void initializeColumns() {
        // Set up each TableColumn to match the properties of Student
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fnameColumn.setCellValueFactory(new PropertyValueFactory<>("fname"));
        lnameColumn.setCellValueFactory(new PropertyValueFactory<>("lname"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        fileNumberColumn.setCellValueFactory(new PropertyValueFactory<>("fileNumber"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        parentNameColumn.setCellValueFactory(new PropertyValueFactory<>("parentName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        parentPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("parentPhone"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));
        studentSchoolNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentSchoolName"));
        remiseColumn.setCellValueFactory(new PropertyValueFactory<>("remise"));
        exemptionColumn.setCellValueFactory(new PropertyValueFactory<>("exemption"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
    }



    public void add_Student(ActionEvent event) {
        logger.info("Add_StudentController add_Student");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marrok/schoolmanager/views/student/add_student_view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("إضافة تلميذ");
            stage.setResizable(false);
            stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/com/marrok/schoolmanager/img/logo.png")));
            stage.show();
        }catch (IOException e) {
            logger.error(e);
        }
    }
    public void goDashboard(ActionEvent event) {
        logger.info("Go dashboard");
        GeneralUtil.loadScene("/com/marrok/schoolmanager/views/dashboard/dashboard.fxml", event, true);
    }

}
