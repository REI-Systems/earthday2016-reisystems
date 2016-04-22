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
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource() throws URISyntaxException {
        URI uri = new URI(environment.getRequiredProperty("DATABASE_URL"));

        String username = uri.getUserInfo().split(":")[0];
        String password = uri.getUserInfo().split(":")[1];
        String databaseUrl = "jdbc:postgresql://" + uri.getHost() + ':' + uri.getPort() + uri.getPath();

        BasicDataSource datasource = new BasicDataSource();
        datasource.setUrl(databaseUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.addConnectionProperty("sslmode", "require");

        return datasource;
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() throws URISyntaxException {
        return new DataSourceTransactionManager(getDataSource());
    }
}
