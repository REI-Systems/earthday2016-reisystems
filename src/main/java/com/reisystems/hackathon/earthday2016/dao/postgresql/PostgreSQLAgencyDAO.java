package com.reisystems.hackathon.earthday2016.dao.postgresql;

import com.reisystems.hackathon.earthday2016.dao.AgencyDAO;
import com.reisystems.hackathon.earthday2016.model.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class PostgreSQLAgencyDAO implements AgencyDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Context getAgency(String agencyId) {
        MapSqlParameterSource args = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder("SELECT agency_id AS identifier, abbreviation, name FROM agencies");

        sql.append(" WHERE ");

        sql.append("agency_id = :agency_id");
        args.addValue("agency_id", agencyId);

        List<Context> l = this.jdbcTemplate.query(sql.toString(), args, new BeanPropertyRowMapper<Context>(Context.class));

        return (l.size() == 0) ? null : l.get(0);
    }
}
