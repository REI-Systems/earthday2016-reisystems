package com.reisystems.hackathon.earthday2016.controller;

import com.reisystems.hackathon.earthday2016.dao.PeopleDAO;
import com.reisystems.hackathon.earthday2016.model.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit) {

        if (limit == null) {
            limit = 1;
        }

        List<Person> people = peopleDAO.getPeople(offset, limit);

        if (people != null) {
            PeopleController templateInstance = methodOn(PeopleController.class);

            String PLACEHOLDER = "__PLACEHOLDER__";
            String templateElementSelf = linkTo(templateInstance.getPerson(PLACEHOLDER)).toString();

            for (Person person: people) {
                person.add(new Link(templateElementSelf.replace(PLACEHOLDER, Long.toString(person.getPersonId())), Link.REL_SELF));
            }
        }

        List<Link> links = new ArrayList<>();
        PeopleController builder = methodOn(PeopleController.class);

        // self
        links.add(linkTo(builder.getPeople(offset, limit)).withSelfRel());
        // navigation links
        if ((offset != null) && (offset > 0)) {
            links.add(linkTo(builder.getPeople(null, limit)).withRel(Link.REL_FIRST));
        }
        if (limit != null) {
            if ((people.size() == limit)) {
                Integer nextOffset = ((offset == null) ? 0 : offset) + limit;
                links.add(linkTo(builder.getPeople(nextOffset, limit)).withRel(Link.REL_NEXT));
            }
            if ((offset != null) && (offset > 0)) {
                Integer prevOffset = (offset > limit) ? offset - limit : null;
                links.add(linkTo(builder.getPeople(prevOffset, limit)).withRel(Link.REL_PREVIOUS));
            }
        }
        // search
        ControllerLinkBuilder searchLinkBuilder = linkTo(builder.getPeople(null, null));
        Link searchLink = new Link(searchLinkBuilder.toString() + "{offset,limit}", "search");
        links.add(searchLink);

        return ResponseEntity.ok().body(new Resources<>(people, links));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Load Participant Information")
    public HttpEntity getPerson(
            @PathVariable("id") String personId) {

        Person person = null;

        if (person == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok().body(person);
        }
    }
}
