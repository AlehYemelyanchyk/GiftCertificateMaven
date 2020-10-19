package com.epam.esm.dao;

import com.epam.esm.dao.exceptions.DAOException;

import java.util.List;
import java.util.Optional;

public interface CrudDAO<T, ID> {
    List<T> findAll() throws DAOException;

    Optional<T> findById(ID id) throws DAOException;

    Optional<T> save(T object) throws DAOException;

    T update(T object) throws DAOException;

    void delete(T object) throws DAOException;

    void deleteById(ID id) throws DAOException;
}