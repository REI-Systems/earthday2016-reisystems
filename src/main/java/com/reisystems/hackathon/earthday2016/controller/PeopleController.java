package com.reisystems.hackathon.earthday2016.controller;

import com.reisystems.hackathon.earthday2016.dao.PeopleDAO;
import com.reisystems.hackathon.earthday2016.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
public class PeopleController {

    @Autowired
    private PeopleDAO peopleDAO;

    @RequestMapping(method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public HttpEntity getPeople() {
        List<Person> data = peopleDAO.getPeople();

        List<Link> links = new ArrayList<>();

        return ResponseEntity.ok().body(new Resources<>(data, links));
    }
}
