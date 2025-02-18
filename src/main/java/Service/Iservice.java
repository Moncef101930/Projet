package Service;

import java.util.List;

public interface Iservice<T> {
    void add(T t);
    void delete(T t);
    void update(T t);
    T get(int id);
    List<T> getAll();

    boolean nomExiste(String nom1);
}
