package com.epam.esm.dao;

public final class Constants {

    public static final String SAVE_TAGGED_CERTIFICATE_SQL_QUERY = "INSERT INTO tagged_certificates " +
            "(certificate_id, tag_id) VALUES (?, ?)";
    public static final String FIND_TAGGED_CERTIFICATE_BY_ID_SQL_QUERY =
            "SELECT a.id_cert, a.name, a.description, a.price, a.create_date, a.last_update_date, a.duration, c.id as id_tag, c.name as name_tag " +
                    "FROM certificates as a " +
                    "JOIN tagged_certificates as b " +
                    "ON a.id_cert = b.certificate_id " +
                    "JOIN tags as c " +
                    "ON b.tag_id = c.id " +
                    "WHERE a.id_cert = ?";
    public static final String DELETE_TAGS_CONNECTION_BY_ID_SQL_QUERY = "DELETE FROM tagged_certificates " +
            "WHERE (certificate_id = ?) and (tag_id = ?)";

    private Constants() {
    }
}
