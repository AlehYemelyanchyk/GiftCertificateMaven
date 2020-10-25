package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Import(SpringDataConfig.class)
@Configuration
@EnableWebMvc
@ComponentScan({"com.epam.esm"})
public class SpringConfig implements WebMvcConfigurer {
}
