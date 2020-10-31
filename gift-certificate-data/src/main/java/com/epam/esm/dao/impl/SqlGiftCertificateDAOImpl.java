package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.model.SearchParametersHolder;
import com.epam.esm.model.TaggedGiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
public class SqlGiftCertificateDAOImpl implements GiftCertificateDAO {

    private JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL_CERTIFICATES_SQL_QUERY = "SELECT * FROM certificates";
    private static final String FIND_CERTIFICATE_BY_ID_SQL_QUERY = "SELECT * FROM certificates " +
            "WHERE id_cert = ?";
    private static final String SAVE_CERTIFICATE_SQL_QUERY = "INSERT INTO certificates " +
            "(name, description, price, create_date, last_update_date, duration) VALUES (?,?,?,?,?,?)";
    private static final String FIND_LAST_CERTIFICATES_BY_NAME_SQL_QUERY = "SELECT * FROM certificates " +
            "WHERE name = ? ORDER BY create_date DESC";
    private static final String DELETE_CERTIFICATES_BY_NAME_SQL_QUERY = "DELETE FROM certificates " +
            "WHERE name = ?";
    private static final String DELETE_CERTIFICATE_BY_ID_SQL_QUERY = "DELETE FROM certificates " +
            "WHERE id_cert = ?";

    @Autowired
    public SqlGiftCertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TaggedGiftCertificate> findAll() {
        return jdbcTemplate.query(
                FIND_ALL_CERTIFICATES_SQL_QUERY,
                this::mapCertificatesResultSet
        );
    }

    @Override
    public List<TaggedGiftCertificate> findBy(SearchParametersHolder searchParametersHolder) {
        return jdbcTemplate.query(
                searchByRequestBuilder(searchParametersHolder),
                this::mapCertificatesResultSet
        );
    }

    @Override
    public Optional<TaggedGiftCertificate> findById(Long id) {
        return jdbcTemplate.query(
                FIND_CERTIFICATE_BY_ID_SQL_QUERY,
                new Object[]{id},
                this::mapCertificatesResultSet
        ).stream().findFirst();
    }

    @Override
    public Optional<TaggedGiftCertificate> save(TaggedGiftCertificate object) {
        jdbcTemplate.update(
                SAVE_CERTIFICATE_SQL_QUERY,
                object.getName(),
                object.getDescription(),
                object.getPrice(),
                formatDate(),
                formatDate(),
                object.getDuration()
        );
        return jdbcTemplate.query(
                FIND_LAST_CERTIFICATES_BY_NAME_SQL_QUERY,
                new Object[]{object.getName()},
                this::mapCertificatesResultSet
        ).stream().findFirst();
    }


    @Override
    public Optional<TaggedGiftCertificate> update(TaggedGiftCertificate object) {
        jdbcTemplate.update(
                updateSqlRequestBuilder(object)
        );
        return findById(object.getId());
    }

    @Override
    public void delete(TaggedGiftCertificate object) {
        jdbcTemplate.update(
                DELETE_CERTIFICATES_BY_NAME_SQL_QUERY,
                object.getName()
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(
                DELETE_CERTIFICATE_BY_ID_SQL_QUERY,
                id
        );
    }

    private TaggedGiftCertificate mapCertificatesResultSet(ResultSet rs, int rowNum) throws SQLException {
        TaggedGiftCertificate tempCertificate = new TaggedGiftCertificate();
        tempCertificate.setId(rs.getLong("id_cert"));
        tempCertificate.setName(rs.getString("name"));
        tempCertificate.setDescription(rs.getString("description"));
        tempCertificate.setPrice(rs.getDouble("price"));
        tempCertificate.setCreateDate(LocalDateTime.parse(rs.getString("create_date"), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        tempCertificate.setLastUpdateDate(LocalDateTime.parse(rs.getString("last_update_date"), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        tempCertificate.setDuration(rs.getInt("duration"));
        return tempCertificate;
    }

    private String updateSqlRequestBuilder(GiftCertificate giftCertificate) {
        String updatePart = "UPDATE certificates SET ";
        String wherePart = " WHERE id_cert = " + giftCertificate.getId();

        String name = giftCertificate.getName();
        String description = giftCertificate.getDescription();
        Double price = giftCertificate.getPrice();
        String lastUpdateDate = formatDate();
        Integer duration = giftCertificate.getDuration();

        StringBuilder sqlRequest = new StringBuilder();
        sqlRequest.append(updatePart);
        sqlRequest.append((name == null) ? "" : " name = '" + name + "',");
        sqlRequest.append((description == null) ? "" : " description = '" + description + "',");
        sqlRequest.append((price == null) ? "" : " price = '" + price + "',");
        sqlRequest.append(" last_update_date = '").append(lastUpdateDate).append("',");
        sqlRequest.append((duration == null) ? "" : " duration = '" + duration + "',");
        sqlRequest.deleteCharAt(sqlRequest.length() - 1);
        sqlRequest.append(wherePart);

        return sqlRequest.toString();
    }

    private String searchByRequestBuilder(SearchParametersHolder searchParametersHolder) {
        String requestBegin =
                "SELECT a.id_cert, a.name, a.description, a.price, a.create_date, a.last_update_date, a.duration, c.id as id_tag, c.name as name_tag " +
                        "FROM certificates as a " +
                        "LEFT OUTER JOIN tagged_certificates as b " +
                        "ON a.id_cert = b.certificate_id " +
                        "LEFT OUTER JOIN tags as c " +
                        "ON b.tag_id = c.id";
        String orderPart = " ORDER BY a.";
        Long id = searchParametersHolder.getId();
        String tagName = searchParametersHolder.getTagName();
        String name = searchParametersHolder.getName();
        String description = searchParametersHolder.getDescription();
        String sortBy = searchParametersHolder.getSortBy();
        String sortOrder = searchParametersHolder.getSortOrder();

        StringBuilder sqlRequest = new StringBuilder();
        sqlRequest.append(requestBegin);
        sqlRequest.append((id == null) ? "" : " WHERE a.id_cert = " + id);
        sqlRequest.append((tagName == null) ? "" : " WHERE c.name LIKE '%" + tagName + "%'");
        sqlRequest.append((name == null) ? "" : " WHERE a.name LIKE '%" + name + "%'");
        sqlRequest.append((description == null) ? "" : " WHERE a.description LIKE '%" + description + "%'");
        sqlRequest.append((sortBy == null) ? "" : orderPart + sortBy);
        sqlRequest.append((sortOrder == null) ? "" : " " + sortOrder);

        return sqlRequest.toString();
    }

    private String formatDate() {
        return ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
