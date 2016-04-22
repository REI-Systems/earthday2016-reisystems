package com.reisystems.hackathon.earthday2016.model;

import org.springframework.hateoas.ResourceSupport;

public class Total extends ResourceSupport {

    private Double amount;

    public Total() {}

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
