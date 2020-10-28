package com.epam.esm.dao.impl;

import com.epam.esm.dao.Constants;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;
import com.epam.esm.util.DAOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
    private final DataSource dataSource;

    @Autowired
    public SqlGiftCertificateDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<TaggedGiftCertificate> findAll() throws DAOException {
        List<TaggedGiftCertificate> giftCertificates;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.FIND_ALL_CERTIFICATES_SQL_QUERY)
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                giftCertificates = DAOUtils.taggedGiftCertificatesListResultSetHandle(resultSet);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return giftCertificates;
    }

    @Override
    public List<TaggedGiftCertificate> findBy(SearchParametersHolder searchParametersHolder) throws DAOException {
        List<TaggedGiftCertificate> returnList;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(searchByRequestBuilder(searchParametersHolder))
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                returnList = DAOUtils.taggedGiftCertificatesListTwoTablesResultSetHandle(resultSet);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return returnList;
    }

    @Override
    public Optional<TaggedGiftCertificate> findById(Long id) throws DAOException {
        Optional<TaggedGiftCertificate> returnObject;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.FIND_CERTIFICATE_BY_ID_SQL_QUERY);
        ) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                returnObject = DAOUtils.taggedGiftCertificatesListResultSetHandle(resultSet).stream()
                        .findFirst();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return returnObject;
    }

    @Override
    public Optional<TaggedGiftCertificate> save(TaggedGiftCertificate object) throws DAOException {
        return Optional.empty();
    }

    @Override
    public Optional<TaggedGiftCertificate> save(TaggedGiftCertificate object, Connection connection) throws DAOException {
        Optional<TaggedGiftCertificate> returnObject;

            try (PreparedStatement statement1 = connection.prepareStatement(Constants.SAVE_CERTIFICATE_SQL_QUERY);
                 PreparedStatement statement2 = connection.prepareStatement(Constants.FIND_LAST_CERTIFICATES_BY_NAME_SQL_QUERY);
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
                    returnObject = DAOUtils.taggedGiftCertificatesListResultSetHandle(resultSet).stream()
                            .findFirst();
                }
            } catch (SQLException e) {
                LOGGER.error("save transaction failed error: " + e.getMessage());
                throw new DAOException(e);
            }
        return returnObject;
    }

    @Override
    public Optional<TaggedGiftCertificate> update(TaggedGiftCertificate object) throws DAOException {
        return Optional.empty();
    }

    @Override
    public Optional<TaggedGiftCertificate> update(TaggedGiftCertificate object, Connection connection) throws DAOException {
        Optional<TaggedGiftCertificate> returnObject;

        try (PreparedStatement statement1 = connection.prepareStatement(updateSqlRequestBuilder(object))) {
                statement1.executeUpdate();
            SearchParametersHolder searchParametersHolder = new SearchParametersHolder();
            searchParametersHolder.setId(object.getId());
            returnObject = findBy(searchParametersHolder).stream().findFirst();
            } catch (SQLException e) {
                LOGGER.error("update transaction failed error: " + e.getMessage());
            throw new DAOException(e);
        }
        return returnObject;
    }

    @Override
    public void delete(TaggedGiftCertificate object) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_CERTIFICATES_BY_NAME_SQL_QUERY);
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
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_CERTIFICATE_BY_ID_SQL_QUERY);
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private String updateSqlRequestBuilder(GiftCertificate giftCertificate) {
        String updatePart = "UPDATE certificates SET ";
        String wherePart = " WHERE id_cert = " + giftCertificate.getId();

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
                "SELECT a.id_cert, a.name, a.description, a.price, a.create_date, a.last_update_date, a.duration, c.id as id_tag, c.name as name_tag " +
                        "FROM certificates as a " +
                        "LEFT OUTER JOIN tagged_certificates as b " +
                        "ON a.id_cert = b.certificate_id " +
                        "LEFT OUTER JOIN tags as c " +
                        "ON b.tag_id = c.id";
        String orderPart = " ORDER BY a.";
        Long id = searchParametersHolder.getId();
        String tagName = searchParametersHolder.getTagName();
        String name = searchParametersHolder.getName();
        String description = searchParametersHolder.getDescription();
        String sortBy = searchParametersHolder.getSortBy();
        String sortOrder = searchParametersHolder.getSortOrder();

        StringBuilder sqlRequest = new StringBuilder();
        sqlRequest.append(requestBegin);
        sqlRequest.append((id == null) ? "" : " WHERE a.id_cert = " + id);
        sqlRequest.append((tagName == null) ? "" : " WHERE c.name LIKE '%" + tagName + "%'");
        sqlRequest.append((name == null) ? "" : " WHERE a.name LIKE '%" + name + "%'");
        sqlRequest.append((description == null) ? "" : " WHERE a.description LIKE '%" + description + "%'");
        sqlRequest.append((sortBy == null) ? "" : orderPart + sortBy);
        sqlRequest.append((sortOrder == null) ? "" : " " + sortOrder);

        return sqlRequest.toString();
    }

    private String formatDate() {
        return ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
