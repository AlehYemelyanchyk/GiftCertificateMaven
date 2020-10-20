package com.epam.esm.services;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.services.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService extends CrudService<GiftCertificate, Long> {
    List<GiftCertificate> findWithParameters(String name, String sortBy, String sortOrder) throws ServiceException;

    List<GiftCertificate> findByPartNameDescription(String part) throws ServiceException;

    List<GiftCertificate> findAllGiftCertificatesByTagName(String name) throws ServiceException;

    Optional<GiftCertificate> updateWithParameters(
            Integer id, String name, String description, Double price, Integer duration) throws ServiceException;
}
