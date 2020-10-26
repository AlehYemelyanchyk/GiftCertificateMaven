package com.epam.esm.dao;

import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.Tag;

import java.sql.Connection;
import java.util.Optional;

/**
 * The interface provides methods to work with Tags information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface TagDAO extends CrudDAO<Tag, Integer> {

    /**
     * Retrieves the Tag stored in a data source based on the provided Tag's name.
     *
     * @param name is the Tag's name.
     * @return the Tag with the given name or {@literal Optional#empty()} if none found.
     * @throws DAOException if an SQLException is thrown from its invoked method.
     */
    Optional<Tag> findByName(String name) throws DAOException;

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param object must not be {@literal null}.
     * @param connection must not be {@literal null}.
     * @return the saved entity or {@literal Optional#empty()} if none returned.
     * @throws DAOException if a SQLException is thrown from its invoked method.
     */
    Optional<Tag> save(Tag object, Connection connection) throws DAOException;

    /**
     * Updates a given entity. Use the returned instance for further operations as the update operation might have changed the
     * entity instance completely.
     *
     * @param object must not be {@literal null}.
     * @param connection must not be {@literal null}.
     * @return the updated entity or {@literal Optional#empty()} if none returned.
     * @throws DAOException if a SQLException is thrown from its invoked method.
     */
    Optional<Tag> update(Tag object, Connection connection) throws DAOException;
}
