package com.reisystems.hackathon.earthday2016.dao.postgresql;

import com.reisystems.hackathon.earthday2016.dao.FpdsDAO;
import com.reisystems.hackathon.earthday2016.dao.PeopleDAO;
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

        StringBuilder sql = new StringBuilder("SELECT is_sustainable, SUM(amount) AS total FROM fpds GROUP BY is_sustainable");

        List<Map<String, Object>> data = this.jdbcTemplate.query(sql.toString(), args, new ColumnMapRowMapper());

        TotalSpending spending = new TotalSpending();

        if (data != null) {
            for (Map r: data) {
                String flag = r.get("is_sustainable").toString();
                Double amount = (Double) r.get("total");

                if (flag.equals("1")) {
                    spending.setAmountSustainable(amount);
                }
                spending.setAmount(spending.getAmount() + amount);
            }
        }

        return spending;
    }
}
