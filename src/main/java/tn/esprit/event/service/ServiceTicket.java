package tn.esprit.event.service;

import tn.esprit.event.entity.Ticket;
import tn.esprit.event.utils.MyConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceTicket implements IService<Ticket> {

    private Connection cnx;

    public ServiceTicket() {
        cnx = MyConnection.getInstance().getCnx();
    }

    @Override
    public void ajouter(Ticket ticket) {
        try {
            String query = "INSERT INTO ticket (utilisateur_nom, evenement_nom, date_achat, type_ticket) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, ticket.getUtilisateur_nom());
            ps.setString(2, ticket.getEvenement_nom());
            ps.setDate(3, Date.valueOf(ticket.getDate_achat()));
            ps.setString(4, ticket.getType_ticket());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du ticket : " + e.getMessage());
        }
    }

    @Override
    public void modifier(int id, Ticket ticket) {
        try {
            String query = "UPDATE ticket SET utilisateur_nom = ?, evenement_nom = ?, date_achat = ?, type_ticket = ? WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, ticket.getUtilisateur_nom());
            ps.setString(2, ticket.getEvenement_nom());
            ps.setDate(3, Date.valueOf(ticket.getDate_achat()));
            ps.setString(4, ticket.getType_ticket());
            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du ticket : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String query = "DELETE FROM ticket WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du ticket : " + e.getMessage());
        }
    }

    @Override
    public List<Ticket> afficher() {
        List<Ticket> tickets = new ArrayList<>();
        try {
            String query = "SELECT * FROM ticket";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getLong("id"));
                ticket.setUtilisateur_nom(rs.getString("utilisateur_nom"));
                ticket.setEvenement_nom(rs.getString("evenement_nom"));
                ticket.setDate_achat(rs.getDate("date_achat").toLocalDate());
                ticket.setType_ticket(rs.getString("type_ticket"));
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des tickets : " + e.getMessage());
        }
        return tickets;
    }
}
