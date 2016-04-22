package com.reisystems.hackathon.earthday2016.model;

import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "trend")
public class AgencyTrend extends Trend {

    private String agencyId;
    private String agencyAbbreviation;

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyAbbreviation() {
        return agencyAbbreviation;
    }

    public void setAgencyAbbreviation(String agencyAbbreviation) {
        this.agencyAbbreviation = agencyAbbreviation;
    }
}
