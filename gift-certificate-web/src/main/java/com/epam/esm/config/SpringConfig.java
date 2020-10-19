package com.epam.esm.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({"com.epam.esm"})
public class SpringConfig implements WebMvcConfigurer {

    @Bean
    public BasicDataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/gift_certificates");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }

//
//    private final ApplicationContext applicationContext;
//
//    @Autowired
//    public SpringConfig(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
//
//    @Bean
//    public DataSource getDataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/investing_spring");
//        dataSource.setUsername("root");
//        dataSource.setPassword("root");
//
//        return dataSource;
//    }
//
//    @Bean
//    public SessionFactory sessionFactory() throws Exception {
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(getDataSource());
//        sessionFactoryBean.setPackagesToScan("com.alehyem.personalfinances.entity");
//
//        Properties hibernateProperties = new Properties();
//        hibernateProperties.put("hibernate.show_sql", true);
//        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.DB2Dialect");
//        sessionFactoryBean.setHibernateProperties(hibernateProperties);
//        sessionFactoryBean.afterPropertiesSet();
//
//        return sessionFactoryBean.getObject();
//    }
//
//    @Bean
//    public SpringResourceTemplateResolver templateResolver() {
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setApplicationContext(applicationContext);
//        templateResolver.setPrefix("/WEB-INF/views/");
//        templateResolver.setSuffix(".jsp");
//        return templateResolver;
//    }
//
//    @Bean
//    public ISpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        templateEngine.setEnableSpringELCompiler(true);
//        return templateEngine;
//    }
//
//    @Bean
//    public TransactionManager transactionManager(
//            SessionFactory sessionFactory) {
//        return new HibernateTransactionManager(
//                sessionFactory);
//    }
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//        resolver.setTemplateEngine(templateEngine());
//        registry.viewResolver(resolver);
//    }
}
