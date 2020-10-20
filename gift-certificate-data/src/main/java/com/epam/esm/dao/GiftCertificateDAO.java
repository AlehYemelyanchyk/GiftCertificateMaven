package com.epam.esm.dao;

import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDAO extends CrudDAO<GiftCertificate, Long> {
    List<GiftCertificate> findAllWithSort(String sortBy, String sortOrder) throws DAOException;

    List<GiftCertificate> findByName(String name) throws DAOException;

    List<GiftCertificate> findByNameWithSort(String name, String sortBy, String sortOrder) throws DAOException;

    List<GiftCertificate> findByPartNameDescription(String part) throws DAOException;

    List<GiftCertificate> findAllGiftCertificatesByTagName(String name) throws DAOException;

}
