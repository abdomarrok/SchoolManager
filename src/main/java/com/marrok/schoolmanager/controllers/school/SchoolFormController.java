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
    private SchoolDbHelper dbHelper = new SchoolDbHelper();
    public TextField nameField;
    public TextField addressField;
    public TextField emailField;
    public Label schoolNameLabel;
    public Label schoolAddressLabel;
    public Label schoolEmailLabel;

    private int existingSchoolId = -1; // To store the ID of the existing school

    public SchoolFormController() throws SQLException {
    }

    @FXML
    public void initialize() {
        loadSchoolInfo();
    }

    private void loadSchoolInfo() {
        try {
            String[] schoolInfo = dbHelper.getSchoolInfo();
            if (schoolInfo != null) {
                schoolNameLabel.setText(schoolInfo[0]);
                nameField.setText(schoolInfo[0]);
                schoolAddressLabel.setText(schoolInfo[1]);
                addressField.setText(schoolInfo[1]);
                schoolEmailLabel.setText(schoolInfo[2]);
                emailField.setText(schoolInfo[2]);


                existingSchoolId = 1; // You can change this index if needed
            } else {
                schoolNameLabel.setText("-");
                schoolAddressLabel.setText("-");
                schoolEmailLabel.setText("-");
                existingSchoolId = -1; // No existing school info
            }
        } catch (SQLException e) {
            e.printStackTrace();
            GeneralUtil.showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load school information.");
        }
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
            if (existingSchoolId > 0) {
                // Update existing school information
                dbHelper.updateSchool(name, address, email);
                GeneralUtil.showAlert(Alert.AlertType.INFORMATION, "Success", "School information updated successfully!");
            } else {
                // Create a new school record if no existing school info is found
                dbHelper.createSchool(name, address, email);
                GeneralUtil.showAlert(Alert.AlertType.INFORMATION, "Success", "New school information saved successfully!");
            }

            // Reload the updated school info into the form
            loadSchoolInfo();

            // Optionally, clear the form or navigate to a new scene
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
        // Optionally, handle cancel (close form or reset fields)
    }
}
