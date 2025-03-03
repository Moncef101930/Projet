package Controller;

import Service.EvenementService;
import entite.Evenements;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class miseajour {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField lieuField;

    @FXML
    private TextField nomField;

    @FXML
    private Button validerButton;

    private Evenements currentEvent; // Stocker l'événement en cours d'édition
    private final EvenementService evenementService = new EvenementService(); // Service pour la BDD

    @FXML
    public void initialize() {
        // Attacher l'événement de mise à jour au bouton
        validerButton.setOnAction(this::Update);
    }

    // Initialiser les champs avec les données de l'événement
    public void initData(Evenements event) {
        if (event != null) {
            currentEvent = event; // Stocker l'événement en cours d'édition
            nomField.setText(event.getNomEvenement());
            descriptionField.setText(event.getDescriptionEvenement());
            lieuField.setText(event.getLieu());

            // Convertir la date en LocalDate et l'afficher dans le DatePicker
            datePicker.setValue(event.getDateEvenement());
        }
    }

    // Mettre à jour l'événement lorsqu'on clique sur le bouton "Valider"
    private void Update(ActionEvent event) {
        if (currentEvent == null) {
            showAlert("Erreur", "Aucun événement sélectionné pour la mise à jour.");
            return;
        }

        // Récupérer les nouvelles valeurs des champs
        String newNom = nomField.getText();
        String newDescription = descriptionField.getText();
        String newLieu = lieuField.getText();
        LocalDate newDate = datePicker.getValue();

        if (newDate.isBefore(LocalDate.now())) {
            showAlert("erreur","La date de début doit être après la date actuelle !");
            return;
        }

        // Vérifier que les champs ne sont pas vides
        if (newNom.isEmpty() || newDescription.isEmpty() || newLieu.isEmpty() || newDate == null) {
            showAlert("Champs vides", "Veuillez remplir tous les champs.");
            return;
        }

        // Mettre à jour l'objet Evenements
        currentEvent.setNomEvenement(newNom);
        currentEvent.setDescriptionEvenement(newDescription);
        currentEvent.setLieu(newLieu);
        currentEvent.setDateEvenement(newDate); // Directement en LocalDate

        // Mise à jour dans la base de données
        try {
            evenementService.update(currentEvent);
            showAlert("ok", "evenement modifier : " );

        } catch (Exception e) {
            showAlert("Erreur", "Échec de la mise à jour : " + e.getMessage());
        }
    }

    // Afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
