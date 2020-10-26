package com.epam.esm.services;

import com.epam.esm.services.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The interface provides methods to work with CrudService operations.
 *
 * @author Aleh Yemelyanchyk
 */
public interface CrudService<T, ID> {
    /**
     * Returns all instances of the type.
     *
     * @return all entities
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method.
     */
    List<T> findAll() throws ServiceException;

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method.
     */
    Optional<T> findById(ID id) throws ServiceException;

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param object must not be {@literal null}.
     * @return the saved entity or {@literal Optional#empty()} if none returned.
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method.
     */
    Optional<T> save(T object) throws ServiceException;

    /**
     * Updates a given entity. Use the returned instance for further operations as the update operation might have changed the
     * entity instance completely.
     *
     * @param object must not be {@literal null}.
     * @return the updated entity or {@literal Optional#empty()} if none returned.
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method.
     */
    Optional<T> update(T object) throws ServiceException;

    /**
     * Deletes a given entity.
     *
     * @param object must not be {@literal null}.
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method.
     */
    void delete(T object) throws ServiceException;

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method
     */
    void deleteById(ID id) throws ServiceException;
}