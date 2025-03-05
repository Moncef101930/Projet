package tn.esprit.event.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.event.MainFX;
import tn.esprit.event.service.GoogleCalendarService;
import tn.esprit.event.entity.Categorie;
import tn.esprit.event.entity.Evenements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import tn.esprit.event.entity.EventCat;
import javafx.event.ActionEvent;
import tn.esprit.event.utils.MyConnection;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventCategorieAffichageC {

    @FXML
    private TableView<EventCat> tableView;

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

    private Connection connection;

    private GoogleCalendarService googleCalendarService = new GoogleCalendarService();

    public void initialize() {
        connection = MyConnection.getInstance().getCnx();

        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        lieuColumn.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        loadEventData();
    }

    private void loadEventData() {
        ObservableList<EventCat> eventList = FXCollections.observableArrayList();

        String query = "SELECT e.nom, e.description, e.lieu, e.date, GROUP_CONCAT(c.nom SEPARATOR ', ') AS categorie " +
                "FROM événements e " +
                "JOIN event_cat ce ON e.id = ce.idevent " +
                "JOIN categorie c ON ce.idcat = c.id " +
                "GROUP BY e.id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Create Evenements and Categorie objects (you may need to modify this to fit your model)
                Evenements evenement = new Evenements(
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getString("lieu"),
                        resultSet.getDate("date").toLocalDate()
                );

                // Assuming categories are being concatenated in a single field
                String categorieStr = resultSet.getString("categorie");
                Categorie categorie = new Categorie(categorieStr);  // Assuming a constructor that takes a String

                EventCat eventCat = new EventCat(evenement, categorie);
                eventList.add(eventCat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.setItems(eventList);
    }

    @FXML
    void exporter(ActionEvent event) {
        List<EventCat> events = tableView.getItems();

        if (events.isEmpty()) {
            showAlert("Information", "Aucun événement à exporter.");
            return;
        }

        for (EventCat eventCat : events) {
            Evenements evenement = (Evenements) eventCat.getEvenement();
            String categories = eventCat.getCategorie();

            // Convert LocalDate to ZonedDateTime for Google Calendar API
            LocalDateTime startDateTime = evenement.getDateEvenement().atStartOfDay();
            ZonedDateTime zonedDateTime = startDateTime.atZone(ZoneId.systemDefault());

            // Convert LocalDate to the Google Calendar Date object (for all-day event)
            com.google.api.client.util.DateTime googleStartDateTime = new com.google.api.client.util.DateTime(zonedDateTime.toInstant().toEpochMilli());

            // Calculate end date as the next day (1-day event)
            ZonedDateTime endZonedDateTime = zonedDateTime.plusDays(1);
            com.google.api.client.util.DateTime googleEndDateTime = new com.google.api.client.util.DateTime(endZonedDateTime.toInstant().toEpochMilli());

            // Create Google Calendar Event (using setDate to make it an all-day event)
            Event googleEvent = new Event()
                    .setSummary(evenement.getNomEvenement())
                    .setLocation(evenement.getLieu())
                    .setDescription("Catégories: " + categories)
                    .setStart(new EventDateTime().setDate(new com.google.api.client.util.DateTime(googleStartDateTime.getValue())).setTimeZone(ZoneId.systemDefault().toString()))
                    .setEnd(new EventDateTime().setDate(new com.google.api.client.util.DateTime(googleEndDateTime.getValue())).setTimeZone(ZoneId.systemDefault().toString()));

            try {
                googleCalendarService.addEvent(evenement.getNomEvenement(), evenement.getLieu(), evenement.getDateEvenement());
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Erreur", "Impossible d'exporter l'événement: " + evenement.getNomEvenement());
            }
        }

        showAlert("Succès", "Événements exportés avec succès vers Google Calendar.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void LogoutFromCat(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("dashbord.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}