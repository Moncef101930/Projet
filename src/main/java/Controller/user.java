package Controller;

import Service.EvenementService;
import entite.Categorie;
import entite.Evenements;
import entite.EventCat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import utile.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class user {

    @FXML
    private TableView<EventCat> eventTable;

    @FXML
    private TableColumn<EventCat, String> nomColumn;

    @FXML
    private TableColumn<EventCat, String> descriptionColumn;

    @FXML
    private TableColumn<EventCat, String> lieuColumn;

    @FXML
    private TableColumn<EventCat, String> dateColumn;

    @FXML
    private TableColumn<EventCat, String> categorieColumn;

    @FXML
    private TextField event;  // Missing in your original code

    @FXML
    private Button searchButton;

    private Connection connection;

    @FXML
    public void initialize() {
        connection = DataSource.getInstance().getConnection();

        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        lieuColumn.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        loadEventsFromDatabase();

        // Ajouter un gestionnaire d'événements pour le bouton de recherche
        searchButton.setOnAction(event -> showMostFrequentEvent());
    }

    private void loadEventsFromDatabase() {
        ObservableList<EventCat> eventList = FXCollections.observableArrayList();

        String query = "SELECT e.nom, e.description, e.lieu, e.date, GROUP_CONCAT(c.nom SEPARATOR ', ') AS categorie " +
                "FROM événements e " +
                "JOIN event_cat ce ON e.id = ce.idevent " +
                "JOIN categorie c ON ce.idcat = c.id " +
                "GROUP BY e.id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Evenements evenement = new Evenements(
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getString("lieu"),
                        resultSet.getDate("date").toLocalDate()
                );

                String categorieStr = resultSet.getString("categorie");
                Categorie categorie = new Categorie(categorieStr);

                EventCat eventCat = new EventCat(evenement, categorie);
                eventList.add(eventCat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        eventTable.setItems(eventList);
    }

    private void showMostFrequentEvent() {
        List<EventCat> events = eventTable.getItems();

        if (events.isEmpty()) {
            showAlert("Information", "Aucun événement disponible.");
            return;
        }

        // Compter la fréquence de chaque événement
        Map<String, Integer> eventCount = new HashMap<>();
        for (EventCat e : events) {  // Change Evenements to EventCat
            eventCount.put(e.getNom(), eventCount.getOrDefault(e.getNom(), 0) + 1);
        }

        // Trouver l'événement le plus récurrent
        String mostFrequentEvent = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : eventCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostFrequentEvent = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        // Afficher le résultat dans le champ de texte
        if (mostFrequentEvent != null) {
            event.setText(mostFrequentEvent);
        } else {
            event.setText("Aucun événement récurrent");
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
