package com.reisystems.hackathon.earthday2016.controller;

import com.reisystems.hackathon.earthday2016.dao.PeopleDAO;
import com.reisystems.hackathon.earthday2016.model.Person;
import com.reisystems.hackathon.earthday2016.model.StateSustainability;
import com.reisystems.hackathon.earthday2016.model.Total;
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
@RequestMapping("/api/v1/fpds")
@Api(tags = "FPDS", description = "Federal Procurement Data System")
public class FpdsController {

    @RequestMapping(value = "/spending", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Total Federal Procurement")
    public HttpEntity getTotal() {
        Total total = new Total();
        total.setAmount(1000000000.0);

        return ResponseEntity.ok().body(total);
    }


    @RequestMapping(value = "/sustainability/state", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "State Sustainability")
    public HttpEntity getStateSustainability() {
        List<StateSustainability> states = new ArrayList<>();

        StateSustainability state = new StateSustainability();
        state.setAcronym("VA");
        state.setName("Virginia");
        state.setAmount(100000.0);
        state.setAmount_sustainable(1000.0);
        states.add(state);

        StateSustainability state2 = new StateSustainability();
        state2.setAcronym("MD");
        state2.setName("Maryland");
        state2.setAmount(1000000.0);
        state2.setAmount_sustainable(1000.0);
        states.add(state2);

        List<Link> links = new ArrayList<>();

        return ResponseEntity.ok().body(new Resources<>(states, links));
    }
}
