package tn.esprit.event.controller;

import tn.esprit.event.entity.Categorie;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.event.entity.EventCat;

public class eventcat {

    @FXML
    private TableColumn<EventCat, String> colCategorie;

    @FXML
    private TableColumn<EventCat, String> colDate;

    @FXML
    private TableColumn<EventCat, String> colDescription;

    @FXML
    private TableColumn<EventCat, String> colLieu;

    @FXML
    private TableColumn<EventCat, String> colNom;

    @FXML
    private TableView<EventCat> tableEvent;

    @FXML
    public void initialize() {
        // Lier les colonnes aux attributs de la classe Evenements et Categorie via EventCat
        colNom.setCellValueFactory(new PropertyValueFactory<>("event.nomEvenement"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("event.descriptionEvenement"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("event.dateEvenement"));
        colLieu.setCellValueFactory(new PropertyValueFactory<>("event.lieu"));
        colCategorie.setCellValueFactory(new PropertyValueFactory<>("cat.nomCategorie"));



    }
}