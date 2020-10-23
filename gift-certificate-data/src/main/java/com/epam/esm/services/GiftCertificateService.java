package com.epam.esm.services;

import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;
import com.epam.esm.services.exceptions.ServiceException;

import java.util.List;

public interface GiftCertificateService extends CrudService<TaggedGiftCertificate, Long> {

    List<TaggedGiftCertificate> findBy(SearchParametersHolder searchParametersHolder) throws ServiceException;

}
