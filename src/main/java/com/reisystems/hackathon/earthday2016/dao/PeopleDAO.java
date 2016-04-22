package com.reisystems.hackathon.earthday2016.dao;

import com.reisystems.hackathon.earthday2016.model.Person;

import java.util.List;
import java.util.Map;

public interface PeopleDAO {

    List<Person> getPeople(Map<String, Object> query, Integer offset, Integer limit);
}
