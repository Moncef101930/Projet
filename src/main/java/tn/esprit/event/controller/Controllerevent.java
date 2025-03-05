package tn.esprit.event.controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.event.MainFX;
import tn.esprit.event.entity.Evenements;
import tn.esprit.event.service.EvenementService;

import java.util.HashMap;
import java.util.Map;

public class Controllerevent {

    @FXML
    private Button ajout;

    @FXML
    private TableColumn<Evenements, String> dateColumn;

    @FXML
    private TableColumn<Evenements, String> descriptionColumn;

    @FXML
    private TableView<Evenements> eventTable;

    @FXML
    private TableColumn<Evenements, String> lieuColumn;

    @FXML
    private TableColumn<Evenements, String> nomColumn;


    @FXML
    private TextField event; // PAS MODIFIÉ

    private ObservableList<Evenements> events = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Vérification si les boutons sont bien injectés

        if (ajout == null) {
            System.out.println("ERREUR: Le bouton 'ajout' est null !");
        } else {
            ajout.setOnAction(this::goToNextPage);
        }

        // Lier les colonnes aux propriétés de l'objet Evenements
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomEvenement"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("descriptionEvenement"));
        lieuColumn.setCellValueFactory(new PropertyValueFactory<>("lieu"));

        dateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateEvenementAsString()));

        // Charger les données depuis la base de données
        loadEventsFromDatabase();
    }

    private void loadEventsFromDatabase() {
        try {
            EvenementService evenementService = new EvenementService();
            events.setAll(evenementService.getAll());

            // Mise à jour correcte de la TableView
            eventTable.setItems(events);
            eventTable.refresh(); // Mise à jour de l'affichage

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement des événements.");
        }
    }

    private void findMostFrequentEvent() {
        if (events.isEmpty()) {
            event.clear();
            return;
        }

        Map<String, Integer> frequencyMap = new HashMap<>();

        // Compter la fréquence de chaque événement
        for (Evenements ev : events) {
            String eventName = ev.getNomEvenement();
            if (eventName != null) {
                frequencyMap.put(eventName, frequencyMap.getOrDefault(eventName, 0) + 1);
            }
        }

        // Trouver l'événement le plus récurrent
        String mostFrequentEvent = null;
        int maxFrequency = 0;
        boolean isUnique = true;

        for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mostFrequentEvent = entry.getKey();
                maxFrequency = entry.getValue();
                isUnique = true;
            } else if (entry.getValue() == maxFrequency) {
                isUnique = false;
            }
        }

        // Afficher dans le TextField uniquement s'il est unique
        if (isUnique && mostFrequentEvent != null) {
            event.setText(mostFrequentEvent);
        } else {
            event.clear();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void goToNextPage(ActionEvent event) {
        try {
            Evenements selectedEvent = eventTable.getSelectionModel().getSelectedItem();
            System.out.println(selectedEvent);

            if (selectedEvent == null) {
                showAlert("Aucun événement sélectionné", "Veuillez sélectionner un événement à mettre à jour.");
                return;
            }
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("AjouterCat.fxml"));
            Parent root = loader.load();

            AjouterCatController controller = loader.getController();
            controller.setSelectedEvent(selectedEvent); // Envoie des données à la page cible

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir la page.");
        }
    }

    public void LogoutFromEvent(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("dashbord_admin.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    }
