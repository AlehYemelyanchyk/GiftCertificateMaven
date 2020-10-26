package com.epam.esm.services;

import com.epam.esm.dao.exceptions.DAOException;

import java.util.Optional;

/**
 * The interface provides methods to work with all the repositories in conjunction
 *
 * @author Aleh Yemelyanchyk
 */
public interface RepositoryManager<T> {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param object must not be {@literal null}.
     * @return the saved entity or {@literal Optional#empty()} if none returned.
     * @throws DAOException if a DAOException is thrown from its invoked DAO level method.
     */
    Optional<T> save(T object) throws DAOException;

    /**
     * Updates a given entity. Use the returned instance for further operations as the update operation might have changed the
     * entity instance completely.
     *
     * @param object must not be {@literal null}.
     * @return the updated entity or {@literal Optional#empty()} if none returned.
     * @throws DAOException if a DAOException is thrown from its invoked DAO level method.
     */
    Optional<T> update(T object) throws DAOException;
}