package com.reisystems.hackathon.earthday2016.model;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "collection")
public class TotalSpending extends ResourceSupport {

    private Double amount = 0.0;
    private Double amountSustainable = 0.0;

    public TotalSpending() {}

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmountSustainable() {
        return amountSustainable;
    }

    public void setAmountSustainable(Double amountSustainable) {
        this.amountSustainable = amountSustainable;
    }
}
