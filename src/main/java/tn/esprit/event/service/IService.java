package tn.esprit.event.service;

import tn.esprit.event.entity.Ticket;
import tn.esprit.event.entity.Utilisateur;

import java.util.List;

public interface IService<T> {
    void ajouter(T t);
    void modifier(int id,T t);
    void supprimer(int id);
    List<T> afficher();
}
