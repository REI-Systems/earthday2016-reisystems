package com.reisystems.hackathon.earthday2016.model;

import org.springframework.hateoas.core.Relation;

import java.util.Date;

@Relation(collectionRelation = "trend")
public class Trend extends TotalSpending {

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
