module tn.esprit.event {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.mail;
    requires jbcrypt;
    requires com.google.gson;
    requires com.google.api.client;
    requires com.google.api.services.calendar;
    requires com.google.api.client.auth;
    requires com.google.api.client.json.gson;
    requires google.api.services.calendar.v3.rev291;
    requires google.api.client;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires java.desktop;


    opens tn.esprit.event.controller to javafx.fxml;
    opens tn.esprit.event.entity to javafx.base, javafx.fxml;
    exports tn.esprit.event;
}