package tn.esprit.event.controller;

import tn.esprit.event.MainFX;
import tn.esprit.event.service.CategorieService;
import tn.esprit.event.entity.Categorie;
import tn.esprit.event.entity.Evenements;
import tn.esprit.event.entity.EventCat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import tn.esprit.event.service.EventCatService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AjouterCatController {

    @FXML
    private ComboBox<String> categoryComboBox; // Stocke uniquement les noms des catégories

    @FXML
    private TextField nomField; // Récupère le nom de l'événement sélectionné

    @FXML
    private TextField nomField1; // Récupère la description de l'événement sélectionné

    @FXML
    private Button valider; // Bouton de validation

    private Evenements selectedEvent; // Stocke l'événement sélectionné

    @FXML
    public void initialize() {
        loadCategories(); // Charger les catégories dans la ComboBox
        nomField.setEditable(false);
        nomField1.setEditable(false);
    }

    public void setSelectedEvent(Evenements event) {
        this.selectedEvent = event;
        if (event != null) {
            nomField.setText(event.getNomEvenement()); // Nom de l'événement
            nomField1.setText(event.getDescriptionEvenement()); // Description de l'événement
        }
    }

    private void loadCategories() {
        CategorieService categorieService = new CategorieService();
        List<Categorie> categories = categorieService.getAll(); // Récupérer toutes les catégories

        // Extraire uniquement les noms des catégories
        List<String> categoryNames = categories.stream()
                .map(Categorie::getNom) // Extraire les noms des catégories
                .collect(Collectors.toList());

        ObservableList<String> categoryList = FXCollections.observableArrayList(categoryNames);
        categoryComboBox.setItems(categoryList); // Ajouter les noms des catégories à la ComboBox
    }

    private Categorie getSelectedCategory() {
        String selectedCategoryName = categoryComboBox.getValue(); // Récupérer le nom sélectionné
        if (selectedCategoryName == null || selectedCategoryName.isEmpty()) {
            return null;
        }

        CategorieService categorieService = new CategorieService();
        return categorieService.getByName(selectedCategoryName); // Retourne l'objet Categorie
    }

    @FXML
    void ajouter(ActionEvent event) {
        if (selectedEvent == null) {
            showAlert("Erreur", "Aucun événement sélectionné !");
            return;
        }

        String categoryName = categoryComboBox.getValue();
        if (categoryName == null || categoryName.isEmpty()) {
            showAlert("Erreur", "Veuillez sélectionner une catégorie.");
            return;
        }

        Categorie selectedCategory = getSelectedCategory();
        if (selectedCategory == null) {
            showAlert("Erreur", "La catégorie sélectionnée est invalide !");
            return;
        }

        EventCatService eventCatService = new EventCatService();

        // Vérification si l'événement est déjà associé à une catégorie
        if (eventCatService.eventAlreadyInCategory(selectedEvent.getIdEvenement(), selectedCategory.getId())) {
            showAlert("Erreur", "Cet événement est déjà associé à cette catégorie !");
            return;
        }

        // Vérifier si l'événement a déjà une catégorie
        EventCat existingEventCat = eventCatService.getByEventId(selectedEvent.getIdEvenement());

        if (existingEventCat != null) {
            // Mise à jour de la catégorie existante
            existingEventCat.setCategorie1(selectedCategory);
            eventCatService.update(existingEventCat);
            showAlert("Succès", "La catégorie de l'événement a été mise à jour avec succès.");
        } else {
            // Ajout de l'association si elle n'existe pas encore
            EventCat eventCat = new EventCat(selectedEvent, selectedCategory);
            eventCatService.add(eventCat);
            showAlert("Succès", "L'événement a été associé à la catégorie avec succès.");
        }

        // Charger la nouvelle page après l'ajout ou la mise à jour
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("EventCatAffichage.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            // Changer de scène
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir la page d'affichage.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}