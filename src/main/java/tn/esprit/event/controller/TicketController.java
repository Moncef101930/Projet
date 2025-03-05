package tn.esprit.event.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import tn.esprit.event.MainFX;
import tn.esprit.event.entity.Ticket;
import tn.esprit.event.entity.Utilisateur;
import tn.esprit.event.service.ServiceTicket;
import tn.esprit.event.utils.Session;

import java.time.LocalDate;

public class TicketController {

    @FXML
    private TextField tfUserName;      // Displays current user name (read-only)

    @FXML
    private TextField tfEventName;     // Displays the event name (passed from the user list)

    @FXML
    private DatePicker dpDateAchat;    // Date of ticket purchase

    @FXML
    private ComboBox<String> cbTicketType; // ComboBox for selecting the ticket type

    @FXML
    private Button btnAcheter;         // Button to confirm purchase

    private ServiceTicket serviceTicket;

    @FXML
    public void initialize() {
        serviceTicket = new ServiceTicket();

        // Pre-fill the current user from session; make read-only
        Utilisateur currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            tfUserName.setText(currentUser.getNom()); // Adjust to match your Utilisateur getter
            tfUserName.setEditable(false);
        }

        // Populate ticket type options (customize as needed)
        cbTicketType.setItems(FXCollections.observableArrayList("Standard", "VIP", "Premium"));
    }

    /**
     * Called by the previous controller to set the selected event name.
     * @param eventName The name of the selected event.
     */
    public void setSelectedEvent(String eventName) {
        tfEventName.setText(eventName);
        tfEventName.setEditable(false);
    }

    /**
     * Handler for the "Acheter" button.
     * Validates inputs, creates a new Ticket, and calls the service to add it.
     */
    @FXML
    private void handleAcheterTicket(ActionEvent event) {
        String userName = tfUserName.getText();
        String eventName = tfEventName.getText();
        LocalDate dateAchat = dpDateAchat.getValue();
        String ticketType = cbTicketType.getValue();

        if (userName == null || userName.isEmpty() ||
                eventName == null || eventName.isEmpty() ||
                dateAchat == null ||
                ticketType == null || ticketType.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Create a new Ticket using current user and selected event information
        Ticket ticket = new Ticket(userName, eventName, dateAchat, ticketType);
        serviceTicket.ajouter(ticket);
        showAlert("Succès", "Ticket acheté avec succès !");

        // Optionally, navigate to another page or clear the form
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void LogoutFromTicket(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("dashbord_user.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    }

