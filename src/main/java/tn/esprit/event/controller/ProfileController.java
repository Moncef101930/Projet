package tn.esprit.event.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.event.MainFX;
import tn.esprit.event.entity.Utilisateur;
import tn.esprit.event.service.ServiceUtilisateur;
import tn.esprit.event.utils.BCryptPass;
import tn.esprit.event.utils.Session;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class ProfileController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private DatePicker dateNaissancePicker;
    @FXML
    private TextField bioField;
    @FXML
    private TextField imageField;
    @FXML
    private Label messageLabel;

    private ServiceUtilisateur service = new ServiceUtilisateur();
    private Utilisateur currentUser;

    @FXML
    public void initialize() {
        currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            nomField.setText(currentUser.getNom());
            prenomField.setText(currentUser.getPrenom());
            emailField.setText(currentUser.getEmail());
           // passwordField.setText(currentUser.getMot_de_passe());
            dateNaissancePicker.setValue(currentUser.getDate_naissance());
            bioField.setText(currentUser.getBio());
            imageField.setText(currentUser.getImage());

        }
    }

    @FXML
    private void handleSaveProfile(ActionEvent event) {
        if (!controleSaisie()) {
            return;
        }
        currentUser.setNom(nomField.getText());
        currentUser.setPrenom(prenomField.getText());
        if (!passwordField.getText().trim().isEmpty()) {
            currentUser.setMot_de_passe(BCryptPass.hashPass(passwordField.getText()));
        }
        currentUser.setDate_naissance(dateNaissancePicker.getValue());
        currentUser.setBio(bioField.getText());
        currentUser.setImage(imageField.getText());

        service.modifier(currentUser.getId().intValue(), currentUser);
        messageLabel.setText("Profile updated successfully.");
    }
    private boolean controleSaisie() {
        String errors = "";
        if(nomField.getText() == null || nomField.getText().trim().isEmpty()){
            errors += "Nom vide\n";
        }
        if(prenomField.getText() == null || prenomField.getText().trim().isEmpty()){
            errors += "Prénom vide\n";
        }
        if(emailField.getText() == null || emailField.getText().trim().isEmpty()){
            errors += "Email vide\n";
        }

        if(dateNaissancePicker.getValue() == null){
            errors += "Date de naissance vide\n";
        } else if (ChronoUnit.YEARS.between(dateNaissancePicker.getValue(), LocalDate.now())<12) {
            errors += "L'utilisateur doit avoir au moins 12ans\n";

        }
        if(nomField.getText() != null && nomField.getText().length() < 3){
            errors += "Nom doit contenir au moins 3 caractères\n";
        }
        if(prenomField.getText() != null && prenomField.getText().length() < 3){
            errors += "Prénom doit contenir au moins 3 caractères\n";
        }

        if(!errors.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText("Veuillez corriger les erreurs");
            alert.setContentText(errors);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void gotoLoginn(ActionEvent actionEvent) {
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
