package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.TaggedGiftCertificateDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DAORepositoryManagerTest {

    @Mock
    private DAORepositoryManager daoManager;

    @Mock
    private GiftCertificateDAO giftCertificateDAO;

    @Mock
    private TagDAO tagDAO;

    @Mock
    private TaggedGiftCertificateDAO taggedGiftCertificateDAO;

    private static final TaggedGiftCertificate EXPECTED_TAGGED_GIFT_CERTIFICATE = new TaggedGiftCertificate();
    private static final SearchParametersHolder TEST_SEARCH_PARAMETERS_HOLDER = new SearchParametersHolder();

    private static final Optional<TaggedGiftCertificate> EXPECTED_OPTIONAL_GIFT_CERTIFICATE =
            Optional.of(EXPECTED_TAGGED_GIFT_CERTIFICATE);
    private static final long TEST_ID = 1;
    private static final DAOException TEST_EXCEPTION = new DAOException("Test message", new RuntimeException());

    @Before
    public void init() {
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setName("Test");
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setDescription("This is a test cert");
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setPrice(9.99);
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setCreateDate(LocalDateTime.parse("2012-12-03T10:15:30+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setLastUpdateDate(LocalDateTime.parse("2012-12-03T10:15:30+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        EXPECTED_TAGGED_GIFT_CERTIFICATE.setDuration(31);

        TEST_SEARCH_PARAMETERS_HOLDER.setTagName("Test tag name");
        TEST_SEARCH_PARAMETERS_HOLDER.setName("Test name");
        TEST_SEARCH_PARAMETERS_HOLDER.setDescription("Test description");
        TEST_SEARCH_PARAMETERS_HOLDER.setSortBy("name");
        TEST_SEARCH_PARAMETERS_HOLDER.setSortOrder("desc");
    }

    @Test
    void saveTest() throws DAOException {
        Mockito.when(giftCertificateDAO.save(EXPECTED_TAGGED_GIFT_CERTIFICATE, null)).thenReturn(EXPECTED_OPTIONAL_GIFT_CERTIFICATE);
        Mockito.when(taggedGiftCertificateDAO.findById(TEST_ID, null)).thenReturn(EXPECTED_OPTIONAL_GIFT_CERTIFICATE);
        Optional<TaggedGiftCertificate> actualGiftCertificate = daoManager.save(EXPECTED_TAGGED_GIFT_CERTIFICATE);
        assertEquals(EXPECTED_OPTIONAL_GIFT_CERTIFICATE, actualGiftCertificate);
    }

    @Test
    void update() {
    }
}