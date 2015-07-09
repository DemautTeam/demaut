package ch.vd.ses.demaut.dao.repository;

import java.util.List;

public interface IDemautRepository<T, I> {

    List<T> findAll();

    T find(I id);

    T save(T newsEntry);

    void delete(I id);
}