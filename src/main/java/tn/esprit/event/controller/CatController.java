package tn.esprit.event.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.event.entity.Categorie;
import tn.esprit.event.service.CategorieService;

import java.io.IOException;

public class CatController {

    private final CategorieService cs;

    @FXML
    private TextField desc;

    @FXML
    private TextField nom;

    @FXML
    private Button update;
    @FXML
    private Button consu;


    // Constructeur sans argument requis par JavaFX
    public CatController() {
        this.cs = new CategorieService();
    }

    @FXML
    void up(ActionEvent event) {
        String nom1 = nom.getText().trim();
        String description = desc.getText().trim();

        if (nom1.isEmpty() || description.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        Categorie c1 = new Categorie(nom1, description);
        cs.update(c1);
        showAlert("Succès", "Catégorie modifiée avec succès !");
        clearFields();
    }

    private void clearFields() {
        nom.clear();
        desc.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}