package com.reisystems.hackathon.earthday2016.dao.postgresql;

import com.reisystems.hackathon.earthday2016.dao.PeopleDAO;
import com.reisystems.hackathon.earthday2016.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

public class PostgreSQLPeopleDAO implements PeopleDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Person> getPeople(Map<String, Object> query, Integer offset, Integer limit) {
        MapSqlParameterSource args = new MapSqlParameterSource();

        StringBuilder sql = new StringBuilder("SELECT person_id, name FROM people");
        if (query != null) {
            sql.append(" WHERE ");
            for (Map.Entry<String, Object> q: query.entrySet()) {
                String columnName = q.getKey();
                sql.append(columnName).append(String.format(" IN (:%s)", columnName));
                args.addValue(columnName, q.getValue());
            }
        }

        if (limit != null) {
            sql.append(" LIMIT ").append(limit);
        }
        if (offset != null) {
            sql.append(" OFFSET ").append(offset);
        }

        return this.jdbcTemplate.query(sql.toString(), args, new BeanPropertyRowMapper<Person>(Person.class));
    }
}
