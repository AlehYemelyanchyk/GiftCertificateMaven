package com.epam.esm.services;

import com.epam.esm.dao.exceptions.DAOException;

import java.util.Optional;

public interface RepositoryManager<T> {
    Optional<T> save(T object) throws DAOException;

    Optional<T> update(T object) throws DAOException;
}