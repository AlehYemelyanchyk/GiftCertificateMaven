package com.epam.esm.dao.impl;

import com.epam.esm.dao.TaggedGiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.model.TaggedGiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SqlTaggedGiftCertificateDAOImpl implements TaggedGiftCertificateDAO {

    private JdbcTemplate jdbcTemplate;

    private static final String FIND_TAGS_BY_CERTIFICATE_ID_SQL_QUERY =
            "SELECT c.id as id, c.name as name  " +
                    "FROM certificates as a " +
                    "JOIN tagged_certificates as b " +
                    "ON a.id_cert = b.certificate_id " +
                    "JOIN tags as c " +
                    "ON b.tag_id = c.id " +
                    "WHERE a.id_cert = ?";
    private static final String SAVE_TAGGED_CERTIFICATE_SQL_QUERY = "INSERT INTO tagged_certificates " +
            "(certificate_id, tag_id) VALUES (?, ?)";

    @Autowired
    public SqlTaggedGiftCertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findByGiftCertificateId(Long id) {
        return jdbcTemplate.query(
                FIND_TAGS_BY_CERTIFICATE_ID_SQL_QUERY,
                new Object[]{id},
                new BeanPropertyRowMapper<>(Tag.class)
        );
    }

    @Override
    public Optional<TaggedGiftCertificate> findById(Long id){
//        Optional<TaggedGiftCertificate> returnObject;
//
//        try (PreparedStatement statement = connection.prepareStatement(Constants.FIND_TAGGED_CERTIFICATE_BY_ID_SQL_QUERY)) {
//            statement.setLong(1, id);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                returnObject = DAOUtils.taggedGiftCertificatesListTwoTablesResultSetHandle(resultSet).stream()
//                        .findFirst();
//            }
//        } catch (SQLException e) {
//            throw new DAOException(e);
//        }
//        return returnObject;
        return null;
    }

    @Override
    public void save(GiftCertificate giftCertificate, Tag tag) {
        jdbcTemplate.update(
                SAVE_TAGGED_CERTIFICATE_SQL_QUERY,
                giftCertificate.getId(),
                tag.getId()
        );
    }

    @Override
    public void update(GiftCertificate giftCertificate, Tag tag){
    }

    @Override
    public void delete(GiftCertificate giftCertificate, Tag tag){
//        try {
//            try (PreparedStatement statement = connection.prepareStatement(Constants.DELETE_TAGS_CONNECTION_BY_ID_SQL_QUERY)
//            ) {
//                statement.setLong(1, giftCertificate.getId());
//                statement.setInt(2, tag.getId());
//                statement.executeUpdate();
//            } catch (SQLException e) {
//                LOGGER.error("delete transaction failed error: " + e.getMessage());
//                throw e;
//            }
//        } catch (SQLException e) {
//            throw new DAOException(e);
//        }
    }
}
