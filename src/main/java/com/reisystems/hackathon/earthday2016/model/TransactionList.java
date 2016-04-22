package com.reisystems.hackathon.earthday2016.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.Relation;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
