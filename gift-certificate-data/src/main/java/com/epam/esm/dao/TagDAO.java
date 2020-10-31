package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.Optional;

/**
 * The interface provides methods to work with Tags information in
 * a data source.
 *
 * @author Aleh Yemelyanchyk
 */
public interface TagDAO extends CrudDAO<Tag, Integer> {

    /**
     * Retrieves the Tag stored in a data source based on the provided Tag's name.
     *
     * @param name is the Tag's name.
     * @return the Tag with the given name or {@literal Optional#empty()} if none found.
     */
    Optional<Tag> findByName(String name);
}
