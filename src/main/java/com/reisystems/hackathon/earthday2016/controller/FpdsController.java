package com.reisystems.hackathon.earthday2016.controller;

import com.reisystems.hackathon.earthday2016.model.ContextBasedSpending;
import com.reisystems.hackathon.earthday2016.model.TotalSpending;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/fpds")
@Api(tags = "FPDS", description = "Federal Procurement Data System")
public class FpdsController {

    @RequestMapping(value = "/spending", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "TotalSpending Federal Procurement")
    public HttpEntity getTotal() {
        TotalSpending totalSpending = new TotalSpending();
        totalSpending.setAmount(1000000000.0);
        totalSpending.setAmountSustainable(340000000.0);

        return ResponseEntity.ok().body(totalSpending);
    }

    @RequestMapping(value = "/sustainability/state", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "State Sustainability")
    public HttpEntity getStateSustainability() {
        List<ContextBasedSpending> states = new ArrayList<>();

        ContextBasedSpending state = new ContextBasedSpending();
        state.setIdentifier("1400");
        state.setAcronym("VA");
        state.setName("Virginia");
        state.setAmount(100000.0);
        state.setAmountSustainable(1000.0);
        states.add(state);

        ContextBasedSpending state2 = new ContextBasedSpending();
        state2.setIdentifier("1789");
        state2.setAcronym("MD");
        state2.setName("Maryland");
        state2.setAmount(1000000.0);
        state2.setAmountSustainable(1000.0);
        states.add(state2);

        List<Link> links = new ArrayList<>();

        return ResponseEntity.ok().body(new Resources<>(states, links));
    }
}
