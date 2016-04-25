package com.reisystems.hackathon.earthday2016.controller;

import com.reisystems.hackathon.earthday2016.dao.PeopleDAO;
import com.reisystems.hackathon.earthday2016.model.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/people")
@Api(tags = "People", description = "Hackathon paticipants")
public class PeopleController {

    @Autowired
    private PeopleDAO peopleDAO;

    @RequestMapping(method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Search")
    public HttpEntity getPeople(
            @RequestParam(value = "ids", required = false) Set<String> ids,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit) {

        if (limit == null) {
            limit = 1;
        }

        Map<String, Object> query = new HashMap<>();
        if (ids != null) {
            query.put("person_id", ids);
        }
        if (name != null) {
            query.put("name", name);
        }

        List<Person> people = peopleDAO.getPeople((query.size() == 0 ? null : query), offset, limit);

        if (people != null) {
            for (Person person: people) {
                assemblePersonLinks(person);
            }
        }

        List<Link> links = new ArrayList<>();
        PeopleController builder = methodOn(PeopleController.class);

        // self
        links.add(linkTo(builder.getPeople(ids, name, offset, limit)).withSelfRel());
        // navigation links
        if ((offset != null) && (offset > 0)) {
            links.add(linkTo(builder.getPeople(ids, name, null, limit)).withRel(Link.REL_FIRST));
        }
        if (limit != null) {
            if ((people.size() == limit)) {
                Integer nextOffset = ((offset == null) ? 0 : offset) + limit;
                links.add(linkTo(builder.getPeople(ids, name, nextOffset, limit)).withRel(Link.REL_NEXT));
            }
            if ((offset != null) && (offset > 0)) {
                Integer prevOffset = (offset > limit) ? offset - limit : null;
                links.add(linkTo(builder.getPeople(ids, name, prevOffset, limit)).withRel(Link.REL_PREVIOUS));
            }
        }
        // search
        ControllerLinkBuilder searchLinkBuilder = linkTo(builder.getPeople(null, null, null, null));
        Link searchLink = new Link(searchLinkBuilder.toString() + "{ids,name,offset,limit}", "search");
        links.add(searchLink);

        return ResponseEntity.ok().body(new Resources<>(people, links));
    }

    @RequestMapping(value = "/{personId}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Load Participant Information")
    public HttpEntity getPerson(
            @PathVariable("personId") Integer personId) {

        Map<String, Object> query = new HashMap<>();
        query.put("person_id", personId);

        List<Person> people = peopleDAO.getPeople(query, null, null);

        if (people == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            Person person = people.get(0);
            assemblePersonLinks(person);
            return ResponseEntity.ok().body(person);
        }
    }

    protected void assemblePersonLinks(Person person) {
        person.add(linkTo(methodOn(PeopleController.class).getPerson(person.getPersonId())).withSelfRel());
    }
}
