package Controller;

import Service.GoogleCalendarService;
import entite.Categorie;
import entite.Evenements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import entite.EventCat;
import javafx.event.ActionEvent;
import java.time.ZoneId;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utile.DataSource;
import entite.EventCat; // Create this model class

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
        connection = DataSource.getInstance().getConnection();

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

            // Convert LocalDate to Google Calendar DateTime
            DateTime startDateTime = new DateTime(java.util.Date.from(evenement.getDateEvenement()
                    .atStartOfDay(ZoneId.systemDefault()).toInstant()));

            Event googleEvent = new Event()
                    .setSummary(evenement.getNomEvenement())
                    .setLocation(evenement.getLieu())
                    .setDescription("Catégories: " + categories);

            EventDateTime start = new EventDateTime().setDateTime(startDateTime);
            googleEvent.setStart(start).setEnd(start); // One-day event

            try {
                googleCalendarService.addEvent(googleEvent);
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
}
