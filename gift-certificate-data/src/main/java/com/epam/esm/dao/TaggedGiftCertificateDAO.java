package com.epam.esm.dao;

import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.model.TaggedGiftCertificate;

import java.sql.Connection;
import java.util.Optional;

public interface TaggedGiftCertificateDAO {

    Optional<TaggedGiftCertificate> findById(Long id, Connection connection) throws DAOException;

    void save(GiftCertificate giftCertificate, Tag tag, Connection connection)
            throws DAOException;

    void update(GiftCertificate giftCertificate, Tag tag, Connection connection)
            throws DAOException;
}
