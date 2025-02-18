package tn.esprit.event.service;

import java.util.List;

public interface Iservice2<T> {
    void add(T t);

    void update(T t);

    void delete(T t);


    T get(int id);


    List<T> getAll();


    boolean nomExiste(String nom);
}
