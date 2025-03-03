package tn.esprit.event.entity;

import java.time.LocalDate;

public class Evenements {
    private int idEvenement;
    private String nomEvenement;
    private String descriptionEvenement;
    private LocalDate dateEvenement;
    private String lieu;

    public Evenements(int id, String nom, String description, LocalDate date, String lieu) {
        this.idEvenement = id;
        this.nomEvenement = nom;
        this.descriptionEvenement = description;
        this.lieu = lieu;
        this.dateEvenement = date;
    }

    public Evenements(String nomEvenement, String descriptionEvenement, String lieu, LocalDate dateEvenement) {
        this.nomEvenement = nomEvenement;
        this.descriptionEvenement = descriptionEvenement;
        this.lieu = lieu;
        this.dateEvenement = dateEvenement;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public String getNomEvenement() {
        return nomEvenement;
    }

    public void setNomEvenement(String nomEvenement) {
        this.nomEvenement = nomEvenement;
    }

    public String getDescriptionEvenement() {
        return descriptionEvenement;
    }

    public void setDescriptionEvenement(String descriptionEvenement) {
        this.descriptionEvenement = descriptionEvenement;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalDate getDateEvenement() {
        return dateEvenement;
    }

    public void setDateEvenement(LocalDate dateEvenement) {
        this.dateEvenement = dateEvenement;
    }

    @Override
    public String toString() {
        return "Evenements{" +
                "idEvenement=" + idEvenement +
                ", nomEvenement='" + nomEvenement + '\'' +
                ", descriptionEvenement='" + descriptionEvenement + '\'' +
                ", lieu='" + lieu + '\'' +
                ", dateEvenement=" + dateEvenement +
                '}';
    }
}