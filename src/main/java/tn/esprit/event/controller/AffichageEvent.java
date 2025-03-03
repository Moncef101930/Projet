package tn.esprit.event.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.event.MainFX;
import tn.esprit.event.entity.Evenements;
import tn.esprit.event.service.EvenementService;

import java.sql.*;


public class AffichageEvent {

    @FXML
    private TableColumn<Evenements, Integer> column1;

    @FXML
    private TableColumn<Evenements, String> column2;

    @FXML
    private TableColumn<Evenements, String> column3;

    @FXML
    private TableColumn<Evenements, String> column4;

    @FXML
    private TableColumn<Evenements, String> column5;

    @FXML
    private TableView<Evenements> tableView;

    private Connection con;

    @FXML
    public void initialize() throws Exception {
        EvenementService es = new EvenementService();
        System.out.println(es.getAll());

        ObservableList<Evenements> obs = FXCollections.observableList(es.getAll());
        tableView.setItems(obs);
        column1.setCellValueFactory(new PropertyValueFactory<>("idEvenement"));
        column2.setCellValueFactory(new PropertyValueFactory<>("nomEvenement"));
        column3.setCellValueFactory(new PropertyValueFactory<>("descriptionEvenement"));
        column4.setCellValueFactory(new PropertyValueFactory<>("dateEvenement"));
        column5.setCellValueFactory(new PropertyValueFactory<>("lieu"));
    }

    public void logg(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFX.class.getResource("dashbord.fxml"));

            Parent root = loader.load();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    }
