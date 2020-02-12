package airPort.psk.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

public interface OwnRepository<T> {

    void add(T item);

    void update(T item);

    void remove(T item);

    T getById(int i);

    List<T> getAll();
}

