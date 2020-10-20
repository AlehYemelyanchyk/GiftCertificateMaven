package com.epam.esm.services.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.Tag;
import com.epam.esm.services.TagService;
import com.epam.esm.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDAO tagDAO;

    @Override
    public List<Tag> findAll() throws ServiceException {
        try {
            return tagDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Tag> findById(Integer id) throws ServiceException {
        try {
            return tagDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Tag> findByName(String name) throws ServiceException {
        try {
            return tagDAO.findByName(name);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Tag> save(Tag object) throws ServiceException {
        try {
            return tagDAO.save(object);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Tag> update(Tag object) throws ServiceException {
        try {
            return tagDAO.update(object);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Tag object) throws ServiceException {
        try {
            tagDAO.delete(object);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(Integer id) throws ServiceException {
        try {
            tagDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
