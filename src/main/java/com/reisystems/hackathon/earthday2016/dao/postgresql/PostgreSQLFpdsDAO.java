package com.reisystems.hackathon.earthday2016.dao.postgresql;

import com.reisystems.hackathon.earthday2016.dao.FpdsDAO;
import com.reisystems.hackathon.earthday2016.dao.PeopleDAO;
import com.reisystems.hackathon.earthday2016.model.ContextBasedSpending;
import com.reisystems.hackathon.earthday2016.model.Person;
import com.reisystems.hackathon.earthday2016.model.TotalSpending;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

public class PostgreSQLFpdsDAO implements FpdsDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public TotalSpending getTotal() {
        MapSqlParameterSource args = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder("SELECT SUM(amount) amount, SUM(CASE WHEN (is_sustainable = '1') THEN amount ELSE 0 END) AS amount_sustainable");
        sql.append(" FROM fpds");

        List<TotalSpending> list = this.jdbcTemplate.query(sql.toString(), args, new BeanPropertyRowMapper<TotalSpending>(TotalSpending.class));

        return list.get(0);
    }

    public List<ContextBasedSpending> getSpendingByStates() {
        MapSqlParameterSource args = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder("SELECT location_state_code AS acronym, SUM(amount) amount, SUM(CASE WHEN (is_sustainable = '1') THEN amount ELSE 0 END) AS amount_sustainable");
        sql.append(" FROM fpds");
        sql.append(" WHERE location_state_code IS NOT NULL");
        sql.append(" GROUP BY location_state_code");

        return this.jdbcTemplate.query(sql.toString(), args, new BeanPropertyRowMapper<ContextBasedSpending>(ContextBasedSpending.class));
    }

    public List<ContextBasedSpending> getSpendingByAgencies(int limit) {
        MapSqlParameterSource args = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder("SELECT d.agency_id, d.amount, d.amount_sustainable, a.name, a.abbreviation AS acronym");
        sql.append(" FROM (");
        sql.append(" SELECT agency_id, SUM(amount) amount, SUM(CASE WHEN (is_sustainable = '1') THEN amount ELSE 0 END) AS amount_sustainable FROM fpds GROUP BY agency_id");
        sql.append(") d, agencies a");
        sql.append(" WHERE a.agency_id = d.agency_id");
        sql.append(" ORDER BY d.amount_sustainable DESC");
        sql.append(" LIMIT ").append(limit);

        return this.jdbcTemplate.query(sql.toString(), args, new BeanPropertyRowMapper<ContextBasedSpending>(ContextBasedSpending.class));
    }
}
