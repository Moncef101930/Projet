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
    private TextField description;

    @FXML
    private TextField nom;

    @FXML
    private Button create;

    @FXML
    private Button consu;

    private final CategorieService cs;

    public CategorieController() {
        this.cs = new CategorieService();
    }

    @FXML
    void bcreate(ActionEvent event) {
        if (nom == null || description == null) {
            showAlert("Erreur", "Problème d'initialisation des champs. Vérifiez votre fichier FXML.");
            return;
        }

        String nomText = nom.getText().trim();
        String descriptionText = description.getText().trim();

        if (nomText.isEmpty() || descriptionText.isEmpty()) {
            showAlert("Erreur", "Le nom et la description ne peuvent pas être vides !");
            return;
        }

        try {
            // Vérifier si la catégorie existe déjà
            boolean categoryExists = cs.getAll().stream()
                    .anyMatch(c -> c.getNom().equalsIgnoreCase(nomText));

            if (categoryExists) {
                showAlert("Erreur", "Une catégorie avec ce nom existe déjà !");
                return;
            }

            // Ajouter la catégorie
            Categorie c1 = new Categorie(nomText, descriptionText);
            cs.add(c1);

            showAlert("Succès", "Catégorie ajoutée avec succès !");
            clearFields();
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de l'ajout de la catégorie.");
            e.printStackTrace(); // Pour le débogage
        }
    }

    @FXML
    void bsupprim(ActionEvent event) {
        if (nom == null || description == null) {
            showAlert("Erreur", "Problème d'initialisation des champs. Vérifiez votre fichier FXML.");
            return;
        }

        String nomText = nom.getText().trim();
        String descriptionText = description.getText().trim();

        if (nomText.isEmpty() || descriptionText.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        try {
            Categorie c1 = new Categorie(nomText, descriptionText);
            boolean deleted = cs.delete(c1);

            if (deleted) {
                showAlert("Succès", "Catégorie supprimée avec succès !");
                clearFields();
            } else {
                showAlert("Erreur", "Échec de la suppression. La catégorie n'existe peut-être pas.");
            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la suppression.");
            e.printStackTrace();
        }
    }

    @FXML
    public void bconsul(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("Affichecat.fxml"));
            Parent root = loader.load();

            // Récupérer la fenêtre actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer la scène
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Afficher l'erreur dans la console
            showAlert("Erreur", "Impossible de charger l'affichage des catégories.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        if (nom != null) nom.clear();
        if (description != null) description.clear();
    }
}