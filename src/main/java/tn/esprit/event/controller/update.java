package tn.esprit.event.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.event.entity.Categorie;
import tn.esprit.event.service.CategorieService;

public class update {

    @FXML
    private TextField field1;

    @FXML
    private TextField field2;

    @FXML
    private Button validerButton;

    private Categorie currentCategorie; // Stocke la catégorie en cours d'édition
    private final CategorieService categorieService = new CategorieService(); // Service pour la gestion des catégories

    @FXML
    public void initialize() {
        // Attacher l'événement de mise à jour au bouton
        validerButton.setOnAction(this::updateCategorie);
    }

    /**
     * Initialise les champs avec les données de la catégorie sélectionnée.
     *
     * @param categorie La catégorie à modifier.
     */
    public void initData(Categorie categorie) {
        if (categorie != null) {
            currentCategorie = categorie; // Stocker la catégorie en cours d'édition
            field1.setText(categorie.getNom());
            field2.setText(categorie.getDescription());
        }
    }

    /**
     * Met à jour la catégorie lorsqu'on clique sur le bouton "Valider".
     */
    private void updateCategorie(ActionEvent event) {
        if (currentCategorie == null) {
            showAlert("Erreur", "Aucune catégorie sélectionnée pour la mise à jour.");
            return;
        }

        // Récupérer les nouvelles valeurs des champs
        String newNom = field1.getText().trim();
        String newDescription = field2.getText().trim();

        // Vérifier que les champs ne sont pas vides
        if (newNom.isEmpty() || newDescription.isEmpty()) {
            showAlert("Champs vides", "Veuillez remplir tous les champs.");
            return;
        }

        // Mettre à jour l'objet Categorie
        currentCategorie.setNom(newNom);
        currentCategorie.setDescription(newDescription);

        // Mise à jour dans la base de données
        boolean isUpdated = categorieService.update(currentCategorie);

        if (isUpdated) {
            showAlert("Succès", "Catégorie mise à jour avec succès !");
        } else {
            showAlert("Erreur", "Échec de la mise à jour de la catégorie.");
        }
    }

    /**
     * Affiche une alerte avec un message donné.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
