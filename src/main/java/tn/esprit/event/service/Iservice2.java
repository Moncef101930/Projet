package tn.esprit.event.service;

import java.util.List;

public interface Iservice2<T> {
    void add(T t);

    boolean update(T t);

    boolean delete(T t);


    T get(int id);


    List<T> getAll();


    boolean nomExiste(String nom);
}
