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
            TaggedGiftCertificate tempCertificate = new TaggedGiftCertificate(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    LocalDateTime.parse(resultSet.getString("create_date"), DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    LocalDateTime.parse(resultSet.getString("last_update_date"), DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    resultSet.getInt("duration"));
            certificates.add(tempCertificate);
        }
        return certificates;
    }

    public static List<TaggedGiftCertificate> taggedGiftCertificatesListTwoTablesResultSetHandle(ResultSet resultSet) throws SQLException {
        Set<TaggedGiftCertificate> taggedGiftCertificatesSet = new LinkedHashSet<>();

        while (resultSet.next()) {
            TaggedGiftCertificate tempCertificate = new TaggedGiftCertificate(
                    resultSet.getLong("a.id"),
                    resultSet.getString("a.name"),
                    resultSet.getString("a.description"),
                    resultSet.getDouble("a.price"),
                    LocalDateTime.parse(resultSet.getString("a.create_date"), DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    LocalDateTime.parse(resultSet.getString("a.last_update_date"), DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                    resultSet.getInt("a.duration"));

            Tag tempTag = new Tag(
                    resultSet.getInt("c.id"),
                    resultSet.getString("c.name"));

            if (!taggedGiftCertificatesSet.contains(tempCertificate)) {
                tempCertificate.getTags().add(tempTag);
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
