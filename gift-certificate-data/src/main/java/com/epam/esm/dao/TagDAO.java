package com.epam.esm.dao;

import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.Tag;

import java.util.Optional;

public interface TagDAO extends CrudDAO<Tag, Integer> {
    Optional<Tag> findByName(String name) throws DAOException;
}
