package com.epam.esm.dao.impl;

import com.epam.esm.config.SpringTestDataConfig;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TaggedGiftCertificateDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.model.TaggedGiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestDataConfig.class})
class SqlTaggedGiftCertificateDAOImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    GiftCertificateDAO sqlGiftCertificateDAO;

    @Autowired
    TaggedGiftCertificateDAO sqlTaggedGiftCertificateDAO;

    private static final TaggedGiftCertificate EXPECTED_TAGGED_GIFT_CERTIFICATE = new TaggedGiftCertificate();
    private static final Optional<TaggedGiftCertificate> EXPECTED_OPTIONAL_GIFT_CERTIFICATE =
            Optional.of(EXPECTED_TAGGED_GIFT_CERTIFICATE);
    private static final long TEST_ID = 1L;
    private final static Tag TEST_TAG = new Tag("test");
    private final static Tag EXPECTED_TAG = new Tag(1, "red");

    @BeforeEach
    void create() throws SQLException {
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setId(TEST_ID);
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setName("SAS");
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setDescription("Hoho");
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setPrice(15.99);
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setCreateDate(LocalDateTime.parse("2012-12-03T10:15:30+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setLastUpdateDate(LocalDateTime.parse("2020-10-21T09:01:56.713+03:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setDuration(10);

        executeSqlScript("/create_schema.sql");
        executeSqlScript("/import_data.sql");
    }

    @AfterEach
    void clean() throws SQLException {
        executeSqlScript("/drop_schema.sql");
    }
}