package com.epam.esm.dao.impl;

import com.epam.esm.config.SpringTestDataConfig;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.model.SearchParametersHolder;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestDataConfig.class})
class SqlGiftCertificateDAOImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    GiftCertificateDAO sqlGiftCertificateDAO;

    private static final TaggedGiftCertificate EXPECTED_TAGGED_GIFT_CERTIFICATE = new TaggedGiftCertificate();
    private static final SearchParametersHolder TEST_SEARCH_PARAMETERS_HOLDER = new SearchParametersHolder();
    private static final Optional<TaggedGiftCertificate> EXPECTED_OPTIONAL_GIFT_CERTIFICATE =
            Optional.of(EXPECTED_TAGGED_GIFT_CERTIFICATE);
    private static final long TEST_ID = 5L;

    @BeforeEach
    void create() throws SQLException {
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setId(TEST_ID);
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setName("ABC");
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setDescription("New Year gift certificate");
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setPrice(15.99);
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setCreateDate(LocalDateTime.parse("2017-12-03T10:15:30+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setLastUpdateDate(LocalDateTime.parse("2011-12-03T10:15:30+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setDuration(31);

        TEST_SEARCH_PARAMETERS_HOLDER.setTagName("red");
        TEST_SEARCH_PARAMETERS_HOLDER.setName("ABC");
        TEST_SEARCH_PARAMETERS_HOLDER.setDescription("New Year gift certificate");
        TEST_SEARCH_PARAMETERS_HOLDER.setSortBy("name");
        TEST_SEARCH_PARAMETERS_HOLDER.setSortOrder("desc");
        executeSqlScript("/create_schema.sql");
        executeSqlScript("/import_data.sql");
    }

    @AfterEach
    void clean() throws SQLException {
        executeSqlScript("/drop_schema.sql");
    }

    @Test
    void findAllTest() {
        List<TaggedGiftCertificate> actualList = sqlGiftCertificateDAO.findAll();
        assertNotNull(actualList);
    }

    @Test
    void findByIdTest() {
        Optional<TaggedGiftCertificate> actualCertificate = sqlGiftCertificateDAO.findById(TEST_ID);
        assertEquals(EXPECTED_OPTIONAL_GIFT_CERTIFICATE, actualCertificate);
    }

    @Test
    void saveTest() throws SQLException{
        Connection connection = getConnection();
        List<TaggedGiftCertificate> listBefore = sqlGiftCertificateDAO.findAll();
        Optional<TaggedGiftCertificate> actualCertificate = sqlGiftCertificateDAO.save(EXPECTED_TAGGED_GIFT_CERTIFICATE);
        List<TaggedGiftCertificate> listAfter = sqlGiftCertificateDAO.findAll();
        assertNotNull(actualCertificate);
        assertNotEquals(listBefore, listAfter);
        connection.close();
    }

    @Test
    void updateTest() throws SQLException{
        Connection connection = getConnection();
        Optional<TaggedGiftCertificate> beforeUpdate = sqlGiftCertificateDAO.findById(TEST_ID);
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setName("New name");
        Optional<TaggedGiftCertificate> actualCertificate = sqlGiftCertificateDAO.update(EXPECTED_TAGGED_GIFT_CERTIFICATE);
        Optional<TaggedGiftCertificate> afterUpdate = sqlGiftCertificateDAO.findById(TEST_ID);
        assertNotNull(actualCertificate);
        assertNotEquals(beforeUpdate, afterUpdate);
        connection.close();
    }

    @Test
    void deleteTest() {
        sqlGiftCertificateDAO.delete(EXPECTED_TAGGED_GIFT_CERTIFICATE);
        Optional<TaggedGiftCertificate> actualCertificate = sqlGiftCertificateDAO.findById(EXPECTED_TAGGED_GIFT_CERTIFICATE.getId());
        assertNull(actualCertificate.orElse(null));
    }

    @Test
    void deleteByIdTest() {
        sqlGiftCertificateDAO.deleteById(TEST_ID);
        Optional<TaggedGiftCertificate> actualCertificate = sqlGiftCertificateDAO.findById(EXPECTED_TAGGED_GIFT_CERTIFICATE.getId());
        assertNull(actualCertificate.orElse(null));
    }
}