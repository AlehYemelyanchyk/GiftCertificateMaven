package com.epam.esm.dao;

import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDAO extends CrudDAO<GiftCertificate, Long> {
    Optional<GiftCertificate> findByName(String name) throws DAOException;

    List<GiftCertificate> findByPartNameDescription(String part) throws DAOException;

    List<GiftCertificate> findAllGiftCertificatesByTagName(String name) throws DAOException;
}
