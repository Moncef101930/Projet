package tn.esprit.event.controller;


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
import tn.esprit.event.MainFX;
import tn.esprit.event.entity.Evenements;
import tn.esprit.event.service.EvenementService;

import java.awt.event.ActionEvent;

public class AffichageEvent {

    @FXML
    private TableColumn<Evenements, String> nomColumn; // Colonne pour le nom

    @FXML
    private TableColumn<Evenements, String> descriptionColumn; // Colonne pour la description

    @FXML
    private TableColumn<Evenements, String> dateColumn; // Colonne pour la date

    @FXML
    private TableColumn<Evenements, String> lieuColumn; // Colonne pour le lieu

    @FXML
    private TableView<Evenements> eventTable; // TableView pour afficher les événements

    @FXML
    private Button deleteButton; // Bouton pour supprimer un événement

    @FXML
    private Button update;


    @FXML
    public void initialize() {
        // Lier les colonnes aux propriétés de l'objet Evenements
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomEvenement"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionEvenement"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateEvenement"));
        lieuColumn.setCellValueFactory(new PropertyValueFactory<>("lieu"));

        // Charger les données depuis la base de données
        loadEventsFromDatabase();

        // Configurer les actions des boutons
        deleteButton.setOnAction(event -> deleteSelectedEvent());
        update.setOnAction(event -> goToUpdatePage()); // Action du bouton update
    }

    private void loadEventsFromDatabase() {
        try {
            EvenementService evenementService = new EvenementService();
            ObservableList<Evenements> events = FXCollections.observableArrayList(evenementService.getAll());

            // Effacer et recharger les données dans la TableView
            eventTable.getItems().clear();
            eventTable.getItems().addAll(events);
            eventTable.refresh(); // Mise à jour de l'interface

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement des événements.");
        }
    }

    private void deleteSelectedEvent() {
        Evenements selectedEvent = eventTable.getSelectionModel().getSelectedItem();

        if (selectedEvent == null) {
            showAlert("Aucun événement sélectionné", "Veuillez sélectionner un événement à supprimer.");
            return;
        }

        EvenementService evenementService = new EvenementService();
        boolean isDeleted = evenementService.delete(selectedEvent);

        if (isDeleted) {
            showAlert("Succès", "L'événement a été supprimé avec succès.");

            // Recharger la page après suppression
            reloadCurrentPage();
        } else {
            showAlert("Erreur", "Une erreur s'est produite lors de la suppression.");
        }
    }

    private void reloadCurrentPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Affichageivent.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de recharger la page.");
        }
    }

    @FXML
    private void goToUpdatePage() {
        try {
            Evenements selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            System.out.println(selectedEvent);

            if (selectedEvent == null) {
                showAlert("Aucun événement sélectionné", "Veuillez sélectionner un événement à mettre à jour.");
                return;
            }

            // Chargement de la page de mise à jour
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("miseajour.fxml"));
            Parent root = loader.load();


            // Récupération du contrôleur de la page de mise à jour
            miseajour miseAjourController = loader.getController();
            miseAjourController.initData(selectedEvent); // Passer les données de l'événement sélectionné

            // Afficher la nouvelle fenêtre
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir la page de mise à jour.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    public void LogoutAffichEvent(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("dashbord.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}