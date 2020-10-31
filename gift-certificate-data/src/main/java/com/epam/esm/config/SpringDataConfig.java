package com.epam.esm.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.epam.esm"})
public class SpringDataConfig {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String ENV_DEV_PROPERTIES = "env-dev.properties";
    private static final String ENV_PROD_PROPERTIES = "env-prod.properties";

    private String driverClassName;
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    @Bean
    public DataSource getDataSource() {
        initProperties();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TransactionManager transactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    private void initProperties() {
        try (InputStream inputStream =
                     getClass().getClassLoader().getResourceAsStream(ENV_DEV_PROPERTIES)) {
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



