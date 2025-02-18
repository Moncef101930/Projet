package tn.esprit.event.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Utilisateur {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String mot_de_passe;
    private Role role;
    private LocalDate date_naissance;
    private String bio;
    private String image;
    public Utilisateur() {

    }

    public Utilisateur(String nom, String prenom, String email, String mot_de_passe, Role role, LocalDate date_naissance, String bio, String image) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.role = role;
        this.date_naissance = date_naissance;
        this.bio = bio;
        this.image = image;
    }

    public Utilisateur(Long id, String nom, String prenom, String email, String mot_de_passe, Role role, LocalDate date_naissance, String bio, String image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.role = role;
        this.date_naissance = date_naissance;
        this.bio = bio;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", role=" + role +
                ", date_naissance=" + date_naissance +
                ", bio='" + bio + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
