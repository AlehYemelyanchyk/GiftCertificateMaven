package com.epam.esm.dao.impl;

import com.epam.esm.dao.TaggedGiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public void save(GiftCertificate giftCertificate, Tag tag) {
        jdbcTemplate.update(
                SAVE_TAGGED_CERTIFICATE_SQL_QUERY,
                giftCertificate.getId(),
                tag.getId()
        );
    }
}
