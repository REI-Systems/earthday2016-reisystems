package com.reisystems.hackathon.earthday2016.model;

import org.springframework.hateoas.ResourceSupport;

public class TotalSpending extends ResourceSupport {

    private Double amount;
    private Double amountSustainable;

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
