package com.epam.esm.dao.impl;

import com.epam.esm.dao.Constants;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.Tag;
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
import java.util.List;
import java.util.Optional;

@Repository
public class SqlTagDAOImpl implements TagDAO {

    private static final Logger LOGGER = LogManager.getLogger();
    private final BasicDataSource dataSource;

    @Autowired
    public SqlTagDAOImpl(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Tag> findAll() throws DAOException {
        List<Tag> returnList;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.FIND_ALL_TAGS_SQL_QUERY);
        ) {
            try (ResultSet resultSet = statement.executeQuery()) {
                returnList = DAOUtils.tagsListResultSetHandle(resultSet);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return returnList;
    }

    @Override
    public Optional<Tag> findById(Integer id) throws DAOException {
        Optional<Tag> returnObject;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.FIND_TAGS_BY_ID_SQL_QUERY);
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                returnObject = DAOUtils.tagsListResultSetHandle(resultSet).stream()
                        .findFirst();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return returnObject;
    }

    @Override
    public Optional<Tag> findByName(String name) throws DAOException {
        Optional<Tag> returnObject;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.FIND_TAGS_BY_NAME_SQL_QUERY)
        ) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                returnObject = DAOUtils.tagsListResultSetHandle(resultSet).stream()
                        .findFirst();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return returnObject;
    }

    @Override
    public Optional<Tag> save(Tag object, Connection connection) throws DAOException {
        Optional<Tag> returnObject;

        try (PreparedStatement statementFindByName = connection.prepareStatement(Constants.FIND_TAGS_BY_NAME_SQL_QUERY)) {
            try (PreparedStatement statementSave = connection.prepareStatement(Constants.SAVE_TAGS_SQL_QUERY)) {
                statementSave.setString(1, object.getName());
                statementSave.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("save transaction failed error: " + e.getMessage());
            }
                statementFindByName.setString(1, object.getName());
                try (ResultSet resultSet = statementFindByName.executeQuery()) {
                    returnObject = DAOUtils.tagsListResultSetHandle(resultSet).stream()
                            .findFirst();
                }
            } catch (SQLException e) {
            LOGGER.error("find transaction failed error: " + e.getMessage());
            throw new DAOException(e);
            }
        return returnObject;
    }

    @Override
    public Optional<Tag> update(Tag object) throws DAOException {
        Optional<Tag> returnObject;

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (
                    PreparedStatement statement1 = connection.prepareStatement(Constants.UPDATE_TAGS_SQL_QUERY);
                    PreparedStatement statement2 = connection.prepareStatement(Constants.FIND_TAGS_BY_ID_SQL_QUERY)
            ) {
                statement1.setString(1, object.getName());
                statement1.setInt(2, object.getId());
                statement1.executeUpdate();
                statement2.setInt(1, object.getId());
                try (ResultSet resultSet = statement2.executeQuery()) {
                    returnObject = DAOUtils.tagsListResultSetHandle(resultSet).stream()
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
    public void delete(Tag object) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_TAGS_BY_NAME_SQL_QUERY);
        ) {
            statement.setString(1, object.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteById(Integer id) throws DAOException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(Constants.DELETE_TAGS_BY_ID_SQL_QUERY);
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
