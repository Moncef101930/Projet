package Service;

import entite.Categorie;
import entite.EventCat;
import utile.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entite.Evenements;

public class EventCatService implements Iservice<EventCat> {

    private Connection con;

    public EventCatService() {
        con = DataSource.getInstance().getConnection();
    }


    @Override
    public void add(EventCat eventCat) {
        String requete = "INSERT INTO event_cat (idevent, idcat) VALUES (?, ?)";
        if (eventCat == null || eventCat.getEvenements() == null || eventCat.getCategorie() == null) {
            throw new IllegalArgumentException("L'événement ou la catégorie ne peut pas être null");
        }
        try (PreparedStatement preparedStatement = con.prepareStatement(requete)) {
            preparedStatement.setInt(1, eventCat.getEvenements().getIdEvenement());
            preparedStatement.setInt(2, eventCat.getCategorie1().getId());
            preparedStatement.executeUpdate();
            System.out.println("EventCat added successfully");
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'association événement-catégorie", e);
        }
    }





    @Override
public boolean delete(EventCat eventCat) {
    String requete = "DELETE FROM event_cat WHERE idevent = ? AND idcat = ?";
    try {
        PreparedStatement preparedStatement = con.prepareStatement(requete);
        preparedStatement.setInt(1, eventCat.getEvenements().getIdEvenement()); // Récupérer l'ID de l'événement
        preparedStatement.setInt(2, eventCat.getCategorie1().getId()); // Récupérer l'ID de la catégorie
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0; // Retourne true si suppression réussie
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    @Override
    public boolean update(EventCat eventCat) {
        String requete = "UPDATE event_cat SET idcat = ? WHERE idevent = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(requete);
            preparedStatement.setInt(1, eventCat.getCategorie1().getId()); // Nouvelle catégorie
            preparedStatement.setInt(2, eventCat.getEvenements().getIdEvenement()); // Événement existant
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Retourne true si au moins une ligne a été mise à jour
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public EventCat get(int id) {
        return null;
    }

    @Override
    public List<EventCat> getAll() {
        List<EventCat> eventCatList = new ArrayList<>();
        String requete = "SELECT idevent, idcat FROM event_cat";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(requete);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EventCat eventCat = new EventCat();

                // Récupérer les objets Event et Categorie associés
                Evenements event = new Evenements();
                event.setIdEvenement(resultSet.getInt("idevent"));

                Categorie categorie = new Categorie();
                categorie.setId(resultSet.getInt("idcat"));

                eventCat.setEvenements(event);
                eventCat.setCategorie(String.valueOf(categorie));

                eventCatList.add(eventCat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventCatList;
    }


    @Override
    public boolean nomExiste(String nom1) {
        return false;
    }

}
