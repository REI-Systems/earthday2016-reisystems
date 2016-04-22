package com.reisystems.hackathon.earthday2016.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "states")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StateSustainability extends ResourceSupport {

    private String acronym;
    private String name;
    private Double amount;
    private Double amount_sustainable;

    public StateSustainability() {}

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmount_sustainable() {
        return amount_sustainable;
    }

    public void setAmount_sustainable(Double amount_sustainable) {
        this.amount_sustainable = amount_sustainable;
    }
}
