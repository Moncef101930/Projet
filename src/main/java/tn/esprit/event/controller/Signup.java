package tn.esprit.event.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.event.MainFX;
import tn.esprit.event.entity.Role;
import tn.esprit.event.entity.Utilisateur;
import tn.esprit.event.service.ServiceUtilisateur;

import java.time.LocalDate;

public class Signup {

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
    private Label errorLabel;
    @FXML
    private ComboBox<Role> roleComboBox;

    private ServiceUtilisateur serviceUtilisateur = new ServiceUtilisateur();
    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll(Role.USER, Role.ORGANISATEUR);

        roleComboBox.setValue(Role.USER);
    }

    @FXML
    private void handleSignup(ActionEvent event) {
        if (!controleSaisie()) {
            return;
        }
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        LocalDate dateNaissance = dateNaissancePicker.getValue();
        String bio = bioField.getText();
        String image = imageField.getText();



        Utilisateur user = new Utilisateur(nom, prenom, email, password, roleComboBox.getValue(), dateNaissance, bio, image);
        serviceUtilisateur.ajouter(user);
        errorLabel.setText("Signup successful! Redirecting to login...");

        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
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
        if(passwordField.getText() == null || passwordField.getText().trim().isEmpty()){
            errors += "Mot de passe vide\n";
        }
        if(dateNaissancePicker.getValue() == null){
            errors += "Date de naissance vide\n";
        }
        if(nomField.getText() != null && nomField.getText().length() < 3){
            errors += "Nom doit contenir au moins 3 caractères\n";
        }
        if(prenomField.getText() != null && prenomField.getText().length() < 3){
            errors += "Prénom doit contenir au moins 3 caractères\n";
        }
        if(passwordField.getText() != null && passwordField.getText().length() < 6){
            errors += "Mot de passe doit contenir au moins 6 caractères\n";
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
}
