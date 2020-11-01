package com.epam.esm.config;

import com.epam.esm.dao.impl.AbstractIntegrationTest;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@ComponentScan({"com.epam.esm"})
public class SpringTestDataConfig {

    private Environment env;

    @Autowired
    public SpringTestDataConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @Profile("test")
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("driverClassName")));
        dataSource.setUrl(env.getProperty("dbUrl"));
        dataSource.setUsername(env.getProperty("dbUsername"));
        dataSource.setPassword(env.getProperty("dbPassword"));

        return dataSource;
    }

    @Bean
    @Profile("test")
    public AbstractIntegrationTest getAbstractTestClass() {
        return new AbstractIntegrationTest();
    }
}
