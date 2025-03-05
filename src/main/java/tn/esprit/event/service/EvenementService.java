package tn.esprit.event.service;


import javafx.scene.control.Alert;
import tn.esprit.event.entity.Evenements;
import tn.esprit.event.utils.MyConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements Iservice2<Evenements> {
    private final Connection con;

    public EvenementService() {
        con = MyConnection.getInstance().getCnx();
    }

    @Override
    public void add(Evenements evenements) {
        String requete = "INSERT INTO événements(nom, description, date, lieu) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(requete)) {
            preparedStatement.setString(1, evenements.getNomEvenement());
            preparedStatement.setString(2, evenements.getDescriptionEvenement());
            preparedStatement.setDate(3, Date.valueOf(evenements.getDateEvenement()));
            preparedStatement.setString(4, evenements.getLieu());

            preparedStatement.executeUpdate();
            System.out.println("Événement ajouté avec succès !");
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'événement : " + e.getMessage(), e);
        }
    }

    public boolean delete(Evenements evenements) {
        String requete = "DELETE FROM événements WHERE nom = ?";

        try (PreparedStatement pst = con.prepareStatement(requete)) {
            pst.setString(1, evenements.getNomEvenement());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Événement supprimé avec succès.");
                return true;
            } else {
                System.out.println("Aucun événement trouvé avec le nom : " + evenements.getNomEvenement());
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'événement : " + e.getMessage(), e);
        }
    }



    @Override
    public boolean update(Evenements evenements) {
        String requete = "UPDATE événements SET lieu = ?, description = ?, date = ? WHERE nom = ?";

        try (PreparedStatement pst = con.prepareStatement(requete)) {
            pst.setString(1, evenements.getLieu());
            pst.setString(2, evenements.getDescriptionEvenement());
            pst.setDate(3, Date.valueOf(evenements.getDateEvenement()));
            pst.setString(4, evenements.getNomEvenement());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Événement mis à jour avec succès.");
                return true;
            } else {
                System.out.println("Aucun événement trouvé avec le nom : " + evenements.getNomEvenement());
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'événement : " + e.getMessage(), e);
        }
    }


    @Override
    public Evenements get(int id) {
        String requete = "SELECT * FROM événements WHERE id = ?";
        Evenements evenement = null;

        try (PreparedStatement pst = con.prepareStatement(requete)) {
            pst.setInt(1, id);
            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next()) {
                    evenement = new Evenements(
                            resultSet.getInt("id"),
                            resultSet.getString("nom"),
                            resultSet.getString("description"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getString("lieu")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'événement : " + e.getMessage(), e);
        }

        return evenement;
    }

    @Override
    public List<Evenements> getAll() {
        List<Evenements> ev = new ArrayList<>();
        String requete = "SELECT * FROM événements";

        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(requete)) {

            while (resultSet.next()) {
                ev.add(new Evenements(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("lieu")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des événements : " + e.getMessage(), e);
        }
        return ev;
    }

    @Override
    public boolean nomExiste(String nom1) {
        return false;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
