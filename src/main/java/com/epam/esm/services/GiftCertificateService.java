package com.epam.esm.services;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.services.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService extends CrudService<GiftCertificate, Long> {
    Optional<GiftCertificate> findByName(String name) throws ServiceException;

    List<GiftCertificate> findAllGiftCertificatesByTagName(String name) throws ServiceException;
}
