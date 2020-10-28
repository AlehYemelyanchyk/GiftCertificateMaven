package com.epam.esm.dao;

import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.model.TaggedGiftCertificate;

import java.sql.Connection;
import java.util.Optional;

/**
 * The interface provides methods to work with GiftCertificate information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface TaggedGiftCertificateDAO {

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found.
     * @throws DAOException if an SQLException is thrown from its invoked method.
     */
    Optional<TaggedGiftCertificate> findById(Long id, Connection connection) throws DAOException;

    /**
     * Saves a given entity. Returns nothing.
     *
     * @param giftCertificate must not be {@literal null}.
     * @param tag             must not be {@literal null}.
     * @param connection      must not be {@literal null}.
     * @throws DAOException if a SQLException is thrown from its invoked method.
     */
    void save(GiftCertificate giftCertificate, Tag tag, Connection connection)
            throws DAOException;

    /**
     * Updates a given entity. Returns nothing.
     *
     * @param giftCertificate must not be {@literal null}.
     * @param tag must not be {@literal null}.
     * @param connection must not be {@literal null}.
     * @throws DAOException if a SQLException is thrown from its invoked method.
     */
    void update(GiftCertificate giftCertificate, Tag tag, Connection connection)
            throws DAOException;

    /**
     * Deletes a given entity. Returns nothing.
     *
     * @param giftCertificate must not be {@literal null}.
     * @param tag must not be {@literal null}.
     * @param connection must not be {@literal null}.
     * @throws DAOException if a SQLException is thrown from its invoked method.
     */
    void delete(GiftCertificate giftCertificate, Tag tag, Connection connection)
            throws DAOException;
}
