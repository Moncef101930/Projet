package tn.esprit.event.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.event.MainFX;
import tn.esprit.event.entity.Evenements;
import tn.esprit.event.service.EvenementService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

public class Evenementcontroller {

    private final EvenementService es = new EvenementService(); // Instance unique

    @FXML
    private Button btnAjouter, btnModifier, btnSupprimer;

    @FXML
    private DatePicker dateEvenement;

    @FXML
    private TextField descriptionEvenement, lieuEvenement, nomEvenement;
    @FXML
    private Button btnGoToNext;


    @FXML

    void bone(ActionEvent event) {
        String nom = nomEvenement.getText().trim();
        String description = descriptionEvenement.getText().trim();
        String lieu = lieuEvenement.getText().trim();
        LocalDate date = dateEvenement.getValue();

        if (nom.isEmpty() || description.isEmpty() || lieu.isEmpty() || date == null) {
            showAlert("Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        Evenements evente = new Evenements(nom, description, lieu, date);

        try {
            es.add(evente);
            showAlert("Succès", "Événement ajouté avec succès !");
            clearFields();
        } catch (Exception e) {
            showAlert("Erreur", "Problème lors de l'ajout !");
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    @FXML
    void bthree(ActionEvent event) {
        String nom = nomEvenement.getText().trim();
        String description = descriptionEvenement.getText().trim();
        String lieu = lieuEvenement.getText().trim();
        LocalDate date = dateEvenement.getValue();

        if (nom.isEmpty()) {
            showAlert("Erreur", "Le champ 'Nom de l'événement' est obligatoire pour la mise à jour !");
            return;
        }

        try {
            Evenements updatedEvent = new Evenements(nom, description, lieu, date);
            es.update(updatedEvent);
            showAlert("Succès", "Événement mis à jour avec succès !");
            clearFields();
        } catch (Exception e) {
            showAlert("Erreur", "Problème lors de la mise à jour !");
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    @FXML
    void btwo(ActionEvent event) {
        String nom = nomEvenement.getText().trim();

        if (nom.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer le nom de l'événement à supprimer !");
            return;
        }

        try {
            Evenements eventToDelete = new Evenements(nom, "", "", null);
            es.delete(eventToDelete);
            showAlert("Succès", "Événement supprimé avec succès !");
            clearFields();
        } catch (Exception e) {
            showAlert("Erreur", "Problème lors de la suppression !");
            System.err.println("Erreur: " + e.getMessage());
        }
    }

    private void clearFields() {
        nomEvenement.clear();
        descriptionEvenement.clear();
        lieuEvenement.clear();
        dateEvenement.setValue(null);
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void goToNextPage(ActionEvent event)  {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("AffichageEvent.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
