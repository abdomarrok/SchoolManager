package com.marrok.schoolmanager.controllers.student;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AddStudentController {
    public Button cencel_btn;

    public void cencel(ActionEvent event) {
        Stage stage =(Stage) cencel_btn.getScene().getWindow();
        stage.close();

    }
}
