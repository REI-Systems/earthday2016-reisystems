package com.reisystems.hackathon.earthday2016.model;

import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "spending")
public class ContextSpending extends Spending {

    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
