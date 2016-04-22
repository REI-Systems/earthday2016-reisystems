package com.reisystems.hackathon.earthday2016.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "collection")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Context extends ResourceSupport {

    private String identifier;
    private String acronym;
    private String name;

    public Context() {}

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

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
}
