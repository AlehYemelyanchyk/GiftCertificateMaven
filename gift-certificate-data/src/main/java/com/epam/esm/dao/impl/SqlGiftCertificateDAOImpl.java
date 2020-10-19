package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.DAOUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
public class SqlGiftCertificateDAOImpl implements GiftCertificateDAO {

    private static final Logger LOGGER = LogManager.getLogger();
    private final BasicDataSource dataSource;

    private static final String FIND_ALL_SQL_QUERY = "SELECT * FROM gift_certificates.certificates";
    private static final String FIND_BY_NAME_SQL_QUERY = "SELECT * FROM gift_certificates.certificates WHERE name = ?";
    private static final String FIND_BY_PART_NAME_DESCRIPTION_SQL_QUERY =
            "SELECT a.id, a.name, a.description, a.price, a.create_date, a.last_update_date, a.duration " +
                    "FROM gift_certificates.certificates as a " +
                    "WHERE a.name LIKE ? OR a.description LIKE ?";
    private static final String FIND_BY_ID_SQL_QUERY = "SELECT * FROM gift_certificates.certificates WHERE id = ?";
    private static final String FIND_ALL_GIFT_CERTIFICATES_BY_NAME_SQL_QUERY =
            "SELECT a.id, a.name, a.description, a.price, a.create_date, a.last_update_date, a.duration " +
            "FROM gift_certificates.certificates as a " +
            "JOIN gift_certificates.tagged_certificates as b " +
            "ON a.id = b.certificate_id " +
            "JOIN gift_certificates.tags as c " +
            "ON b.tag_id = c.id " +
            "WHERE c.name = ?";
    private static final String SAVE_SQL_QUERY = "INSERT INTO gift_certificates.certificates (name, description, price, create_date, last_update_date, duration) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_SQL_QUERY = "UPDATE gift_certificates.certificates SET name = ?, last_update_date = ? WHERE id = ?";
    private static final String DELETE_BY_NAME_SQL_QUERY = "DELETE FROM gift_certificates.certificates WHERE name = ?";
    private static final String DELETE_BY_ID_SQL_QUERY = "DELETE FROM gift_certificates.certificates WHERE id = ?";

    @Autowired
    public SqlGiftCertificateDAOImpl(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<GiftCertificate> findAll() throws DAOException {
        List<GiftCertificate> giftCertificates;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL_QUERY);
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                giftCertificates = DAOUtils.giftCertificatesListResultSetHandle(resultSet);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return giftCertificates;
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) throws DAOException {
        Optional<GiftCertificate> returnObject;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_SQL_QUERY);
        ) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                returnObject = DAOUtils.giftCertificatesListResultSetHandle(resultSet).stream()
                        .findFirst();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return returnObject;
    }

    @Override
    public List<GiftCertificate> findByPartNameDescription(String part) throws DAOException {
        List<GiftCertificate> returnList;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_PART_NAME_DESCRIPTION_SQL_QUERY);
        ) {
            statement.setString(1, "%"+part+"%");
            statement.setString(2, "%"+part+"%");
            try (ResultSet resultSet = statement.executeQuery()) {
                returnList = DAOUtils.giftCertificatesListResultSetHandle(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return returnList;
    }

    @Override
    public List<GiftCertificate> findAllGiftCertificatesByTagName(String name) throws DAOException {
        List<GiftCertificate> returnList;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_GIFT_CERTIFICATES_BY_NAME_SQL_QUERY)
        ) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                returnList = DAOUtils.giftCertificatesListResultSetHandle(resultSet);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return returnList;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) throws DAOException {
        Optional<GiftCertificate> returnObject;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL_QUERY);
        ) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                returnObject = DAOUtils.giftCertificatesListResultSetHandle(resultSet).stream()
                        .findFirst();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return returnObject;
    }

    @Override
    public Optional<GiftCertificate> save(GiftCertificate object) throws DAOException {
        Optional<GiftCertificate> returnObject;

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement1 = connection.prepareStatement(SAVE_SQL_QUERY);
                 PreparedStatement statement2 = connection.prepareStatement(FIND_BY_NAME_SQL_QUERY)
            ) {
                statement1.setString(1, object.getName());
                statement1.setString(2, object.getDescription());
                statement1.setDouble(3, object.getPrice());
                statement1.setString(4, formatDate());
                statement1.setString(5, formatDate());
                statement1.setInt(6, object.getDuration());
                statement1.executeUpdate();
                statement2.setString(1, object.getName());
                try (ResultSet resultSet = statement2.executeQuery()) {
                    returnObject = DAOUtils.giftCertificatesListResultSetHandle(resultSet).stream()
                            .findFirst();
                }
            } catch (SQLException e) {
                connection.rollback();
                LOGGER.error("save transaction failed error: " + e.getMessage());
                throw e;
            }
            connection.commit();

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return returnObject;
    }

    @Override
    public GiftCertificate update(GiftCertificate object) throws DAOException {
        GiftCertificate returnObject;

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_SQL_QUERY);
            ) {
                statement.setString(1, object.getName());
                statement.setString(2, formatDate());
                statement.setLong(3, object.getId());
                statement.executeUpdate();
                returnObject = object;
            } catch (SQLException e) {
                connection.rollback();
                LOGGER.error("update transaction failed error: " + e.getMessage());
                throw e;
            }
            connection.commit();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return returnObject;
    }

    @Override
    public void delete(GiftCertificate object) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_NAME_SQL_QUERY);
        ) {
            statement.setString(1, object.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteById(Long id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_SQL_QUERY);
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private String formatDate() {
        return ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
