package tn.esprit.event;

import tn.esprit.event.entity.Role;
import tn.esprit.event.entity.Ticket;
import tn.esprit.event.entity.Utilisateur;
import tn.esprit.event.service.ServiceTicket;
import tn.esprit.event.service.ServiceUtilisateur;
import tn.esprit.event.utils.MyConnection;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ServiceUtilisateur serviceUtilisateur=new ServiceUtilisateur();
        Utilisateur u=new Utilisateur("John2","Doe2","test2@gmail.com","test123", Role.ADMIN, LocalDate.now(),"bio","image.jpg");
        //serviceUtilisateur.ajouter(u);
        //serviceUtilisateur.modifier(1,u);
        serviceUtilisateur.supprimer(1);
        System.out.println(serviceUtilisateur.afficher());
        ServiceTicket serviceTicket=new ServiceTicket();
        Ticket t=new Ticket(3L,4L,LocalDate.now(),"STANDAR");
       //serviceTicket.ajouter(t);
        //serviceTicket.modifier(2,t);
        serviceTicket.supprimer(2);
        System.out.println(serviceTicket.afficher());

    }
}
