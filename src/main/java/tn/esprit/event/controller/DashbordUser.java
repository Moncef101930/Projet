package tn.esprit.event.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.event.MainFX; // Adjust the package if needed

public class DashbordUser {

    @FXML
    private Button creationEventButton;

    @FXML
    private Button consulterEventButton;

    @FXML
    private Button btnProfile;

    @FXML
    private void handleCreationEvent(ActionEvent event) {
        try {
            // Assuming "list-event.fxml" is the FXML for listing events
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("user.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConsulterEvent(ActionEvent event) {
        try {
            // Assuming "consulter-ticket.fxml" is the FXML for consulting tickets
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("TicketList.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gotoProfile(ActionEvent event) {
        try {
            // Assuming "profile.fxml" is the FXML for the user's profile page
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("profile.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void LogoutDashUser(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("login.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    }

