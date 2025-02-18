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
import tn.esprit.event.MainFX;
import tn.esprit.event.entity.Categorie;
import tn.esprit.event.service.CategorieService;

import java.io.IOException;

public class CategorieController {

    @FXML
    private TextField Description;

    @FXML
    private TextField Nom;

    @FXML
    private Button create;

    @FXML
    private Button supprimer;
    @FXML
    private Button create1;
    @FXML
    private Button consu;

    private final CategorieService cs;

    public CategorieController() {
        this.cs = new CategorieService();
    }

    @FXML
    void bcreate(ActionEvent event) {
        String nom = Nom.getText().trim();
        String description = Description.getText().trim();

        if (nom.isEmpty() || description.isEmpty()) {
            showAlert("Erreur", "Le nom et la description ne peuvent pas �tre vides !");
            return;
        }

        Categorie c1 = new Categorie(nom, description);
        cs.add(c1);
        showAlert("Succ�s", "Cat�gorie ajout�e avec succ�s !");
        clearFields();
    }

    @FXML
    void bsupprim(ActionEvent event) {
        String nom = Nom.getText().trim();
        String description = Description.getText().trim();

        if (nom.isEmpty() || description.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        Categorie c1 = new Categorie(nom, description);
        cs.delete(c1);
        showAlert("Succ�s", "Cat�gorie supprim�e avec succ�s !");
        clearFields();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        Nom.clear();
        Description.clear();
    }
    @FXML
    public void ucreate(ActionEvent event) {
        String nom1 = Nom.getText().trim();
        String description = Description.getText().trim();

        if (nom1.isEmpty() || description.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        Categorie c1 = new Categorie(nom1, description);
        cs.update(c1);
        showAlert("Succ�s", "Cat�gorie modifi�e avec succ�s !");
        clearFields();
    }

    public void bconsul(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("Affichecat.fxml"));
            Parent root = loader.load();

            // R�cup�rer la fen�tre actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la sc�ne
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Afficher l'erreur dans la console
        }
    }



}
