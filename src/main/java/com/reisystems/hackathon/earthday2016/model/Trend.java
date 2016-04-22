package com.reisystems.hackathon.earthday2016.model;

import org.springframework.hateoas.core.Relation;

import java.util.Date;

@Relation(collectionRelation = "trend")
public class Trend extends TotalSpending {

    private String agencyId;
    private String agencyAbbreviation;
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
