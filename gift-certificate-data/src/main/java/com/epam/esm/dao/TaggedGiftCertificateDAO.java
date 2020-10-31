package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;

/**
 * The interface provides methods to work with GiftCertificate information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface TaggedGiftCertificateDAO {

    /**
     * Returns all tags, stored in a data source and connected to the Gift Certificate based on the
     * provided Gift Certificate's id.
     *
     * @param id is the Gift Certificate's id.
     * @return all entities.
     */
    List<Tag> findByGiftCertificateId(Long id);

    /**
     * Saves a given entity. Returns nothing.
     *
     * @param giftCertificate must not be {@literal null}.
     * @param tag             must not be {@literal null}.
     */
    void save(GiftCertificate giftCertificate, Tag tag);
}
