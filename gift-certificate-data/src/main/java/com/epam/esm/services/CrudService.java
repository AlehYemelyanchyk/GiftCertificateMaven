package com.epam.esm.services;

import com.epam.esm.services.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    List<T> findAll() throws ServiceException;

    Optional<T> findById(ID id) throws ServiceException;

    Optional<T> save(T object) throws ServiceException;

    Optional<T> update(T object) throws ServiceException;

    void delete(T object) throws ServiceException;

    void deleteById(ID id) throws ServiceException;
}