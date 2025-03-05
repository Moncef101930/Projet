package tn.esprit.event.controller;

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
import tn.esprit.event.entity.Ticket;
import tn.esprit.event.entity.Utilisateur;
import tn.esprit.event.service.ServiceTicket;
import tn.esprit.event.utils.Session;

import java.time.LocalDate;

public class TicketListController {

    @FXML
    private TableView<Ticket> ticketTable;

    @FXML
    private TableColumn<Ticket, Long> idColumn;

    @FXML
    private TableColumn<Ticket, String> utilisateurColumn;

    @FXML
    private TableColumn<Ticket, String> evenementColumn;

    @FXML
    private TableColumn<Ticket, String> dateColumn;

    @FXML
    private TableColumn<Ticket, String> typeColumn;

    @FXML
    private Button btnDelete;

    private ServiceTicket serviceTicket;
    private ObservableList<Ticket> ticketList;

    @FXML
    public void initialize() {
        serviceTicket = new ServiceTicket();

        // Initialize table columns (ensure the property names match those in the Ticket class)
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        utilisateurColumn.setCellValueFactory(new PropertyValueFactory<>("utilisateur_nom"));
        evenementColumn.setCellValueFactory(new PropertyValueFactory<>("evenement_nom"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date_achat"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type_ticket"));

        loadTickets();
    }

    /**
     * Loads tickets from the service and filters them by the current user's name.
     */
    private void loadTickets() {
        ticketList = FXCollections.observableArrayList();
        ObservableList<Ticket> allTickets = FXCollections.observableArrayList(serviceTicket.afficher());

        // Get the current user's name from the session
        Utilisateur currentUser = Session.getCurrentUser();
        String currentUserName = (currentUser != null) ? currentUser.getNom() : "";

        // Filter tickets whose utilisateur_nom matches the current user's name
        for (Ticket t : allTickets) {
            if (t.getUtilisateur_nom().equals(currentUserName)) {
                ticketList.add(t);
            }
        }
        ticketTable.setItems(ticketList);
    }

    /**
     * Called when the "Delete" button is pressed.
     * Deletes the selected ticket from the database and refreshes the table.
     * A ticket is deleted only if its purchase (or event) date has not yet passed.
     */
    @FXML
    private void handleDelete() {
        Ticket selectedTicket = ticketTable.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            showAlert("Information", "Veuillez sélectionner un ticket à supprimer.");
            return;
        }

        // Check if the ticket's date has not passed
        LocalDate ticketDate = selectedTicket.getDate_achat();
        if (ticketDate.isBefore(LocalDate.now())) {
            showAlert("Erreur", "Vous ne pouvez pas supprimer un ticket dont la date est déjà passée.");
            return;
        }

        // Delete the ticket using its id.
        serviceTicket.supprimer((int) selectedTicket.getId());
        showAlert("Succès", "Ticket supprimé avec succès.");
        // Refresh the table
        loadTickets();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void LogoutFromticketlist(ActionEvent actionEvent) {
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
