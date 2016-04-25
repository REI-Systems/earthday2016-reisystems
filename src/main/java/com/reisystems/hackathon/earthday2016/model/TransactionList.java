package com.reisystems.hackathon.earthday2016.model;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

public class TransactionList<T> extends Resources {

    private Integer count;

    public TransactionList(Iterable<T> content, Iterable<Link> links) {
        super(content, links);
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
