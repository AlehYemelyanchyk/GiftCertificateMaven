package com.epam.esm.services;

import com.epam.esm.entity.Tag;
import com.epam.esm.services.exceptions.ServiceException;

import java.util.Optional;

public interface TagService extends CrudService<Tag, Integer> {
    Optional<Tag> findByName(String name) throws ServiceException;
}
