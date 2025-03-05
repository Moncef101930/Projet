package tn.esprit.event.entity;
import java.util.Objects;
import java.time.LocalDate;
public class Ticket {
    private long id;
    private String utilisateur_nom;
    private String evenement_nom;
    private LocalDate date_achat;
    private String type_ticket;


    public Ticket(String type_ticket, LocalDate date_achat, String evenement_nom, String utilisateur_nom, long id) {
        this.type_ticket = type_ticket;
        this.date_achat = date_achat;
        this.evenement_nom = evenement_nom;
        this.utilisateur_nom = utilisateur_nom;
        this.id = id;
    }
    public Ticket(String utilisateur_nom, String evenement_nom, LocalDate date_achat, String type_ticket) {
        this.utilisateur_nom = utilisateur_nom;
        this.evenement_nom = evenement_nom;
        this.date_achat = date_achat;
        this.type_ticket = type_ticket;


    }
    public Ticket() {}

    public long getId() {
        return id;
    }

    public String getUtilisateur_nom() {
        return utilisateur_nom;
    }

    public String getEvenement_nom() {
        return evenement_nom;
    }

    public LocalDate getDate_achat() {
        return date_achat;
    }

    public String getType_ticket() {
        return type_ticket;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUtilisateur_nom(String utilisateur_nom) {
        this.utilisateur_nom = utilisateur_nom;
    }

    public void setEvenement_nom(String evenement_nom) {
        this.evenement_nom = evenement_nom;
    }

    public void setDate_achat(LocalDate date_achat) {
        this.date_achat = date_achat;
    }

    public void setType_ticket(String type_ticket) {
        this.type_ticket = type_ticket;

    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", utilisateur_nom=" + utilisateur_nom +
                ", evenement_nom=" + evenement_nom +
                ", date_achat=" + date_achat +
                ", type_ticket='" + type_ticket + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && Objects.equals(utilisateur_nom, ticket.utilisateur_nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, utilisateur_nom);
    }
}
