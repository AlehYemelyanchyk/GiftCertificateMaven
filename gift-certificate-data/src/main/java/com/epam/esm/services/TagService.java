package com.epam.esm.services;

import com.epam.esm.entity.Tag;
import com.epam.esm.services.exceptions.ServiceException;

import java.util.Optional;

/**
 * The interface provides methods to work with Tags information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface TagService extends CrudService<Tag, Integer> {

    /**
     * Retrieves the Tag stored in a data source based on the provided Tag's name.
     *
     * @param name is the Tag's name.
     * @return the Tag with the given name or {@literal Optional#empty()} if none found.
     * @throws ServiceException if a DAOException is thrown from its invoked DAO level method.
     */
    Optional<Tag> findByName(String name) throws ServiceException;
}
