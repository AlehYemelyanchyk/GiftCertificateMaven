package com.epam.esm.services.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.exceptions.DAOException;
import com.epam.esm.entity.Tag;
import com.epam.esm.services.exceptions.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private static final Tag EXPECTED_TAG = new Tag("blue");
    private static final Optional<Tag> EXPECTED_OPTIONAL_TAG = Optional.of(EXPECTED_TAG);
    private static final int TEST_ID = 1;
    private static final String TEST_NAME = "blue";
    private static final DAOException TEST_EXCEPTION = new DAOException("Test message", new RuntimeException());

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDAO tagDAO;

    @Test
    void findAllTest() throws DAOException, ServiceException {
        List<Tag> expectedList = new ArrayList<>();
        expectedList.add(EXPECTED_TAG);

        Mockito.when(tagDAO.findAll()).thenReturn(expectedList);
        List<Tag> actualList = tagService.findAll();
        assertEquals(expectedList, actualList);
    }

    @Test
    void findAllExceptionTest() throws DAOException {
        Mockito.when(tagDAO.findAll()).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> {
            tagService.findAll();
        });
    }

    @Test
    void findByIdTest() throws DAOException, ServiceException {
        Mockito.when(tagDAO.findById(TEST_ID)).thenReturn(EXPECTED_OPTIONAL_TAG);
        Optional<Tag> actualTag = tagService.findById(TEST_ID);
        assertEquals(EXPECTED_OPTIONAL_TAG, actualTag);
    }

    @Test
    void findByIdExceptionTest() throws DAOException {
        Mockito.when(tagDAO.findById(TEST_ID)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> tagService.findById(TEST_ID));
    }

    @Test
    void findByNameTest() throws DAOException, ServiceException {
        Mockito.when(tagDAO.findByName(TEST_NAME)).thenReturn(EXPECTED_OPTIONAL_TAG);
        Optional<Tag> actualTag = tagService.findByName(TEST_NAME);
        assertEquals(EXPECTED_OPTIONAL_TAG, actualTag);
    }

    @Test
    void findByNameExceptionTest() throws DAOException {
        Mockito.when(tagDAO.findByName(TEST_NAME)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> tagService.findByName(TEST_NAME));
    }

    @Test
    void saveTest() throws DAOException, ServiceException {
        Mockito.when(tagDAO.save(EXPECTED_TAG)).thenReturn(EXPECTED_OPTIONAL_TAG);
        Optional<Tag> actualTag = tagService.save(EXPECTED_TAG);
        assertEquals(EXPECTED_OPTIONAL_TAG, actualTag);
    }

    @Test
    void saveExceptionTest() throws DAOException {
        Mockito.when(tagDAO.save(EXPECTED_TAG)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> tagService.save(EXPECTED_TAG));
    }

    @Test
    void updateTest() throws DAOException, ServiceException {
        Mockito.when(tagDAO.update(EXPECTED_TAG)).thenReturn(EXPECTED_OPTIONAL_TAG);
        Optional<Tag> actualTag = tagService.update(EXPECTED_TAG);
        assertEquals(EXPECTED_OPTIONAL_TAG, actualTag);
    }

    @Test
    void updateExceptionTest() throws DAOException {
        Mockito.when(tagDAO.update(EXPECTED_TAG)).thenThrow(DAOException.class);
        assertThrows(ServiceException.class, () -> tagService.update(EXPECTED_TAG));
    }

    @Test
    void deleteInvocationTest() throws ServiceException, DAOException {
        tagService.delete(EXPECTED_TAG);
        Mockito.verify(tagDAO).delete(EXPECTED_TAG);
    }

    @Test
    void deleteExceptionTest() throws DAOException {
        Mockito.doThrow(TEST_EXCEPTION).when(tagDAO).delete(EXPECTED_TAG);
        try {
            tagService.delete(EXPECTED_TAG);
        } catch (ServiceException e) {
            assertEquals(TEST_EXCEPTION, e.getCause());
        }
    }

    @Test
    void deleteById() throws ServiceException, DAOException {
        tagService.deleteById(TEST_ID);
        Mockito.verify(tagDAO).deleteById(TEST_ID);
    }

    @Test
    void deleteByIdExceptionTest() throws DAOException {
        Mockito.doThrow(TEST_EXCEPTION).when(tagDAO).deleteById(TEST_ID);
        try {
            tagService.deleteById(TEST_ID);
        } catch (ServiceException e) {
            assertEquals(TEST_EXCEPTION, e.getCause());
        }
    }
}