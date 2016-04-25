package com.reisystems.hackathon.earthday2016.dao;

import com.reisystems.hackathon.earthday2016.model.*;

import java.util.List;
import java.util.Map;

public interface FpdsDAO {

    Spending getTotalSpending();

    List<ContextSpending> getSpendingByStates();

    List<ContextSpending> getSpendingByAgencies(int limit);

    List<Trend> getTrend();
    List<ContextTrend> getAgencyTrend(String agencyId);

    List<Transaction> getTransactions(Map<String, Object> query, Integer offset, Integer limit);
    int getTransactionCount(Map<String, Object> query);
}
