package tn.esprit.event.controller;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.event.MainFX;
import tn.esprit.event.utils.MyConnection;

public class StatUserRoleController implements Initializable {

    @FXML
    private PieChart pieChart;

    // ObservableList to hold PieChart data
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadData();
        } catch (SQLException ex) {
            Logger.getLogger(StatUserRoleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Load user role statistics from the database into the PieChart
    public void loadData() throws SQLException {
        String query = "SELECT role, COUNT(*) as nbr FROM utilisateur GROUP BY role";
        Statement st = MyConnection.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            String role = rs.getString("role");
            int nbr = rs.getInt("nbr");
            pieChartData.add(new PieChart.Data(role, nbr));
        }
        pieChart.setData(pieChartData);
    }

    @FXML
    private void handleRetour(ActionEvent event) {
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
}