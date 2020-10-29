package com.epam.esm.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan({"com.epam.esm"})
public class SpringDataConfig {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String ENV_DEV_PROPERTIES = "env-dev.properties";
    private static final String ENV_PROD_PROPERTIES = "env-prod.properties";

    private String driverClassName;
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private int initialSize;
    private int maxIdle;
    private Long maxWait;

    @Bean
    public DataSource getDataSource() {
        initProperties();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMaxWait(maxWait);

        return dataSource;
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
            initialSize = Integer.parseInt(properties.getProperty("initialSize"));
            maxIdle = Integer.parseInt(properties.getProperty("maxIdle"));
            maxWait = Long.valueOf(properties.getProperty("maxWait"));
        } catch (IOException e) {
            LOGGER.error("initProperties error: " + e);
            throw new Error("Properties has not been loaded", e);
        }
    }
}
