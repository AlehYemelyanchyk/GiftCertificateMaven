package com.epam.esm.services.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.services.TagService;
import com.epam.esm.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public List<Tag> findAll() throws ServiceException {
            return tagDAO.findAll();
    }

    @Override
    public Optional<Tag> findById(Integer id) throws ServiceException {
            return tagDAO.findById(id);
    }

    @Transactional
    @Override
    public Optional<Tag> save(Tag object) throws ServiceException {
            return tagDAO.save(object);
    }

    @Override
    public Optional<Tag> update(Tag object) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public Optional<Tag> findByName(String name) throws ServiceException {
            return tagDAO.findByName(name);
    }

    @Override
    public void delete(Tag object) throws ServiceException {
            tagDAO.delete(object);
    }

    @Override
    public void deleteById(Integer id) throws ServiceException {
            tagDAO.deleteById(id);
    }
}
