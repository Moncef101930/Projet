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
            String query = "INSERT INTO `ticket` "
                    + "(`utilisateur_id`, `evenement_id`, `date_achat`, `type_ticket`) "
                    + "VALUES ("
                    + ticket.getUtilisateur_id() + ", "
                    + ticket.getEvenement_id() + ", "
                    + "'" + ticket.getDate_achat() + "', "
                    + "'" + ticket.getType_ticket() + "')";
            Statement st = cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(int id, Ticket ticket) {
        try {
            String query = "UPDATE `ticket` SET "
                    + "`utilisateur_id`=" + ticket.getUtilisateur_id() + ", "
                    + "`evenement_id`=" + ticket.getEvenement_id() + ", "
                    + "`date_achat`='" + ticket.getDate_achat() + "', "
                    + "`type_ticket`='" + ticket.getType_ticket() + "' "
                    + "WHERE `id`=" + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println( e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String query = "DELETE FROM `ticket` WHERE `id`=" + id;
            Statement st = cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(  e.getMessage());
        }
    }

    @Override
    public List<Ticket> afficher() {
        List<Ticket> tickets = new ArrayList<>();
        try {
            String query = "SELECT * FROM `ticket`";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getLong("id"));
                ticket.setUtilisateur_id(rs.getLong("utilisateur_id"));
                ticket.setEvenement_id(rs.getLong("evenement_id"));
                ticket.setDate_achat(rs.getDate("date_achat").toLocalDate());
                ticket.setType_ticket(rs.getString("type_ticket"));

                tickets.add(ticket);
            }
        } catch (SQLException e) {
            System.out.println( e.getMessage());
        }
        return tickets;
    }
}
