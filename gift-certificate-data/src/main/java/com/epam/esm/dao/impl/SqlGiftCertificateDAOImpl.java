package com.epam.esm.dao.impl;

import com.epam.esm.dao.Constants;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;
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

    @Autowired
    public SqlGiftCertificateDAOImpl(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<GiftCertificate> findAll() throws DAOException {
        List<GiftCertificate> giftCertificates;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.FIND_ALL_SQL_QUERY)
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
    public List<GiftCertificate> findAllGiftCertificatesByTagName(String name) throws DAOException {
        List<GiftCertificate> returnList;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.FIND_ALL_GIFT_CERTIFICATES_BY_NAME_SQL_QUERY)
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
    public List<TaggedGiftCertificate> searchBy(SearchParametersHolder searchParametersHolder) throws DAOException {
        List<TaggedGiftCertificate> returnList;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(searchByRequestBuilder(searchParametersHolder))
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                returnList = DAOUtils.taggedGiftCertificatesListResultSetHandle(resultSet);
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
             PreparedStatement statement = connection.prepareStatement(Constants.FIND_BY_ID_SQL_QUERY);
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
            try (PreparedStatement statement1 = connection.prepareStatement(Constants.SAVE_SQL_QUERY);
                 PreparedStatement statement2 = connection.prepareStatement(Constants.FIND_BY_NAME_SQL_QUERY)
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
    public Optional<GiftCertificate> update(GiftCertificate object) throws DAOException {
        Optional<GiftCertificate> returnObject;

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement1 =
                         connection.prepareStatement(updateSqlRequestBuilder(object));
                 PreparedStatement statement2 = connection.prepareStatement(Constants.FIND_BY_ID_SQL_QUERY)
            ) {
                statement1.executeUpdate();
                statement2.setLong(1, object.getId());
                try (ResultSet resultSet = statement2.executeQuery()) {
                    returnObject = DAOUtils.giftCertificatesListResultSetHandle(resultSet).stream()
                            .findFirst();
                }
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
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_BY_NAME_SQL_QUERY);
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
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_BY_ID_SQL_QUERY);
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private String updateSqlRequestBuilder(GiftCertificate giftCertificate) {
        String updatePart = "UPDATE gift_certificates.certificates SET ";
        String wherePart = " WHERE id = " + giftCertificate.getId();

        String name = giftCertificate.getName();
        String description = giftCertificate.getDescription();
        Double price = giftCertificate.getPrice();
        String lastUpdateDate = formatDate();
        Integer duration = giftCertificate.getDuration();

        StringBuilder sqlRequest = new StringBuilder();
        sqlRequest.append(updatePart);
        sqlRequest.append((name == null) ? "" : " name = '" + name + "',");
        sqlRequest.append((description == null) ? "" : " description = '" + description + "',");
        sqlRequest.append((price == null) ? "" : " price = '" + price + "',");
        sqlRequest.append(" last_update_date = '").append(lastUpdateDate).append("',");
        sqlRequest.append((duration == null) ? "" : " duration = '" + duration + "',");
        sqlRequest.deleteCharAt(sqlRequest.length() - 1);
        sqlRequest.append(wherePart);

        return sqlRequest.toString();
    }

    private String searchByRequestBuilder(SearchParametersHolder searchParametersHolder) {
        String requestBegin =
                "SELECT a.id, a.name, a.description, a.price, a.create_date, a.last_update_date, a.duration, c.id, c.name " +
                        "FROM gift_certificates.certificates as a " +
                        "JOIN gift_certificates.tagged_certificates as b " +
                        "ON a.id = b.certificate_id " +
                        "JOIN gift_certificates.tags as c " +
                        "ON b.tag_id = c.id";
        String orderPart = " ORDER BY ";
        String name = searchParametersHolder.getName();
        String tagName = searchParametersHolder.getTagName();
        String sortBy = searchParametersHolder.getSortBy();
        String sortOrder = searchParametersHolder.getSortOrder();

        StringBuilder sqlRequest = new StringBuilder();
        sqlRequest.append(requestBegin);
        sqlRequest.append((name == null) ? "" : " WHERE a.name LIKE '%" + name + "%'");
        sqlRequest.append((sortBy == null) ? "" : orderPart + sortBy);
        sqlRequest.append((sortOrder == null) ? "" : " " + sortOrder);

        return sqlRequest.toString();
    }

    private String formatDate() {
        return ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
