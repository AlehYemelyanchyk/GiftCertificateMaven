package com.epam.esm.dao.impl;

import com.epam.esm.config.SpringTestDataConfig;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestDataConfig.class})
class SqlTagDAOImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private SqlTagDAOImpl sqlTagDAO;

    @BeforeEach
    void init() throws SQLException {
        executeSqlScript("/create_schema.sql");
    }

    @AfterEach
    void clean() throws SQLException {
        executeSqlScript("/drop_schema.sql");
    }

    @Test
    void testTest() throws SQLException, DAOException {
        executeSqlScript("/import_data.sql");
        Tag expectedTag = new Tag("reed");
        Optional<Tag> optionalTag = sqlTagDAO.findByName("red");
        Tag actualTag = optionalTag.orElse(null);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void testTest2() {
        System.out.println("Test 2 called");
    }
}