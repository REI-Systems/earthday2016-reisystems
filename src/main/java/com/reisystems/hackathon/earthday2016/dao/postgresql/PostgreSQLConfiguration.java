package com.reisystems.hackathon.earthday2016.dao.postgresql;

import com.reisystems.hackathon.earthday2016.dao.AgencyDAO;
import com.reisystems.hackathon.earthday2016.dao.FpdsDAO;
import com.reisystems.hackathon.earthday2016.dao.PeopleDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostgreSQLConfiguration {

    @Bean
    public PeopleDAO getPeopleDAO() {
        return new PostgreSQLPeopleDAO();
    }

    @Bean
    public AgencyDAO getAgencyDAO() {
        return new PostgreSQLAgencyDAO();
    }

    @Bean
    public FpdsDAO getFpdsDAO() {
        return new PostgreSQLFpdsDAO();
    }
}
