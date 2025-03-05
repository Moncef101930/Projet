package tn.esprit.event.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.event.MainFX; // adjust the import if MainFX is in a different package

public class DashbordAdmin {

    @FXML
    private Button creationEventButton;

    @FXML
    private Button consulterEventButton;

    @FXML
    private Button consulterEventButton1;

    @FXML
    private void handleCreationEvent(ActionEvent event) {
        try {
            // Assuming "creation-cat.fxml" is the FXML for creating a category
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("Categorie.fxml"));
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
            // Assuming "consulter-event-et-cat.fxml" is the FXML for consulting events and categories
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("EvenementAd.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gotoGestionUser(ActionEvent event) {
        try {
            // Navigates to the admin user management interface
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("admin-user.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void LogoutAdmoun(ActionEvent actionEvent) {
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
