package tn.esprit.event.entity;
import java.util.Objects;
import java.time.LocalDate;
public class Ticket {
    private long id;
    private Long utilisateur_id;
    private Long evenement_id;
    private LocalDate date_achat;
    private String type_ticket;


    public Ticket(String type_ticket, LocalDate date_achat, Long evenement_id, Long utilisateur_id, long id) {
        this.type_ticket = type_ticket;
        this.date_achat = date_achat;
        this.evenement_id = evenement_id;
        this.utilisateur_id = utilisateur_id;
        this.id = id;
    }
    public Ticket(Long utilisateur_id, Long evenement_id, LocalDate date_achat, String type_ticket) {
        this.utilisateur_id = utilisateur_id;
        this.evenement_id = evenement_id;
        this.date_achat = date_achat;
        this.type_ticket = type_ticket;


    }
    public Ticket() {}

    public long getId() {
        return id;
    }

    public Long getUtilisateur_id() {
        return utilisateur_id;
    }

    public Long getEvenement_id() {
        return evenement_id;
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

    public void setUtilisateur_id(Long utilisateur_id) {
        this.utilisateur_id = utilisateur_id;
    }

    public void setEvenement_id(Long evenement_id) {
        this.evenement_id = evenement_id;
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
                ", utilisateur_id=" + utilisateur_id +
                ", evenement_id=" + evenement_id +
                ", date_achat=" + date_achat +
                ", type_ticket='" + type_ticket + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && Objects.equals(utilisateur_id, ticket.utilisateur_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, utilisateur_id);
    }
}
