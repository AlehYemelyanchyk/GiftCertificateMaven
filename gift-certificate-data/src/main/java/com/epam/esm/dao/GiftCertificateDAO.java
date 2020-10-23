package com.epam.esm.dao;

import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;

import java.util.List;

public interface GiftCertificateDAO extends CrudDAO<TaggedGiftCertificate, Long> {

    List<TaggedGiftCertificate> findBy(SearchParametersHolder searchParametersHolder) throws DAOException;
}
