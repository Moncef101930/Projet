package Service;

import java.util.List;

public interface Iservice<T> {
    void add(T t);
   boolean delete(T t);
    boolean update(T t);
    T get(int id);
    List<T> getAll();

    boolean nomExiste(String nom1);
}
