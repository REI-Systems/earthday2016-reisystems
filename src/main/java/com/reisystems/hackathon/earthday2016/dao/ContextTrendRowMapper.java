package com.reisystems.hackathon.earthday2016.dao;

import com.reisystems.hackathon.earthday2016.model.Context;
import com.reisystems.hackathon.earthday2016.model.ContextSpending;
import com.reisystems.hackathon.earthday2016.model.ContextTrend;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContextTrendRowMapper implements RowMapper<ContextTrend> {

    public ContextTrend mapRow(ResultSet resultSet, int i) throws SQLException {
        ContextTrend element = new ContextTrend();

        Context context = new Context();
        element.setContext(context);

        context.setIdentifier(resultSet.getString("identifier"));
        context.setAbbreviation(resultSet.getString("abbreviation"));
        context.setName(resultSet.getString("name"));

        element.setYear(resultSet.getInt("year"));
        element.setAmount(resultSet.getDouble("amount"));
        element.setAmountSustainable(resultSet.getDouble("amount_sustainable"));

        return element;
    }
}
