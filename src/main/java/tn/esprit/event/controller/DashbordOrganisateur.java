package tn.esprit.event.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.event.MainFX; // Adjust this import if necessary

public class DashbordOrganisateur {

    @FXML
    private Button creationEventButton;

    @FXML
    private Button consulterEventButton;

    @FXML
    private void handleCreationEvent(ActionEvent event) {
        try {
            // Replace "creation-event.fxml" with the actual FXML file for creating an event
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("Evenements.fxml"));
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
            // Replace "consulter-event.fxml" with the actual FXML file for consulting events
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("EventCatAffichage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void LogoutFromOrga(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("login.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
