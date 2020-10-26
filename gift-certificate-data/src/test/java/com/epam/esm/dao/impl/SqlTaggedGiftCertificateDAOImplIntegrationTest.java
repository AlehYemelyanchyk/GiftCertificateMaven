package com.epam.esm.dao.impl;

import com.epam.esm.config.SpringTestDataConfig;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.Tag;
import com.epam.esm.model.TaggedGiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestDataConfig.class})
class SqlTaggedGiftCertificateDAOImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    SqlGiftCertificateDAOImpl sqlGiftCertificateDAO;

    @Autowired
    SqlTaggedGiftCertificateDAOImpl sqlTaggedGiftCertificateDAO;

    private static final TaggedGiftCertificate EXPECTED_TAGGED_GIFT_CERTIFICATE = new TaggedGiftCertificate();
    private static final Optional<TaggedGiftCertificate> EXPECTED_OPTIONAL_GIFT_CERTIFICATE =
            Optional.of(EXPECTED_TAGGED_GIFT_CERTIFICATE);
    private static final long TEST_ID = 5L;
    private final static Tag TEST_TAG_TEST = new Tag( "test");

    @BeforeEach
    void create() throws SQLException {
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setId(TEST_ID);
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setName("ABC");
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setDescription("New Year gift certificate");
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setPrice(15.99);
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setCreateDate(LocalDateTime.parse("2017-12-03T10:15:30+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setLastUpdateDate(LocalDateTime.parse("2011-12-03T10:15:30+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setDuration(31);

        executeSqlScript("/create_schema.sql");
        executeSqlScript("/import_data.sql");
    }

    @AfterEach
    void clean() throws SQLException {
        executeSqlScript("/drop_schema.sql");
    }

    @Test
    void findByIdTest() throws SQLException, DAOException {
        Connection connection = getConnection();
        Optional<TaggedGiftCertificate> actualCertificate = sqlTaggedGiftCertificateDAO.findById(TEST_ID, connection);
        assertEquals(EXPECTED_OPTIONAL_GIFT_CERTIFICATE, actualCertificate);
        connection.close();
    }

    @Test
    void saveTest() throws DAOException, SQLException {
        Connection connection = getConnection();
        List<TaggedGiftCertificate> listBeforeSave = sqlGiftCertificateDAO.findAll();
        sqlTaggedGiftCertificateDAO.save(EXPECTED_TAGGED_GIFT_CERTIFICATE, TEST_TAG_TEST, connection);
        List<TaggedGiftCertificate> listAfterSave = sqlGiftCertificateDAO.findAll();
        assertEquals(listBeforeSave, listAfterSave);
        connection.close();
    }

    @Test
    void updateTest() {
    }

    @Test
    void deleteTest() {
    }
}