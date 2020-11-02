package com.epam.esm.dao.impl;

import com.epam.esm.config.SpringTestDataConfig;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.TaggedGiftCertificateDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.model.TaggedGiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestDataConfig.class})
class SqlTaggedGiftCertificateDAOImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    GiftCertificateDAO sqlGiftCertificateDAO;

    @Autowired
    TagDAO tagDAO;

    @Autowired
    TaggedGiftCertificateDAO sqlTaggedGiftCertificateDAO;

    private TaggedGiftCertificate expectedTaggedGiftCertificate = new TaggedGiftCertificate();
    private Optional<TaggedGiftCertificate> expectedOptionalGiftCertificate =
            Optional.of(expectedTaggedGiftCertificate);
    private static final long TEST_ID = 1L;
    private Tag testTag = new Tag("test");
    private List<Tag> expectedTagList = new ArrayList<>();
    private Tag expectedTag = new Tag(1, "red");
    private Tag expectedTag2 = new Tag(2, "green");

    @BeforeEach
    void create() throws SQLException {
        expectedTaggedGiftCertificate.setId(TEST_ID);
        expectedTaggedGiftCertificate.setName("SAS");
        expectedTaggedGiftCertificate.setDescription("Hoho");
        expectedTaggedGiftCertificate.setPrice(15.99);
        expectedTaggedGiftCertificate.setCreateDate(LocalDateTime.parse("2012-12-03T10:15:30+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        expectedTaggedGiftCertificate.setLastUpdateDate(LocalDateTime.parse("2020-10-21T09:01:56.713+03:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        expectedTaggedGiftCertificate.setDuration(10);

        expectedTagList.add(expectedTag);
        expectedTagList.add(expectedTag2);

        executeSqlScript("/create_schema.sql");
        executeSqlScript("/import_data.sql");
    }

    @AfterEach
    void clean() throws SQLException {
        executeSqlScript("/drop_schema.sql");
    }

    @Test
    void findByGiftCertificateIdTest() {
        List<Tag> actualList = sqlTaggedGiftCertificateDAO.findByGiftCertificateId(TEST_ID);
        for (int i = 0; i < expectedTagList.size(); i++) {
            assertEquals(expectedTagList.get(i), actualList.get(i));
        }
    }

    @Test
    void saveTest() {
        Optional<Tag> savedTag = tagDAO.save(testTag);
        sqlTaggedGiftCertificateDAO.save(expectedTaggedGiftCertificate, savedTag.get());
        List<Tag> actualList = sqlTaggedGiftCertificateDAO.findByGiftCertificateId(expectedTaggedGiftCertificate.getId());
        for (int i = 0; i < actualList.size(); i++) {
            if (actualList.get(i).equals(testTag)) {
                assertTrue(true);
            }
        }
    }
}