package tn.esprit.event.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import tn.esprit.event.entity.Role;
import tn.esprit.event.entity.Utilisateur;
import tn.esprit.event.service.ServiceUtilisateur;

import java.time.LocalDate;
import java.util.Optional;

public class AdminController {

    @FXML
    private TableView<Utilisateur> userTable;
    @FXML
    private TableColumn<Utilisateur, String> nomColumn;
    @FXML
    private TableColumn<Utilisateur, String> prenomColumn;
    @FXML
    private TableColumn<Utilisateur, String> emailColumn;
    @FXML
    private TableColumn<Utilisateur, Role> roleColumn;
    @FXML
    private TableColumn<Utilisateur, Void> actionsColumn;

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private DatePicker dateNaissancePicker;
    @FXML
    private TextField bioField;
    @FXML
    private TextField imageField;
    @FXML
    private Label formMessage;

    private ServiceUtilisateur service = new ServiceUtilisateur();
    private ObservableList<Utilisateur> userList;
    private Utilisateur selectedUser = null;

    @FXML
    public void initialize() {
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleComboBox.setItems(FXCollections.observableArrayList("ADMIN", "USER"));
        userList = FXCollections.observableArrayList(service.afficher());
        userTable.setItems(userList);
        addActionsToTable();
        roleComboBox.setItems(FXCollections.observableArrayList("ADMIN", "USER"));

    }

    private void addActionsToTable() {
        actionsColumn.setCellFactory(col -> new TableCell<Utilisateur, Void>() {
            private final Button deleteButton = new Button("Delete");
            private final Button editButton = new Button("Edit");
            {
                deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                editButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
                deleteButton.setOnAction(event -> {
                    Utilisateur user = getTableView().getItems().get(getIndex());
                    handleDeleteUser(user);
                });
                editButton.setOnAction(event -> {
                    Utilisateur user = getTableView().getItems().get(getIndex());
                    populateForm(user);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox pane = new HBox(5, editButton, deleteButton);
                    setGraphic(pane);
                }
            }
        });
    }

    private void populateForm(Utilisateur user) {
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

        if (selectedUser == null) {
            Utilisateur newUser = new Utilisateur(nom, prenom, email, password, role, dateNaissance, bio, image);
            service.ajouter(newUser);
            formMessage.setText("User added successfully.");
        } else {
            selectedUser.setNom(nom);
            selectedUser.setPrenom(prenom);
            selectedUser.setEmail(email);
            selectedUser.setMot_de_passe(password);
            selectedUser.setRole(role);
            selectedUser.setDate_naissance(dateNaissance);
            selectedUser.setBio(bio);
            selectedUser.setImage(image);
            service.modifier(selectedUser.getId().intValue(), selectedUser);
            formMessage.setText("User updated successfully.");
        }
        refreshUserTable();
        clearForm();
    }

    private void handleDeleteUser(Utilisateur user) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Delete User");
        alert.setContentText("Are you sure you want to delete user " + user.getNom() + " " + user.getPrenom() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            service.supprimer(user.getId().intValue());
            refreshUserTable();
        }
    }

    private void refreshUserTable() {
        userList.setAll(service.afficher());
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
