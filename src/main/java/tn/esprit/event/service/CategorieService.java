package tn.esprit.event.service;


import tn.esprit.event.entity.Categorie;
import tn.esprit.event.utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategorieService implements Iservice2<Categorie> {
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private Connection con;

    public CategorieService() {
        con = MyConnection.getInstance().getCnx();
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

    public boolean delete(Categorie categorie) {
        String requete = "DELETE FROM categorie WHERE nom = '" + categorie.getNom() + "'";
        try {
            statement = con.createStatement();
            int rowsAffected = statement.executeUpdate(requete);
            if (rowsAffected > 0) {
                System.out.println("Categorie supprime");
                return true;
            } else {
                System.out.println("Aucune categorie trouvee avec le nom : " + categorie.getNom());
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la categorie : " + e.getMessage(), e);
        }
    }


    @Override
    public boolean update(Categorie categorie) {
        String requete = "UPDATE categorie SET description = ?, nom = ? WHERE nom = ?";

        // Use try-with-resources to automatically close PreparedStatement
        try (PreparedStatement pst = con.prepareStatement(requete)) {
            // Set parameters in the correct order
            pst.setString(1, categorie.getDescription()); // First parameter (SET description = ?)
            pst.setString(2, categorie.getNom());
            pst.setString(3, categorie.getNom()); // WHERE nom = ?

            // Execute the update and check affected rows
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Categorie updated successfully.");
                return true;
            } else {
                System.out.println("No categorie found with name " + categorie.getNom());
                return false;
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
    public Categorie getByName(String nom) {
        String requete = "SELECT * FROM categorie WHERE nom = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(requete)) {
            preparedStatement.setString(1, nom);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Categorie(
                            resultSet.getInt("id"),
                            resultSet.getString("nom"),
                            resultSet.getString("description")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la catégorie par nom", e);
        }
        return null; // Retourne null si aucune catégorie n'est trouvée
    }

}


