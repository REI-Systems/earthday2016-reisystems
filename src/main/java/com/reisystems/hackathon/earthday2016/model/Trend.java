package com.reisystems.hackathon.earthday2016.model;

import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "trend")
public class Trend extends Spending {

    private Integer year;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
