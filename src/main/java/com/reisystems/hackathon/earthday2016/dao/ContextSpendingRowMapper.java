package com.reisystems.hackathon.earthday2016.dao;

import com.reisystems.hackathon.earthday2016.model.Context;
import com.reisystems.hackathon.earthday2016.model.ContextSpending;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContextSpendingRowMapper implements RowMapper<ContextSpending> {

    public ContextSpending mapRow(ResultSet resultSet, int i) throws SQLException {
        ContextSpending element = new ContextSpending();

        Context context = new Context();
        element.setContext(context);

        try {
            int indexIdentifier = resultSet.findColumn("identifier");
            context.setIdentifier(resultSet.getString(indexIdentifier));
        } catch (SQLException e) {}

        context.setAbbreviation(resultSet.getString("abbreviation"));
        try {
            int indexName = resultSet.findColumn("name");
            context.setName(resultSet.getString(indexName));
        } catch (SQLException e) {}

        element.setAmount(resultSet.getDouble("amount"));
        element.setAmountSustainable(resultSet.getDouble("amount_sustainable"));

        return element;
    }
}
