module tn.esprit.event {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.mail;
    requires jbcrypt;


    opens tn.esprit.event.controller to javafx.fxml;
    opens tn.esprit.event.entity to javafx.base, javafx.fxml;
    exports tn.esprit.event;
}