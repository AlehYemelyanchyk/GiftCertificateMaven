package com.epam.esm.dao;

import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;

import java.util.List;

/**
 * The interface provides methods to work with GiftCertificate information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface GiftCertificateDAO extends CrudDAO<TaggedGiftCertificate, Long> {

    /**
     * Returns all instances of the TaggedGiftCertificate type.
     *
     * @param searchParametersHolder is an object which contains all the field used in a search.
     * @return List with all entities found with the search.
     * @throws DAOException if an SQLException is thrown from its invoked method.
     */
    List<TaggedGiftCertificate> findBy(SearchParametersHolder searchParametersHolder) throws DAOException;
}
