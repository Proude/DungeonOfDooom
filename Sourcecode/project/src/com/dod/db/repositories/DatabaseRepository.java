package com.dod.db.repositories;

/**
 * Created by Fortnox on 18/11/2016.
 */
public interface DatabaseRepository<T> {

    void insert(T object);
    void delete(T object);
    T getType(int id);

}
