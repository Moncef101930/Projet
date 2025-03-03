package Controller;

import Service.CategorieService;
import Service.EventCatService;
import entite.Categorie;
import entite.Evenements;
import entite.EventCat;
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

        System.out.println("Selected Event: " + selectedEvent);
        System.out.println("Selected Category: " + selectedCategory);

        EventCatService eventCatService = new EventCatService();
        EventCat eventCat = new EventCat(selectedEvent, selectedCategory);
        System.out.println(eventCat);

        try {
            eventCatService.add(eventCat);
            showAlert("Succès", "L'événement a été associé à la catégorie avec succès.");
            try {
                // Charger la nouvelle page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventCatAffichage.fxml"));
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

        } catch (IllegalArgumentException e) {
            showAlert("Erreur", "Impossible d'associer l'événement à la catégorie : " + e.getMessage());
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
