package com.reisystems.hackathon.earthday2016.dao.postgresql;

import com.reisystems.hackathon.earthday2016.dao.ContextSpendingRowMapper;
import com.reisystems.hackathon.earthday2016.dao.ContextTrendRowMapper;
import com.reisystems.hackathon.earthday2016.dao.FpdsDAO;
import com.reisystems.hackathon.earthday2016.model.ContextSpending;
import com.reisystems.hackathon.earthday2016.model.ContextTrend;
import com.reisystems.hackathon.earthday2016.model.Spending;
import com.reisystems.hackathon.earthday2016.model.Trend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class PostgreSQLFpdsDAO implements FpdsDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Spending getTotalSpending() {
        MapSqlParameterSource args = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder("SELECT SUM(amount) amount, SUM(CASE WHEN (is_sustainable = '1') THEN amount ELSE 0 END) AS amount_sustainable");
        sql.append(" FROM fpds");

        List<Spending> list = this.jdbcTemplate.query(sql.toString(), args, new BeanPropertyRowMapper<Spending>(Spending.class));

        return list.get(0);
    }

    public List<ContextSpending> getSpendingByStates() {
        MapSqlParameterSource args = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder("SELECT location_state_code AS abbreviation, SUM(amount) amount, SUM(CASE WHEN (is_sustainable = '1') THEN amount ELSE 0 END) AS amount_sustainable");
        sql.append(" FROM fpds");
        sql.append(" WHERE location_state_code IS NOT NULL AND place_of_performance_country = 'USA'");
        sql.append(" GROUP BY location_state_code");

        return this.jdbcTemplate.query(sql.toString(), args, new ContextSpendingRowMapper());
    }

    public List<ContextSpending> getSpendingByAgencies(int limit) {
        MapSqlParameterSource args = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder("SELECT d.agency_id AS identifier, d.amount, d.amount_sustainable, a.name, a.abbreviation");
        sql.append(" FROM (");
        sql.append(" SELECT agency_id, SUM(amount) amount, SUM(CASE WHEN (is_sustainable = '1') THEN amount ELSE 0 END) AS amount_sustainable FROM fpds GROUP BY agency_id");
        sql.append(") d, agencies a");
        sql.append(" WHERE a.agency_id = d.agency_id AND d.amount > 0");
        sql.append(" ORDER BY (d.amount_sustainable / d.amount) DESC");
        sql.append(" LIMIT ").append(limit);

        return this.jdbcTemplate.query(sql.toString(), args, new ContextSpendingRowMapper());
    }

    public List<Trend> getTrend() {
        MapSqlParameterSource args = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder("SELECT date_part('year', effective_date) AS year, SUM(amount) amount, SUM(CASE WHEN (is_sustainable = '1') THEN amount ELSE 0 END) AS amount_sustainable");
        sql.append(" FROM fpds");
        sql.append(" GROUP BY date_part('year', effective_date)");

        return this.jdbcTemplate.query(sql.toString(), args, new BeanPropertyRowMapper<Trend>(Trend.class));
    }

    public List<ContextTrend> getAgencyTrend(String agencyId) {
        MapSqlParameterSource args = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder("SELECT d.agency_id AS identifier, d.year, d.amount, d.amount_sustainable, a.name, a.abbreviation");
        sql.append(" FROM (");
        sql.append(" SELECT agency_id, date_part('year', effective_date) AS year, SUM(amount) amount, SUM(CASE WHEN (is_sustainable = '1') THEN amount ELSE 0 END) AS amount_sustainable");
        sql.append("   FROM fpds");
        sql.append("  WHERE agency_id = :agency_id");
        sql.append("  GROUP BY agency_id, date_part('year', effective_date)");
        sql.append(") d, agencies a");
        sql.append(" WHERE a.agency_id = d.agency_id");

        args.addValue("agency_id", agencyId);

        return this.jdbcTemplate.query(sql.toString(), args, new ContextTrendRowMapper());
    }
}
