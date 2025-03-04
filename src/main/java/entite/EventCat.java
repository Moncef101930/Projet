package entite;

import java.time.LocalDate;

public class EventCat {
    private String nom;
    private String description;
    private String lieu;
    private LocalDate date;
    private String categorie;
    private Evenements evenements;
    private Categorie categorie1;

    public EventCat() {}

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Evenements getEvenements() {
        return evenements;
    }

    public void setEvenements(Evenements evenements) {
        this.evenements = evenements;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Categorie getCategorie1() {
        return categorie1;
    }

    public void setCategorie1(Categorie categorie1) {
        this.categorie1 = categorie1;
    }



   /* public EventCat(Evenements event, Categorie categorie) {
        this.nom = event.getNomEvenement();
        this.description = event.getDescriptionEvenement();
        this.lieu = event.getLieu();
        this.date = event.getDateEvenement();
        this.categorie = categorie.getNom(); // Assuming Categorie has a getNom() method
    }*/
   public EventCat(Evenements event, Categorie categorie) {
       this.evenements = event;
       this.categorie1 = categorie;

       // Copier les valeurs depuis Evenements et Categorie
       if (event != null) {
           this.nom = event.getNomEvenement();
           this.description = event.getDescriptionEvenement();
           this.lieu = event.getLieu();
           this.date = event.getDateEvenement();
       }
       if (categorie != null) {
           this.categorie = categorie.getNom();
       }
   }
    // Getters and setters
    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getLieu() {
        return lieu;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategorie() {
        return categorie;
    }

    public Evenements getEvenement() {
        return evenements;
    }

    @Override
    public String toString() {
        return "EventCat{" +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", lieu='" + lieu + '\'' +
                ", date=" + date +
                ", categorie='" + categorie + '\'' +
                ", evenements=" + evenements +
                ", categorie1=" + categorie1 +
                '}';
    }
}
