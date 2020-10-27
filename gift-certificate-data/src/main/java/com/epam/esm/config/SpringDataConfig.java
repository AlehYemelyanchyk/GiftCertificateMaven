package com.epam.esm.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan({"com.epam.esm"})
public class SpringDataConfig {

    private static final Logger LOGGER = LogManager.getLogger();

    private String driverClassName;
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    @Bean
    public BasicDataSource getDataSource() {
        initProperties();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }

    private void initProperties() {
        try (InputStream inputStream =
                     getClass().getClassLoader().getResourceAsStream("env-dev.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            dbUrl = properties.getProperty("dbUrl");
            driverClassName = properties.getProperty("driverClassName");
            dbUsername = properties.getProperty("dbUsername");
            dbPassword = properties.getProperty("dbPassword");
        } catch (IOException e) {
            LOGGER.error("initProperties error: " + e);
            throw new Error("Properties has not been loaded", e);
        }
    }
}
