package tn.esprit.event.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.event.MainFX;
import tn.esprit.event.entity.Role;
import tn.esprit.event.entity.Utilisateur;
import tn.esprit.event.service.ServiceUtilisateur;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    private ComboBox<String> cbtri;
    @FXML
    private TextField tfrecherche;

    private ServiceUtilisateur service = new ServiceUtilisateur();
    private ObservableList<Utilisateur> data; // full user data list

    @FXML
    public void initialize() {
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Load data from the service
        data = FXCollections.observableArrayList(service.afficher());
        userTable.setItems(data);
        cbtri.setItems(FXCollections.observableArrayList("Nom", "Prenom", "Email", "Role", "Date Naissance"));

        // Initialize actions (Edit/Delete) in the table
        addActionsToTable();

        // Set up advanced search and sort listeners
        recherche_avance();
        addSortListener();
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
                    handleEditUser(user, event);
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

    private void handleEditUser(Utilisateur user, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("admin-add-user.fxml"));
            Parent root = loader.load();
            AdminAddUser addUserController = loader.getController();
            addUserController.populateForm(user);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gotoAjouter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("admin-add-user.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
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
        data.setAll(service.afficher());
        userTable.refresh();
    }

    @FXML
    void gotoLogin(ActionEvent event) {
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

    // Advanced search method
    public void recherche_avance() {
        // Create a FilteredList based on the full data list
        FilteredList<Utilisateur> filteredData = new FilteredList<>(data, u -> true);

        tfrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(utilisateur -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                // You can add additional fields as needed.
                if (utilisateur.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (utilisateur.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (utilisateur.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(utilisateur.getDate_naissance()).contains(lowerCaseFilter)) {
                    return true;
                } else if (utilisateur.getRole().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
            userTable.setItems(filteredData);
        });
    }

    // Sorting listener on the ComboBox using switch-case and Java streams
    private void addSortListener() {
        cbtri.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                List<Utilisateur> sortedList = new ArrayList<>();
                switch (newValue) {
                    case "Nom":
                        sortedList = data.stream().sorted((u1, u2) -> {
                            return u1.getNom().compareToIgnoreCase(u2.getNom());
                        }).collect(Collectors.toList());
                        break;
                    case "Prenom":
                        sortedList = data.stream().sorted((u1, u2) -> {
                            return u1.getPrenom().compareToIgnoreCase(u2.getPrenom());
                        }).collect(Collectors.toList());
                        break;
                    case "Email":
                        sortedList = data.stream().sorted((u1, u2) -> {
                            return u1.getEmail().compareToIgnoreCase(u2.getEmail());
                        }).collect(Collectors.toList());
                        break;
                    case "Role":
                        sortedList = data.stream().sorted((u1, u2) -> {
                            return u1.getRole().toString().compareToIgnoreCase(u2.getRole().toString());
                        }).collect(Collectors.toList());
                        break;
                    case "Date Naissance":
                        sortedList = data.stream().sorted((u1, u2) -> {
                            return u1.getDate_naissance().compareTo(u2.getDate_naissance());
                        }).collect(Collectors.toList());
                        break;
                    default:
                        sortedList = new ArrayList<>(data);
                        break;
                }
                data.setAll(sortedList);
                userTable.refresh();
            }
        });
    }

    @FXML
    void gotoStat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("stat-user.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
