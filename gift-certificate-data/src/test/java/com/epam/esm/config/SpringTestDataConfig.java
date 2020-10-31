package com.epam.esm.config;

import com.epam.esm.dao.impl.AbstractIntegrationTest;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;

@Configuration
@ComponentScan({"com.epam.esm"})
public class SpringTestDataConfig {

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;MODE=MySQL");
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");

        return dataSource;
    }

    @Bean
    public AbstractIntegrationTest getAbstractTestClass() {
        return new AbstractIntegrationTest();
    }
}
