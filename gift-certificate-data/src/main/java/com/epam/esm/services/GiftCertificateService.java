package com.epam.esm.services;

import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;
import com.epam.esm.services.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService extends CrudService<TaggedGiftCertificate, Long> {

    List<TaggedGiftCertificate> findBy(SearchParametersHolder searchParametersHolder) throws ServiceException;

    Optional<TaggedGiftCertificate> save(TaggedGiftCertificate object) throws ServiceException;

    Optional<TaggedGiftCertificate> update(TaggedGiftCertificate object) throws ServiceException;

}
