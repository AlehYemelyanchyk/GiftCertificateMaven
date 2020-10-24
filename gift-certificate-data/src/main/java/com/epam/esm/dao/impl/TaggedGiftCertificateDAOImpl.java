package com.epam.esm.dao.impl;

import com.epam.esm.dao.Constants;
import com.epam.esm.dao.TaggedGiftCertificateDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.model.TaggedGiftCertificate;
import com.epam.esm.util.DAOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class TaggedGiftCertificateDAOImpl implements TaggedGiftCertificateDAO {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Optional<TaggedGiftCertificate> findById(Long id, Connection connection) throws DAOException {
        Optional<TaggedGiftCertificate> returnObject;

        try (PreparedStatement statement = connection.prepareStatement(Constants.FIND_TAGGED_CERTIFICATE_BY_ID_SQL_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                returnObject = DAOUtils.taggedGiftCertificatesListTwoTablesResultSetHandle(resultSet).stream()
                        .findFirst();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return returnObject;
    }

    @Override
    public void save(GiftCertificate giftCertificate, Tag tag, Connection connection)
            throws DAOException {
        try {
            try (PreparedStatement statement = connection.prepareStatement(Constants.SAVE_TAGGED_CERTIFICATE_SQL_QUERY);
            ) {
                statement.setLong(1, giftCertificate.getId());
                statement.setInt(2, tag.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("save transaction failed error: " + e.getMessage());
                throw e;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(GiftCertificate giftCertificate, Tag tag, Connection connection) throws DAOException {
    }
}
