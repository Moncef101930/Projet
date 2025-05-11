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
import tn.esprit.event.entity.Role;
import tn.esprit.event.entity.Utilisateur;
import tn.esprit.event.service.ServiceUtilisateur;
import tn.esprit.event.utils.BCryptPass;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AdminAddUser {

    @FXML
    private TextField bioField;

    @FXML
    private DatePicker dateNaissancePicker;

    @FXML
    private TextField emailField;

    @FXML
    private Label formMessage;

    @FXML
    private TextField imageField;

    @FXML
    private TextField nomField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField prenomField;
    private ServiceUtilisateur service = new ServiceUtilisateur();
    private ObservableList<Utilisateur> userList;
    private Utilisateur selectedUser = null;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    public void initialize() {
        roleComboBox.setItems(FXCollections.observableArrayList("ADMIN", "USER","ORGANISATEUR"));


    }

    @FXML
    private void handleSaveUser(ActionEvent event) {
        if (!controleSaisieAdmin()) {
            return;
        }
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        Role role = Role.valueOf(roleComboBox.getValue());
        LocalDate dateNaissance = dateNaissancePicker.getValue();
        String bio = bioField.getText();
        String image = imageField.getText();
        String hashedPassword = BCryptPass.hashPass(password);
        if (selectedUser == null) {
            Utilisateur newUser = new Utilisateur(nom, prenom, email, hashedPassword, role, dateNaissance, bio, image);
            service.ajouter(newUser);
            formMessage.setText("User added successfully.");
        } else {
            selectedUser.setNom(nom);
            selectedUser.setPrenom(prenom);
            selectedUser.setEmail(email);
            selectedUser.setMot_de_passe(hashedPassword);
            selectedUser.setRole(role);
            selectedUser.setDate_naissance(dateNaissance);
            selectedUser.setBio(bio);
            selectedUser.setImage(image);
            service.modifier(selectedUser.getId().intValue(), selectedUser);
            formMessage.setText("User updated successfully.");
        }
        clearForm();
    }

    @FXML
    void retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("admin-user.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void populateForm(Utilisateur user) {
        selectedUser = user;
        nomField.setText(user.getNom());
        prenomField.setText(user.getPrenom());
        emailField.setText(user.getEmail());
        passwordField.setText(user.getMot_de_passe());
        roleComboBox.setValue(user.getRole().name());
        dateNaissancePicker.setValue(user.getDate_naissance());
        bioField.setText(user.getBio());
        imageField.setText(user.getImage());
    }
    private void clearForm() {
        selectedUser = null;
        nomField.clear();
        prenomField.clear();
        emailField.clear();
        passwordField.clear();
        roleComboBox.setValue(null);
        dateNaissancePicker.setValue(null);
        bioField.clear();
        imageField.clear();
    }
    private boolean controleSaisieAdmin() {
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
        if(roleComboBox.getValue() == null){
            errors += "Role non sélectionné\n";
        }
        if(dateNaissancePicker.getValue() == null){
            errors += "Date de naissance vide\n";
        } else if (ChronoUnit.YEARS.between(dateNaissancePicker.getValue(), LocalDate.now())<12) {
            errors += "L'utilisateur doit avoir au moins 12ans\n";

        }
        // Minimal length checks (optional)
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
