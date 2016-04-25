package com.reisystems.hackathon.earthday2016.dao;

import com.reisystems.hackathon.earthday2016.model.ContextSpending;
import com.reisystems.hackathon.earthday2016.model.Spending;
import com.reisystems.hackathon.earthday2016.model.Trend;

import java.util.List;

public interface FpdsDAO {

    Spending getTotal();

    List<ContextSpending> getSpendingByStates();

    List<ContextSpending> getSpendingByAgencies(int limit);

    List<Trend> getTrend();
}
