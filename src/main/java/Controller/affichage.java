package Controller;

import Service.CategorieService;
import entite.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class affichage {

    @FXML
    private TableColumn<Categorie, Integer> col1;

    @FXML
    private TableColumn<Categorie, String> col2;

    @FXML
    private TableColumn<Categorie, String> col3;

    @FXML
    private TableView<Categorie> tableView;

    @FXML
    private Button deletee;

    @FXML
    private Button updatee;

    private final CategorieService cs = new CategorieService(); // Instance unique du service

    @FXML
    public void initialize() {
        loadCategoriesFromDatabase();
        deletee.setOnAction(event -> deleteSelectedCategory());
        updatee.setOnAction(event -> goToUpdatePage());
    }

    /**
     * Charge toutes les catégories depuis la base de données et les affiche dans la TableView.
     */
    private void loadCategoriesFromDatabase() {
        try {
            ObservableList<Categorie> categories = FXCollections.observableArrayList(cs.getAll());

            // Effacer et recharger les données dans la TableView
            tableView.setItems(categories);
            col1.setCellValueFactory(new PropertyValueFactory<>("id"));
            col2.setCellValueFactory(new PropertyValueFactory<>("nom"));
            col3.setCellValueFactory(new PropertyValueFactory<>("description"));

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement des catégories.");
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

    /**
     * Supprime la catégorie sélectionnée.
     */
    @FXML
    private void deleteSelectedCategory() {
        Categorie selectedCategory = tableView.getSelectionModel().getSelectedItem();

        if (selectedCategory == null) {
            showAlert("Aucune catégorie sélectionnée", "Veuillez sélectionner une catégorie à supprimer.");
            return;
        }

        boolean isDeleted = cs.delete(selectedCategory);

        if (isDeleted) {
            showAlert("Succès", "La catégorie a été supprimée avec succès.");
            loadCategoriesFromDatabase(); // Rafraîchir la liste après suppression
        } else {
            showAlert("Erreur", "Une erreur s'est produite lors de la suppression.");
        }
    }

    /**
     * Recharge la page actuelle.
     */
    private void reloadCurrentPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Affichecat.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de recharger la page.");
        }
    }

    /**
     * Ouvre la page de mise à jour avec les données de la catégorie sélectionnée.
     */
    private void goToUpdatePage() {
        try {
            Categorie selectedCat = tableView.getSelectionModel().getSelectedItem();

            if (selectedCat == null) {
                showAlert("Aucune catégorie sélectionnée", "Veuillez sélectionner une catégorie à mettre à jour.");
                return;
            }

            // Chargement de la page de mise à jour
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/update.fxml"));
            Parent root = loader.load();

            // Récupération du contrôleur de la page de mise à jour
            update miseAjourController = loader.getController();

            // Initialiser les champs avec les données de la catégorie sélectionnée
            miseAjourController.initData(selectedCat);

            // Afficher la nouvelle fenêtre
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir la page de mise à jour.");
        }
    }
}
