package com.epam.esm.dao;

public final class Constants {

    public static final String FIND_ALL_SQL_QUERY = "SELECT * FROM gift_certificates.certificates";
    public static final String FIND_BY_NAME_SQL_QUERY = "SELECT * FROM gift_certificates.certificates WHERE name = ?";
    public static final String FIND_BY_ID_SQL_QUERY = "SELECT * FROM gift_certificates.certificates WHERE id = ?";
    public static final String SAVE_SQL_QUERY = "INSERT INTO gift_certificates.certificates (name, description, price, create_date, last_update_date, duration) VALUES (?,?,?,?,?,?)";
    public static final String DELETE_BY_NAME_SQL_QUERY = "DELETE FROM gift_certificates.certificates WHERE name = ?";
    public static final String DELETE_BY_ID_SQL_QUERY = "DELETE FROM gift_certificates.certificates WHERE id = ?";
    public static final String FIND_ALL_TAGS_SQL_QUERY = "SELECT * FROM gift_certificates.tags";
    public static final String FIND_TAGS_BY_NAME_SQL_QUERY = "SELECT * FROM gift_certificates.tags WHERE name = ?";
    public static final String FIND_TAGS_BY_ID_SQL_QUERY = "SELECT * FROM gift_certificates.tags WHERE id = ?";
    public static final String SAVE_TAGS_SQL_QUERY = "INSERT INTO gift_certificates.tags (name) VALUES (?)";
    public static final String UPDATE_TAGS_SQL_QUERY = "UPDATE gift_certificates.tags SET name = ? WHERE id = ?";
    public static final String DELETE_TAGS_BY_NAME_SQL_QUERY = "DELETE FROM gift_certificates.tags WHERE name = ?";
    public static final String DELETE_TAGS_BY_ID_SQL_QUERY = "DELETE FROM gift_certificates.tags WHERE id = ?";
    
    private Constants() {
    }
}
