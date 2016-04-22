package com.reisystems.hackathon.earthday2016.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContextBasedSpending extends Context {

    private Double amount;
    private Double amountSustainable;

    public ContextBasedSpending() {}

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
