package com.epam.esm.dao;

import com.epam.esm.dao.exceptions.DAOException;

import java.util.List;
import java.util.Optional;

/**
 * The interface provides methods to work with CrudDAO operations.
 *
 * @author Aleh Yemelyanchyk
 */
public interface CrudDAO<T, ID> {

    /**
     * Returns all instances of the type.
     *
     * @return all entities.
     * @throws DAOException if an SQLException is thrown from its invoked method.
     */
    List<T> findAll() throws DAOException;

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws DAOException if a SQLException is thrown from its invoked method.
     */
    Optional<T> findById(ID id) throws DAOException;

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param object must not be {@literal null}.
     * @return the saved entity or {@literal Optional#empty()} if none returned.
     * @throws DAOException if a SQLException is thrown from its invoked method.
     */
    Optional<T> save(T object) throws DAOException;

    /**
     * Updates a given entity. Use the returned instance for further operations as the update operation might have changed the
     * entity instance completely.
     *
     * @param object must not be {@literal null}.
     * @return the updated entity or {@literal Optional#empty()} if none returned.
     * @throws DAOException if a SQLException is thrown from its invoked method.
     */
    Optional<T> update(T object) throws DAOException;

    /**
     * Deletes a given entity.
     *
     * @param object must not be {@literal null}.
     * @throws DAOException if a SQLException is thrown from its invoked method.
     */
    void delete(T object) throws DAOException;

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws DAOException if a SQLException is thrown from its invoked method.
     */
    void deleteById(ID id) throws DAOException;
}