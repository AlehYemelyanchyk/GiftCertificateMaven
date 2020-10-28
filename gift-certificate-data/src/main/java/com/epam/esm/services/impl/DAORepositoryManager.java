package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.TaggedGiftCertificateDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.Tag;
import com.epam.esm.model.TaggedGiftCertificate;
import com.epam.esm.services.RepositoryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

@Repository
public class DAORepositoryManager implements RepositoryManager<TaggedGiftCertificate> {

    private static final Logger LOGGER = LogManager.getLogger();
    private final DataSource dataSource;
    private GiftCertificateDAO giftCertificateDAO;
    private TagDAO tagDAO;
    private TaggedGiftCertificateDAO taggedGiftCertificateDAO;

    public DAORepositoryManager(DataSource dataSource, GiftCertificateDAO giftCertificateDAO,
                                TagDAO tagDAO, TaggedGiftCertificateDAO taggedGiftCertificateDAO) {
        this.dataSource = dataSource;
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
        this.taggedGiftCertificateDAO = taggedGiftCertificateDAO;
    }

    @Override
    public Optional<TaggedGiftCertificate> save(TaggedGiftCertificate object) throws DAOException {
        Optional<TaggedGiftCertificate> returnObject;

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Optional<TaggedGiftCertificate> returnOptionalCertificate = giftCertificateDAO.save(object, connection);
                TaggedGiftCertificate returnCertificate = returnOptionalCertificate.orElseThrow(() ->
                        new SQLException("DB returned null certificate"));
                Set<Tag> tags = object.getTags();
                saveTags(tags, returnCertificate, connection);
                returnObject = taggedGiftCertificateDAO.findById(returnCertificate.getId(), connection);
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
            connection.commit();
        } catch (Exception e) {
            LOGGER.error("save transaction failed error: " + e.getMessage());
            throw new DAOException(e);
        }
        return returnObject;
    }

    @Override
    public Optional<TaggedGiftCertificate> update(TaggedGiftCertificate object) throws DAOException {
        Optional<TaggedGiftCertificate> returnObject;

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Optional<TaggedGiftCertificate> returnOptionalCertificate = giftCertificateDAO.update(object, connection);
                TaggedGiftCertificate returnCertificate = returnOptionalCertificate.orElseThrow(() ->
                        new SQLException("DB returned null certificate"));
                Set<Tag> tags = returnCertificate.getTags();
                deleteTagsConnection(tags, returnCertificate, connection);
                if (object.getTags() != null) {
                    tags.addAll(object.getTags());
                }
                saveTags(tags, returnCertificate, connection);
                returnObject = taggedGiftCertificateDAO.findById(returnCertificate.getId(), connection);
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
            connection.commit();
        } catch (Exception e) {
            LOGGER.error("update transaction failed error: " + e.getMessage());
            throw new DAOException(e);
        }
        return returnObject;
    }

    private void saveTags(Set<Tag> tags, TaggedGiftCertificate returnCertificate, Connection connection) throws SQLException, DAOException {
        for (Tag tag : tags) {
            Optional<Tag> returnOptionalTag = tagDAO.save(tag, connection);
            Tag returnTag = returnOptionalTag.orElseThrow(() ->
                    new SQLException("DB returned null tag"));
            taggedGiftCertificateDAO.save(returnCertificate, returnTag, connection);
        }
    }

    private void deleteTagsConnection(Set<Tag> tags, TaggedGiftCertificate returnCertificate, Connection connection) throws SQLException, DAOException {
        for (Tag tag : tags) {
            taggedGiftCertificateDAO.delete(returnCertificate, tag, connection);
        }
    }
}
