package Service;

import entite.Categorie;
import utile.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieService implements Iservice<Categorie> {
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private Connection con;

    public CategorieService() {
        con = DataSource.getInstance().getConnection();
    }

    @Override
    public void add(Categorie categorie) {
        String requete = "INSERT INTO categorie(nom, description) VALUES (?, ?)";
        try {
            preparedStatement = con.prepareStatement(requete);
            preparedStatement.setString(1, categorie.getNom());
            preparedStatement.setString(2, categorie.getDescription());
            preparedStatement.executeUpdate();
            System.out.println("Categorie added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Categorie categorie) {
        String requete = "delete from categorie where nom='" + categorie.getNom() + "'";
        try {
            statement = con.createStatement();
            statement.executeUpdate(requete);
            System.out.println("Categorie supprime");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Categorie categorie) {
        String requete = "UPDATE categorie SET description= ?,nom= ? WHERE nom = ?";

        // Use try-with-resources to automatically close PreparedStatement
        try (PreparedStatement pst = con.prepareStatement(requete)) {
            // Set parameters in the correct order
            pst.setString(1, categorie.getDescription()); // First parameter (SET description = ?)
            pst.setString(2, categorie.getNom());
            pst.setString(3, categorie.getNom()); // Second parameter (WHERE nom = ?)

            // Execute the update and check affected rows
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Categorie updated successfully.");
            } else {
                System.out.println("No categorie found with name " + categorie.getNom());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating categorie", e);
        }
    }
    @Override
    public List<Categorie> getAll() {
        List<Categorie> cat = new ArrayList<>();
        String requete = "SELECT * FROM categorie";

        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(requete)) {

            while (resultSet.next()) {
                cat.add(new Categorie(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cat;  // Return the correct list
    }

    @Override
    public boolean nomExiste(String nom1) {
        return false;
    }

    @Override
    public Categorie get(int id) {
        return null;

    }

}
