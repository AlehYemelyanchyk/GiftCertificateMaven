package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @Autowired
    private GiftCertificateDAO giftCertificateDAO;

    @Override
    public List<GiftCertificate> findAll() throws ServiceException {
        try {
            return giftCertificateDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) throws ServiceException {
        try {
            return giftCertificateDAO.findByName(name);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<GiftCertificate> findAllGiftCertificatesByTagName(String name) throws ServiceException {
        try {
            return giftCertificateDAO.findAllGiftCertificatesByTagName(name);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) throws ServiceException {
        try {
            return giftCertificateDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<GiftCertificate> save(GiftCertificate object) throws ServiceException {
        try {
            return giftCertificateDAO.save(object);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public GiftCertificate update(GiftCertificate object) throws ServiceException {
        try {
            return giftCertificateDAO.update(object);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(GiftCertificate object) throws ServiceException {
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
