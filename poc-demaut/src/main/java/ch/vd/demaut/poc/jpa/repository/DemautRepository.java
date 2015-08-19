package ch.vd.demaut.poc.jpa.repository;

import java.util.List;

public interface DemautRepository<T, I> {

    List<T> findAll();

    T find(I id);

    T save(T newsEntry);

    boolean delete(I id);

    List<T> findRange(int[] range);

    int count();
}