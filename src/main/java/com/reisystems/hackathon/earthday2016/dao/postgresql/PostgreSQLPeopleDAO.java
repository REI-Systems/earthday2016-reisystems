package com.reisystems.hackathon.earthday2016.dao.postgresql;

import com.reisystems.hackathon.earthday2016.dao.PeopleDAO;
import com.reisystems.hackathon.earthday2016.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

public class PostgreSQLPeopleDAO implements PeopleDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Person> getPeople() {
        String sql = "SELECT person_id, name FROM people";

        SqlParameterSource args = new MapSqlParameterSource();

        return this.jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<Person>(Person.class));
    }
}
