package com.jeelab.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<E, K> {

    Optional<E> find(K id);
    List<E> findAll();
    E create(E entity);
    void delete(E entity);
    void update(E entity);
}
