package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;
import com.epam.esm.services.exceptions.ServiceException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    private static final TaggedGiftCertificate EXPECTED_TAGGED_GIFT_CERTIFICATE = new TaggedGiftCertificate();
    private static final SearchParametersHolder TEST_SEARCH_PARAMETERS_HOLDER = new SearchParametersHolder();

    private static final Optional<TaggedGiftCertificate> EXPECTED_OPTIONAL_GIFT_CERTIFICATE =
            Optional.of(EXPECTED_TAGGED_GIFT_CERTIFICATE);
    private static final long TEST_ID = 1L;
    private static final DAOException TEST_EXCEPTION = new DAOException("Test message", new RuntimeException());

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateDAO giftCertificateDAO;

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
    void findAllTest() throws DAOException, ServiceException {
        List<TaggedGiftCertificate> expectedList = new ArrayList<>();
        expectedList.add(EXPECTED_TAGGED_GIFT_CERTIFICATE);

        Mockito.when(giftCertificateDAO.findAll()).thenReturn(expectedList);
        List<TaggedGiftCertificate> actualList = giftCertificateService.findAll();
        assertEquals(expectedList, actualList);
    }

    @Test
    void findAllExceptionTest() throws DAOException {
        Mockito.when(giftCertificateDAO.findAll()).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.findAll());
    }

    @Test
    void findByTest() throws DAOException, ServiceException {
        List<TaggedGiftCertificate> expectedList = new ArrayList<>();
        expectedList.add(EXPECTED_TAGGED_GIFT_CERTIFICATE);

        Mockito.when(giftCertificateDAO.findBy(TEST_SEARCH_PARAMETERS_HOLDER)).thenReturn(expectedList);
        List<TaggedGiftCertificate> actualList = giftCertificateService.findBy(TEST_SEARCH_PARAMETERS_HOLDER);
        assertEquals(expectedList, actualList);

    }

    @Test
    void findByExceptionTest() throws DAOException {
        Mockito.when(giftCertificateDAO.findBy(TEST_SEARCH_PARAMETERS_HOLDER)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.findBy(TEST_SEARCH_PARAMETERS_HOLDER));
    }

    @Test
    void findByIdTest() throws DAOException, ServiceException {
        Mockito.when(giftCertificateDAO.findById(TEST_ID)).thenReturn(EXPECTED_OPTIONAL_GIFT_CERTIFICATE);
        Optional<TaggedGiftCertificate> actualGiftCertificate = giftCertificateService.findById(TEST_ID);
        assertEquals(EXPECTED_OPTIONAL_GIFT_CERTIFICATE, actualGiftCertificate);
    }

    @Test
    void findByIdExceptionTest() throws DAOException {
        Mockito.when(giftCertificateDAO.findById(TEST_ID)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.findById(TEST_ID));
    }

    @Test
    void saveTest() throws DAOException, ServiceException {
        Mockito.when(giftCertificateDAO.save(EXPECTED_TAGGED_GIFT_CERTIFICATE)).thenReturn(EXPECTED_OPTIONAL_GIFT_CERTIFICATE);
        Optional<TaggedGiftCertificate> actualGiftCertificate = giftCertificateService.save(EXPECTED_TAGGED_GIFT_CERTIFICATE);
        assertEquals(EXPECTED_OPTIONAL_GIFT_CERTIFICATE, actualGiftCertificate);
    }

    @Test
    void saveExceptionTest() throws DAOException {
        Mockito.when(giftCertificateDAO.save(EXPECTED_TAGGED_GIFT_CERTIFICATE)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.save(EXPECTED_TAGGED_GIFT_CERTIFICATE));
    }

    @Test
    void updateUpdate() throws DAOException, ServiceException {
        Mockito.when(giftCertificateDAO.update(EXPECTED_TAGGED_GIFT_CERTIFICATE)).thenReturn(EXPECTED_OPTIONAL_GIFT_CERTIFICATE);
        Optional<TaggedGiftCertificate> actualGiftCertificate = giftCertificateService.update(EXPECTED_TAGGED_GIFT_CERTIFICATE);
        assertEquals(EXPECTED_OPTIONAL_GIFT_CERTIFICATE, actualGiftCertificate);
    }

    @Test
    void updateExceptionTest() throws DAOException {
        Mockito.when(giftCertificateDAO.update(EXPECTED_TAGGED_GIFT_CERTIFICATE)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> giftCertificateService.update(EXPECTED_TAGGED_GIFT_CERTIFICATE));
    }

    @Test
    void deleteInvocationTest() throws DAOException, ServiceException {
        giftCertificateService.delete(EXPECTED_TAGGED_GIFT_CERTIFICATE);
        Mockito.verify(giftCertificateDAO).delete(EXPECTED_TAGGED_GIFT_CERTIFICATE);
    }

    @Test
    void deleteExceptionTest() throws DAOException {
        Mockito.doThrow(TEST_EXCEPTION).when(giftCertificateDAO).delete(EXPECTED_TAGGED_GIFT_CERTIFICATE);
        try {
            giftCertificateService.delete(EXPECTED_TAGGED_GIFT_CERTIFICATE);
        } catch (ServiceException e) {
            assertEquals(TEST_EXCEPTION, e.getCause());
        }
    }

    @Test
    void deleteByIdInvocationTest() throws ServiceException, DAOException {
        giftCertificateService.deleteById(TEST_ID);
        Mockito.verify(giftCertificateDAO).deleteById(TEST_ID);
    }

    @Test
    void deleteByIdExceptionTest() throws DAOException {
        Mockito.doThrow(TEST_EXCEPTION).when(giftCertificateDAO).deleteById(TEST_ID);
        try {
            giftCertificateService.deleteById(TEST_ID);
        } catch (ServiceException e) {
            assertEquals(TEST_EXCEPTION, e.getCause());
        }
    }
}