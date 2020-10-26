package com.epam.esm.util;

import com.epam.esm.entity.Tag;
import com.epam.esm.model.TaggedGiftCertificate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class DAOUtils {

    public static List<Tag> tagsListResultSetHandle(ResultSet resultSet) throws SQLException {
        List<Tag> users = new ArrayList<>();
        while (resultSet.next()) {
            Tag tempTag = new Tag(
                    resultSet.getInt("id"),
                    resultSet.getString("name"));
            users.add(tempTag);
        }
        return users;
    }

    public static List<TaggedGiftCertificate> taggedGiftCertificatesListResultSetHandle(ResultSet resultSet) throws SQLException {
        List<TaggedGiftCertificate> certificates = new ArrayList<>();
        while (resultSet.next()) {
            TaggedGiftCertificate tempCertificate = new TaggedGiftCertificate();
            tempCertificate.setId(resultSet.getLong("id_cert"));
            tempCertificate.setName(resultSet.getString("name"));
            tempCertificate.setDescription(resultSet.getString("description"));
            tempCertificate.setPrice(resultSet.getDouble("price"));
            tempCertificate.setCreateDate(LocalDateTime.parse(resultSet.getString("create_date"), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            tempCertificate.setLastUpdateDate(LocalDateTime.parse(resultSet.getString("last_update_date"), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            tempCertificate.setDuration(resultSet.getInt("duration"));
            certificates.add(tempCertificate);
        }
        return certificates;
    }

    public static List<TaggedGiftCertificate> taggedGiftCertificatesListTwoTablesResultSetHandle(ResultSet resultSet) throws SQLException {
        Set<TaggedGiftCertificate> taggedGiftCertificatesSet = new LinkedHashSet<>();

        while (resultSet.next()) {
            TaggedGiftCertificate tempCertificate = new TaggedGiftCertificate();
            tempCertificate.setId(resultSet.getLong("id_cert"));
            tempCertificate.setName(resultSet.getString("name"));
            tempCertificate.setDescription(resultSet.getString("description"));
            tempCertificate.setPrice(resultSet.getDouble("price"));
            tempCertificate.setCreateDate(LocalDateTime.parse(resultSet.getString("create_date"), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            tempCertificate.setLastUpdateDate(LocalDateTime.parse(resultSet.getString("last_update_date"), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            tempCertificate.setDuration(resultSet.getInt("duration"));

            Tag tempTag = new Tag(
                    resultSet.getInt("id_tag"),
                    resultSet.getString("name_tag"));

            if (!taggedGiftCertificatesSet.contains(tempCertificate)) {
                if (tempTag.getName() != null) {
                    tempCertificate.getTags().add(tempTag);
                }
                taggedGiftCertificatesSet.add(tempCertificate);
            } else {
                for (TaggedGiftCertificate nextCertificate : taggedGiftCertificatesSet) {
                    if (tempCertificate.equals(nextCertificate)) {
                        nextCertificate.getTags().add(tempTag);
                    }
                }
            }
        }
        return new ArrayList<>(taggedGiftCertificatesSet);
    }
}
