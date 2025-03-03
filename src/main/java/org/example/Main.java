package org.example;

import Service.CategorieService;
import Service.EvenementService;
import entite.Categorie;
import entite.Evenements;
import utile.DataSource;

import java.sql.Connection;
import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Connection ds =DataSource.getInstance().getConnection();
        Categorie c1 = new Categorie("mmusique","l art de la musiquee");
        Categorie c2 = new Categorie("musique","tres tres bien");
        Categorie c3 = new Categorie("musique"," +++ tiktok");
        CategorieService cs = new CategorieService();
        cs.add(c3);
        Evenements e1 =new Evenements("balti","tresbien++","hammamet",LocalDate.of(2025,2,3));
EvenementService es= new EvenementService();
System.out.println(es.getAll());
    }
}