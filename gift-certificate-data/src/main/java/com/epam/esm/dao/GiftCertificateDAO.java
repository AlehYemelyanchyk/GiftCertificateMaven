package com.epam.esm.dao;

import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;

import java.util.List;

public interface GiftCertificateDAO extends CrudDAO<GiftCertificate, Long> {
    List<GiftCertificate> findAllGiftCertificatesByTagName(String name) throws DAOException;

    List<TaggedGiftCertificate> searchBy(SearchParametersHolder searchParametersHolder) throws DAOException;
}
