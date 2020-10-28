package com.epam.esm.dao;

import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * The interface provides methods to work with GiftCertificate information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface GiftCertificateDAO extends CrudDAO<TaggedGiftCertificate, Long> {

    /**
     * Returns all instances of the TaggedGiftCertificate type.
     *
     * @param searchParametersHolder is an object which contains all the field used in a search.
     * @return List with all entities found with the search.
     * @throws DAOException if an SQLException is thrown from its invoked method.
     */
    List<TaggedGiftCertificate> findBy(SearchParametersHolder searchParametersHolder) throws DAOException;

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param object must not be {@literal null}.
     * @param connection must not be {@literal null}.
     * @return the saved entity or {@literal Optional#empty()} if none returned.
     * @throws DAOException if a SQLException is thrown from its invoked method.
     */
    Optional<TaggedGiftCertificate> save(TaggedGiftCertificate object, Connection connection) throws DAOException;

    /**
     * Updates a given entity. Use the returned instance for further operations as the update operation might have changed the
     * entity instance completely.
     *
     * @param object must not be {@literal null}.
     * @param connection must not be {@literal null}.
     * @return the updated entity or {@literal Optional#empty()} if none returned.
     * @throws DAOException if a SQLException is thrown from its invoked method.
     */
    Optional<TaggedGiftCertificate> update(TaggedGiftCertificate object, Connection connection) throws DAOException;
}
