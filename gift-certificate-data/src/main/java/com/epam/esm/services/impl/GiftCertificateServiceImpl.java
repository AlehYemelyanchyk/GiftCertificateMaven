package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.StorageService;
import com.epam.esm.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDAO giftCertificateDAO;
    private StorageService<Connection> storageService;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, StorageService<Connection> storageService) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.storageService = storageService;
    }

    @Override
    public List<TaggedGiftCertificate> findAll() throws ServiceException {
        try {
            return giftCertificateDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TaggedGiftCertificate> findBy(SearchParametersHolder searchParametersHolder) throws ServiceException {
        try {
            return giftCertificateDAO.findBy(searchParametersHolder);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<TaggedGiftCertificate> findById(Long id) throws ServiceException {
        try {
            return giftCertificateDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<TaggedGiftCertificate> save(TaggedGiftCertificate object) throws ServiceException {
        try {
            List<TaggedGiftCertificate> taggedGiftCertificates = new ArrayList<>();
//            return storageService.execute(o -> giftCertificateDAO.save(object, o));
            return storageService.execute(o -> {
                List<Optional<TaggedGiftCertificate>> optionals = new ArrayList<>();
                for (TaggedGiftCertificate taggedGiftCertificate : taggedGiftCertificates) {
                    Optional<TaggedGiftCertificate> save = giftCertificateDAO.save(taggedGiftCertificate, o);
                    optionals.add(save);
                }
            });
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<TaggedGiftCertificate> update(TaggedGiftCertificate object) throws ServiceException {
        try {
            return giftCertificateDAO.update(object);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(TaggedGiftCertificate object) throws ServiceException {
        try {
            giftCertificateDAO.delete(object);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        try {
            giftCertificateDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
