package tn.esprit.event.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.event.MainFX;
import java.io.IOException;

public class Dashbord {

    @FXML
    private Button creationEventButton;
    @FXML
    private Button consulterEventButton;

    @FXML
    private void handleCreationEvent(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("Evenements.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConsulterEvent(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainFX.class.getResource("AffichageEvent.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
