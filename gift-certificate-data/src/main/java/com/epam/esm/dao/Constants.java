package com.epam.esm.dao;

public final class Constants {

    public static final String FIND_ALL_CERTIFICATES_SQL_QUERY = "SELECT * FROM gift_certificates.certificates";
    public static final String FIND_LAST_CERTIFICATES_BY_NAME_SQL_QUERY = "SELECT * FROM gift_certificates.certificates " +
            "WHERE name = ? ORDER BY create_date DESC";
    public static final String FIND_CERTIFICATE_BY_ID_SQL_QUERY = "SELECT * FROM gift_certificates.certificates " +
            "WHERE id = ?";
    public static final String SAVE_CERTIFICATE_SQL_QUERY = "INSERT INTO gift_certificates.certificates " +
            "(name, description, price, create_date, last_update_date, duration) VALUES (?,?,?,?,?,?)";
    public static final String DELETE_CERTIFICATES_BY_NAME_SQL_QUERY = "DELETE FROM gift_certificates.certificates " +
            "WHERE name = ?";
    public static final String DELETE_CERTIFICATE_BY_ID_SQL_QUERY = "DELETE FROM gift_certificates.certificates " +
            "WHERE id = ?";
    public static final String FIND_ALL_TAGS_SQL_QUERY = "SELECT * FROM gift_certificates.tags";
    public static final String FIND_TAGS_BY_NAME_SQL_QUERY = "SELECT * FROM gift_certificates.tags WHERE name = ?";
    public static final String FIND_TAGS_BY_ID_SQL_QUERY = "SELECT * FROM gift_certificates.tags WHERE id = ?";
    public static final String SAVE_TAGS_SQL_QUERY = "INSERT INTO gift_certificates.tags (name) VALUES (?)";
    public static final String UPDATE_TAGS_SQL_QUERY = "UPDATE gift_certificates.tags SET name = ? WHERE id = ?";
    public static final String DELETE_TAGS_BY_NAME_SQL_QUERY = "DELETE FROM gift_certificates.tags WHERE name = ?";
    public static final String DELETE_TAGS_BY_ID_SQL_QUERY = "DELETE FROM gift_certificates.tags WHERE id = ?";
    public static final String SAVE_TAGGED_CERTIFICATE_SQL_QUERY = "INSERT INTO gift_certificates.tagged_certificates " +
            "(certificate_id, tag_id) VALUES (?, ?)";
    public static final String FIND_TAGGED_CERTIFICATE_BY_ID_SQL_QUERY =
            "SELECT a.id, a.name, a.description, a.price, a.create_date, a.last_update_date, a.duration, c.id, c.name " +
                    "FROM gift_certificates.certificates as a " +
                    "JOIN gift_certificates.tagged_certificates as b " +
                    "ON a.id = b.certificate_id " +
                    "JOIN gift_certificates.tags as c " +
                    "ON b.tag_id = c.id " +
                    "WHERE a.id = ?";

    private Constants() {
    }
}
