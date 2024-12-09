module com.marrok.schoolmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.synedra.validatorfx;
    requires java.logging;
    requires log4j;
    requires java.sql;

    opens com.marrok.schoolmanager to javafx.fxml;
    exports com.marrok.schoolmanager;
    opens com.marrok.schoolmanager.controllers to javafx.fxml;
    exports com.marrok.schoolmanager.controllers;
    exports com.marrok.schoolmanager.controllers.school;
    opens com.marrok.schoolmanager.controllers.school to javafx.fxml;
    exports com.marrok.schoolmanager.controllers.login;
    opens com.marrok.schoolmanager.controllers.login to javafx.fxml;

}