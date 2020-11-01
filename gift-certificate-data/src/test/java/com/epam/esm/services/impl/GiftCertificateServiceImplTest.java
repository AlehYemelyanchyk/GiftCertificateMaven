package com.epam.esm.services.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TaggedGiftCertificateDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;
import com.epam.esm.services.exceptions.ServiceException;
import org.junit.jupiter.api.BeforeEach;
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

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    private TaggedGiftCertificate expectedTaggedGiftCertificate = new TaggedGiftCertificate();
    private TaggedGiftCertificate expectedTaggedGiftCertificate2 = new TaggedGiftCertificate();
    private SearchParametersHolder testSearchParametersHolder = new SearchParametersHolder();

    private Optional<TaggedGiftCertificate> expectedOptionalGiftCertificate =
            Optional.of(expectedTaggedGiftCertificate);
    private Optional<TaggedGiftCertificate> expectedOptionalGiftCertificate2 =
            Optional.of(expectedTaggedGiftCertificate2);
    private Long id = 1L;
    private Tag expectedTag = new Tag("blue");
    private Tag expectedTag2 = new Tag("red");
    private static final long TEST_ID = 1L;
    private static final long TEST_ID2 = 2L;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private TaggedGiftCertificateDAO taggedGiftCertificateDAO;

    @BeforeEach
    public void init() {
        setGiftCertificateValues(expectedTaggedGiftCertificate);
        setGiftCertificateValues(expectedTaggedGiftCertificate2);

        testSearchParametersHolder.setTagName("Test tag name");
        testSearchParametersHolder.setName("Test name");
        testSearchParametersHolder.setDescription("Test description");
        testSearchParametersHolder.setSortBy("name");
        testSearchParametersHolder.setSortOrder("desc");
    }

    private void setGiftCertificateValues(TaggedGiftCertificate taggedGiftCertificate) {
        taggedGiftCertificate.setId(id++);
        taggedGiftCertificate.setName("Test");
        taggedGiftCertificate.setDescription("This is a test cert");
        taggedGiftCertificate.setPrice(9.99);
        taggedGiftCertificate.setCreateDate(LocalDateTime.parse("2012-12-03T10:15:30+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        taggedGiftCertificate.setLastUpdateDate(LocalDateTime.parse("2012-12-03T10:15:30+01:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        taggedGiftCertificate.setDuration(31);
    }

    @Test
    void findAllListTest() throws ServiceException {
        List<TaggedGiftCertificate> expectedList = new ArrayList<>();

        Mockito.when(giftCertificateDAO.findAll()).thenReturn(expectedList);
        List<TaggedGiftCertificate> actualList = giftCertificateService.findAll();
        assertEquals(expectedList, actualList);
    }

    @Test
    void findAllTagsTest() throws ServiceException {
        List<TaggedGiftCertificate> expectedList = new ArrayList<>();
        expectedList.add(expectedTaggedGiftCertificate);
        expectedList.add(expectedTaggedGiftCertificate2);
        List<Tag> expectedTagList = new ArrayList<>();
        List<Tag> expectedTagList2 = new ArrayList<>();
        expectedTagList.add(expectedTag);
        expectedTagList.add(expectedTag2);
        expectedTagList2.add(expectedTag);
        expectedTagList2.add(expectedTag2);


        Mockito.when(giftCertificateDAO.findAll()).thenReturn(expectedList);
        Mockito.when(taggedGiftCertificateDAO.findByGiftCertificateId(TEST_ID)).thenReturn(expectedTagList);
        Mockito.when(taggedGiftCertificateDAO.findByGiftCertificateId(TEST_ID2)).thenReturn(expectedTagList2);
        List<TaggedGiftCertificate> actualList = giftCertificateService.findAll();
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i).getTags(), actualList.get(i).getTags());
        }
    }

    @Test
    void findByTest() throws ServiceException {
        List<TaggedGiftCertificate> expectedList = new ArrayList<>();
        expectedList.add(expectedTaggedGiftCertificate);

        Mockito.when(giftCertificateDAO.findBy(testSearchParametersHolder)).thenReturn(expectedList);
        List<TaggedGiftCertificate> actualList = giftCertificateService.findBy(testSearchParametersHolder);
        assertEquals(expectedList, actualList);

    }

    @Test
    void findByIdTest() throws ServiceException {
        Mockito.when(giftCertificateDAO.findById(TEST_ID)).thenReturn(expectedOptionalGiftCertificate);
        Optional<TaggedGiftCertificate> actualGiftCertificate = giftCertificateService.findById(TEST_ID);
        assertEquals(expectedOptionalGiftCertificate, actualGiftCertificate);
    }

    @Test
    void saveTest() throws ServiceException {
        Mockito.when(giftCertificateDAO.save(expectedTaggedGiftCertificate)).thenReturn(expectedOptionalGiftCertificate);
        Optional<TaggedGiftCertificate> actualGiftCertificate = giftCertificateService.save(expectedTaggedGiftCertificate);
        assertEquals(expectedOptionalGiftCertificate, actualGiftCertificate);
    }

    @Test
    void updateUpdate() throws ServiceException {
        Mockito.when(giftCertificateDAO.update(expectedTaggedGiftCertificate)).thenReturn(expectedOptionalGiftCertificate);
        Optional<TaggedGiftCertificate> actualGiftCertificate = giftCertificateService.update(expectedTaggedGiftCertificate);
        assertEquals(expectedOptionalGiftCertificate, actualGiftCertificate);
    }

    @Test
    void deleteInvocationTest() throws ServiceException {
        giftCertificateService.delete(expectedTaggedGiftCertificate);
        Mockito.verify(giftCertificateDAO).delete(expectedTaggedGiftCertificate);
    }

    @Test
    void deleteByIdInvocationTest() throws ServiceException {
        giftCertificateService.deleteById(TEST_ID);
        Mockito.verify(giftCertificateDAO).deleteById(TEST_ID);
    }
}