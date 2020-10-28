package com.epam.esm.services;

import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;
import com.epam.esm.services.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * The interface provides methods to work with TaggedGiftCertificate information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface GiftCertificateService extends CrudService<TaggedGiftCertificate, Long> {

    /**
     * Returns all instances of the TaggedGiftCertificate type.
     *
     * @param searchParametersHolder is an object which contains all the field used in a search.
     * @return List with all entities found with the search.
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method.
     */
    List<TaggedGiftCertificate> findBy(SearchParametersHolder searchParametersHolder) throws ServiceException;

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param object must not be {@literal null}.
     * @return the saved entity or {@literal Optional#empty()} if none returned.
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method.
     */
    Optional<TaggedGiftCertificate> save(TaggedGiftCertificate object) throws ServiceException;

    /**
     * Updates a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param object must not be {@literal null}.
     * @return the updated entity or {@literal Optional#empty()} if none returned.
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method.
     */
    Optional<TaggedGiftCertificate> update(TaggedGiftCertificate object) throws ServiceException;

}
