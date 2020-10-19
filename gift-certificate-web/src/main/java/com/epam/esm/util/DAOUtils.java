package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public static List<GiftCertificate> giftCertificatesListResultSetHandle(ResultSet resultSet) throws SQLException {
        List<GiftCertificate> certificates = new ArrayList<>();
        while (resultSet.next()) {
            GiftCertificate tempCertificate = new GiftCertificate(
                    resultSet.getInt("id"),
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
}
