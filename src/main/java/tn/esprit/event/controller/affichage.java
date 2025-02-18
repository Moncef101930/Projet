package tn.esprit.event.controller;


import tn.esprit.event.entity.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.event.service.CategorieService;

import java.sql.Connection;

public class affichage {

    @FXML
    private TableColumn<Categorie,Integer > col1;

    @FXML
    private TableColumn<Categorie,String> col2;

    @FXML
    private TableColumn<Categorie,String> col3;

    @FXML
    private TableView<Categorie> tableView;

    private Connection con;
    public void initialize() throws Exception {
        CategorieService cs = new CategorieService();
        System.out.println(cs.getAll());

        ObservableList<Categorie> obs = FXCollections.observableList(cs.getAll());
        tableView.setItems(obs);
        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col3.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

}