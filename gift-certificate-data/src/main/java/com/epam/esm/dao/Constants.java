package com.epam.esm.dao;

public final class Constants {

    public static final String DELETE_TAGS_CONNECTION_BY_ID_SQL_QUERY = "DELETE FROM tagged_certificates " +
            "WHERE (certificate_id = ?) and (tag_id = ?)";

    private Constants() {
    }
}
