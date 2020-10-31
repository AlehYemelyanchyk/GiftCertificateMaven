package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.TaggedGiftCertificateDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.Tag;
import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private TagDAO tagDAO;
    private GiftCertificateDAO giftCertificateDAO;
    private TaggedGiftCertificateDAO taggedGiftCertificateDAO;

    @Autowired
    public GiftCertificateServiceImpl(
            GiftCertificateDAO giftCertificateDAO,
            TaggedGiftCertificateDAO taggedGiftCertificateDAO,
            TagDAO tagDAO
    ) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.taggedGiftCertificateDAO = taggedGiftCertificateDAO;
        this.tagDAO = tagDAO;
    }

    @Transactional
    @Override
    public List<TaggedGiftCertificate> findAll() throws ServiceException {
        List<TaggedGiftCertificate> taggedGiftCertificates = giftCertificateDAO.findAll();
        taggedGiftCertificates.forEach(this::getAllTags);
        return taggedGiftCertificates;
    }

    @Transactional
    @Override
    public List<TaggedGiftCertificate> findBy(SearchParametersHolder searchParametersHolder) throws ServiceException {
        List<TaggedGiftCertificate> taggedGiftCertificates;
        try {
            taggedGiftCertificates = giftCertificateDAO.findBy(searchParametersHolder);
            taggedGiftCertificates.forEach(this::getAllTags);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return taggedGiftCertificates;
    }

    @Transactional
    @Override
    public Optional<TaggedGiftCertificate> findById(Long id) throws ServiceException {
        Optional<TaggedGiftCertificate> taggedGiftCertificate = giftCertificateDAO.findById(id);
        taggedGiftCertificate.ifPresent(this::getAllTags);
        return taggedGiftCertificate;
    }

    @Transactional
    @Override
    public Optional<TaggedGiftCertificate> save(TaggedGiftCertificate object) throws ServiceException {
        Optional<TaggedGiftCertificate> optionalTaggedGiftCertificate;
        try {
            optionalTaggedGiftCertificate = giftCertificateDAO.save(object);
            TaggedGiftCertificate taggedGiftCertificate = optionalTaggedGiftCertificate.orElseThrow(() ->
                    new DAOException("Failed to save the certificate."));
            List<Tag> passedTags = object.getTags();
            passedTags.forEach(tag -> {
                Optional<Tag> savedTag = tagDAO.findByName(tag.getName());
                if (!savedTag.isPresent()) {
                    savedTag = tagDAO.save(tag);
                }
                savedTag.ifPresent(value -> taggedGiftCertificateDAO.save(taggedGiftCertificate, value));
            });
            optionalTaggedGiftCertificate.ifPresent(this::getAllTags);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return optionalTaggedGiftCertificate;
    }

    @Transactional
    @Override
    public Optional<TaggedGiftCertificate> update(TaggedGiftCertificate object) throws ServiceException {
        Optional<TaggedGiftCertificate> optionalTaggedGiftCertificate;
        try {
            optionalTaggedGiftCertificate = giftCertificateDAO.update(object);
            TaggedGiftCertificate taggedGiftCertificate = optionalTaggedGiftCertificate.orElseThrow(() ->
                    new DAOException("Failed to save the certificate."));
            List<Tag> passedTags = object.getTags();
            if (passedTags != null) {
                passedTags.forEach(tag -> {
                    Optional<Tag> savedTag = tagDAO.findByName(tag.getName());
                    if (!savedTag.isPresent()) {
                        savedTag = tagDAO.save(tag);
                    }
                    savedTag.ifPresent(value -> taggedGiftCertificateDAO.save(taggedGiftCertificate, value));
                });
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        optionalTaggedGiftCertificate.ifPresent(this::getAllTags);
        return optionalTaggedGiftCertificate;
    }

    @Transactional
    @Override
    public void delete(TaggedGiftCertificate object) throws ServiceException {
        giftCertificateDAO.delete(object);
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws ServiceException {
        giftCertificateDAO.deleteById(id);
    }

    private void getAllTags(TaggedGiftCertificate taggedGiftCertificates) {
        List<Tag> tags = taggedGiftCertificateDAO.findByGiftCertificateId(taggedGiftCertificates.getId());
        taggedGiftCertificates.setTags(tags);
    }
}
