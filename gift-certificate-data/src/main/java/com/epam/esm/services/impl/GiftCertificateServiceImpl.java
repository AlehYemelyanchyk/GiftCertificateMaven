package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDAO giftCertificateDAO;
    private DAORepositoryManager daoManager;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, DAORepositoryManager daoManager) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.daoManager = daoManager;
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
            return daoManager.save(object);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<TaggedGiftCertificate> update(TaggedGiftCertificate object) throws ServiceException {
        try {
            return daoManager.update(object);
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
