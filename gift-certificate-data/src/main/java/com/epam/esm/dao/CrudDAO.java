package com.epam.esm.dao;

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
     */
    List<T> findAll();

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     */
    Optional<T> findById(ID id);

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param object must not be {@literal null}.
     * @return the saved entity or {@literal Optional#empty()} if none returned.
     */
    Optional<T> save(T object);

    /**
     * Updates a given entity. Use the returned instance for further operations as the update operation might have changed the
     * entity instance completely.
     *
     * @param object must not be {@literal null}.
     * @return the updated entity or {@literal Optional#empty()} if none returned.
     */
    Optional<T> update(T object);

    /**
     * Deletes a given entity.
     *
     * @param object must not be {@literal null}.
     */
    void delete(T object);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     */
    void deleteById(ID id);
}