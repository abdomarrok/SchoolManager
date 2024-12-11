module com.marrok.schoolmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires java.logging;
    requires log4j;
    requires java.sql;

    opens com.marrok.schoolmanager to javafx.fxml;
    exports com.marrok.schoolmanager;
    exports com.marrok.schoolmanager.controllers.school;
    opens com.marrok.schoolmanager.controllers.school to javafx.fxml;
    exports com.marrok.schoolmanager.controllers.login;
    opens com.marrok.schoolmanager.controllers.login to javafx.fxml;
    exports com.marrok.schoolmanager.controllers.dashboard;
    opens com.marrok.schoolmanager.controllers.dashboard to javafx.fxml;
    exports com.marrok.schoolmanager.controllers.student;
    opens com.marrok.schoolmanager.controllers.student to javafx.fxml;

}