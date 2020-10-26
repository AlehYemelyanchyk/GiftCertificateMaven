package com.epam.esm.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class AbstractIntegrationTest {

    @Autowired
    private DataSource dataSource;

    public void executeSqlScript(String script) throws SQLException {
        Connection connection = dataSource.getConnection();
        ScriptUtils.executeSqlScript(connection, new ClassPathResource(script));
        connection.close();
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
