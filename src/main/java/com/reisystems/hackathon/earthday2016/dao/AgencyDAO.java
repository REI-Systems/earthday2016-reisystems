package com.reisystems.hackathon.earthday2016.dao;

import com.reisystems.hackathon.earthday2016.model.Context;
import com.reisystems.hackathon.earthday2016.model.Person;

import java.util.List;
import java.util.Map;

public interface AgencyDAO {

    Context getAgency(String agencyId);
}
