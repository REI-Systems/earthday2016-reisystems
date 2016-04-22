package com.reisystems.hackathon.earthday2016.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource() {
        System.out.println("\n\n\nStarted initialization Data Source\n\n\n\n\n\n");
        BasicDataSource datasource = new BasicDataSource();
        datasource.setUrl(environment.getRequiredProperty("database.url"));
        datasource.setUsername(environment.getRequiredProperty("database.username"));
        datasource.setPassword(environment.getRequiredProperty("database.password"));
        datasource.addConnectionProperty("sslmode", "require");
        System.out.println("\n\n\n\n\n\n\nCompleted initialization Data Source\n\n\n\n\n");

        return datasource;
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }
}
