package com.reisystems.hackathon.earthday2016.dao;

import com.reisystems.hackathon.earthday2016.model.ContextBasedSpending;
import com.reisystems.hackathon.earthday2016.model.Person;
import com.reisystems.hackathon.earthday2016.model.TotalSpending;
import com.reisystems.hackathon.earthday2016.model.Trend;

import java.util.List;
import java.util.Map;

public interface FpdsDAO {

    TotalSpending getTotal();

    List<ContextBasedSpending> getSpendingByStates();

    List<ContextBasedSpending> getSpendingByAgencies(int limit);

    List<Trend> getTrend();
}
