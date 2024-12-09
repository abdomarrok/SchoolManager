package com.marrok.schoolmanager.controllers.school;

import com.marrok.schoolmanager.utils.GeneralUtil;
import com.marrok.schoolmanager.utils.database.SchoolDbHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class SchoolFormController {
    SchoolDbHelper dbHelper = new SchoolDbHelper();
    public TextField nameField;

    public TextField addressField;

    public TextField emailField;
    public Label schoolNameLabel;

    public Label schoolAddressLabel;

    public Label schoolEmailLabel;
    @FXML
    public void initialize() {
        loadSchoolInfo();
    }

    private void loadSchoolInfo() {
        try {
            SchoolDbHelper dbHelper = new SchoolDbHelper();
            String[] schoolInfo = dbHelper.getSchoolInfo(); // Assume this method retrieves school info
            if (schoolInfo != null) {
                schoolNameLabel.setText(schoolInfo[0]);
                nameField.setText(schoolInfo[0]);
                schoolAddressLabel.setText(schoolInfo[1]);
                addressField.setText(schoolInfo[1]);
                schoolEmailLabel.setText(schoolInfo[2]);
                emailField.setText(schoolInfo[2]);

            } else {
                schoolNameLabel.setText("-");
                schoolAddressLabel.setText("-");
                schoolEmailLabel.setText("-");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public SchoolFormController() throws SQLException {
    }

    /**
     * Handles the save button action.
     * @param event The ActionEvent triggered by the save button.
     */
    public void onSave(ActionEvent event) {
        String name = nameField.getText();
        String address = addressField.getText();
        String email = emailField.getText();

        // Validate inputs
        if (name.isEmpty() || address.isEmpty() || email.isEmpty()) {
            GeneralUtil.showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields are required!");
            return;
        }

        try {
            // Check if school info already exists
            int existingSchoolId = 1;
            // Update existing school information
            dbHelper.updateSchool(existingSchoolId, name, address, email);
            GeneralUtil.showAlert(Alert.AlertType.INFORMATION, "Success", "School information updated successfully!");

            clearForm();
        } catch (SQLException e) {
            GeneralUtil.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save school information: " + e.getMessage());
        }
    }



    private void clearForm() {
        nameField.clear();
        addressField.clear();
        emailField.clear();
    }
    public void onCancel(ActionEvent event) {
    }
}
