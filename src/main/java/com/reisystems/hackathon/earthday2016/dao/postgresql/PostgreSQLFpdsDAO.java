package com.reisystems.hackathon.earthday2016.dao.postgresql;

import com.reisystems.hackathon.earthday2016.dao.ContextSpendingRowMapper;
import com.reisystems.hackathon.earthday2016.dao.ContextTrendRowMapper;
import com.reisystems.hackathon.earthday2016.dao.FpdsDAO;
import com.reisystems.hackathon.earthday2016.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

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
        sql.append("  FROM fpds");
        sql.append(" WHERE effective_date < current_date");
        sql.append(" GROUP BY date_part('year', effective_date)");

        return this.jdbcTemplate.query(sql.toString(), args, new BeanPropertyRowMapper<Trend>(Trend.class));
    }

    public List<ContextTrend> getAgencyTrend(String agencyId) {
        MapSqlParameterSource args = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder("SELECT d.agency_id AS identifier, d.year, d.amount, d.amount_sustainable, a.name, a.abbreviation");
        sql.append(" FROM (");
        sql.append(" SELECT agency_id, date_part('year', effective_date) AS year, SUM(amount) amount, SUM(CASE WHEN (is_sustainable = '1') THEN amount ELSE 0 END) AS amount_sustainable");
        sql.append("   FROM fpds");
        sql.append("  WHERE agency_id = :agency_id AND effective_date < current_date");
        sql.append("  GROUP BY agency_id, date_part('year', effective_date)");
        sql.append(") d, agencies a");
        sql.append(" WHERE a.agency_id = d.agency_id");

        args.addValue("agency_id", agencyId);

        return this.jdbcTemplate.query(sql.toString(), args, new ContextTrendRowMapper());
    }

    public List<Transaction> getTransactions(Map<String, Object> query, Integer offset, Integer limit) {
        MapSqlParameterSource args = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder("SELECT agency_id, identifier, agency_name, product_name, state_code, amount, date, year");
        sql.append(" FROM (");
        sql.append("SELECT fpds.agency_id, fpds.piid AS identifier, a.name AS agency_name, fpds.product AS product_name, fpds.location_state_code AS state_code, fpds.amount, fpds.effective_date AS date, date_part('year', effective_date) AS year");
        sql.append("  FROM fpds, agencies a");
        sql.append(" WHERE a.agency_id = fpds.agency_id) n");
        if (query != null) {
            StringBuilder b = new StringBuilder();
            for (Map.Entry<String, Object> q: query.entrySet()) {
                String columnName = q.getKey();
                if (b.length() > 0) {
                    b.append(" AND ");
                }
                b.append(columnName).append(String.format(" IN (:%s)", columnName));
                args.addValue(columnName, q.getValue());
            }

            if (b.length() > 0) {
                sql.append(" WHERE ").append(b);
            }
        }
        sql.append(" ORDER BY date, identifier");

        if (limit != null) {
            sql.append(" LIMIT ").append(limit);
        }
        if (offset != null) {
            sql.append(" OFFSET ").append(offset);
        }

        return this.jdbcTemplate.query(sql.toString(), args, new BeanPropertyRowMapper<Transaction>(Transaction.class));
    }

    public int getTransactionCount(Map<String, Object> query) {
        MapSqlParameterSource args = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder("SELECT COUNT(*)");
        sql.append(" FROM (");
        sql.append("SELECT agency_id, location_state_code AS state_code, date_part('year', effective_date) AS year");
        sql.append("  FROM fpds) n");
        if (query != null) {
            StringBuilder b = new StringBuilder();
            for (Map.Entry<String, Object> q: query.entrySet()) {
                String columnName = q.getKey();
                if (b.length() > 0) {
                    b.append(" AND ");
                }
                b.append(columnName).append(String.format(" IN (:%s)", columnName));
                args.addValue(columnName, q.getValue());
            }
            if (b.length() > 0) {
                sql.append(" WHERE ").append(b);
            }
        }

        return this.jdbcTemplate.queryForObject(sql.toString(), args, Integer.class);
    }
}
