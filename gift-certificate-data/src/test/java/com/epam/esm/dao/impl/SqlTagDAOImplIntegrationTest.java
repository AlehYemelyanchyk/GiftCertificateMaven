package com.epam.esm.dao.impl;

import com.epam.esm.config.SpringTestDataConfig;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestDataConfig.class})
class SqlTagDAOImplIntegrationTest extends AbstractIntegrationTest {

    private final static Tag EXPECTED_TAG = new Tag(1, "red");
    private final static Tag TEST_TAG_RED = new Tag(1, "red");
    private final static Tag TEST_TAG_TEST = new Tag( "test");

    @Autowired
    private TagDAO sqlTagDAO;

    @BeforeEach
    void init() throws SQLException {
        executeSqlScript("/create_schema.sql");
        executeSqlScript("/import_data.sql");
    }

    @AfterEach
    void clean() throws SQLException {
        executeSqlScript("/drop_schema.sql");
    }

    @Test
    void findAllTest() throws DAOException {
        List<Tag> actualList = sqlTagDAO.findAll();
        assertNotNull(actualList);
    }

    @Test
    void findByIdTest() throws DAOException {
        Optional<Tag> optionalTag = sqlTagDAO.findById(EXPECTED_TAG.getId());
        Tag actualTag = optionalTag.orElse(null);
        assertEquals(EXPECTED_TAG, actualTag);
    }

    @Test
    void findByNameTest() throws DAOException {
        Optional<Tag> optionalTag = sqlTagDAO.findByName(EXPECTED_TAG.getName());
        Tag actualTag = optionalTag.orElse(null);
        assertEquals(EXPECTED_TAG, actualTag);
    }

    @Test
    void saveTest() throws DAOException, SQLException {
        Connection connection = getConnection();
        sqlTagDAO.save(TEST_TAG_TEST);
        Optional<Tag> optionalTag = sqlTagDAO.findByName(TEST_TAG_TEST.getName());
        Tag actualTag = optionalTag.orElse(null);
        assertEquals(TEST_TAG_TEST, actualTag);
        connection.close();
    }

    @Test
    void saveWithConnectionTest() throws DAOException, SQLException {
        Connection connection = getConnection();
        sqlTagDAO.save(TEST_TAG_TEST);
        Optional<Tag> optionalTag = sqlTagDAO.findByName(TEST_TAG_TEST.getName());
        Tag actualTag = optionalTag.orElse(null);
        assertEquals(TEST_TAG_TEST, actualTag);
        connection.close();
    }

    @Test
    void updateTest() throws DAOException, SQLException {
        Connection connection = getConnection();
        Optional<Tag> optionalTag = sqlTagDAO.update(TEST_TAG_TEST);
        Tag actualTag = optionalTag.orElse(null);
        assertNull(actualTag);
        connection.close();
    }

    @Test
    void deleteTest() throws DAOException {
        sqlTagDAO.delete(TEST_TAG_RED);
        Optional<Tag> optionalTag = sqlTagDAO.findByName(TEST_TAG_RED.getName());
        Tag actualTag = optionalTag.orElse(null);
        assertNull(actualTag);
    }

    @Test
    void deleteByIdTest() throws DAOException {
        sqlTagDAO.deleteById(TEST_TAG_RED.getId());
        Optional<Tag> optionalTag = sqlTagDAO.findByName(TEST_TAG_RED.getName());
        Tag actualTag = optionalTag.orElse(null);
        assertNull(actualTag);
    }
}