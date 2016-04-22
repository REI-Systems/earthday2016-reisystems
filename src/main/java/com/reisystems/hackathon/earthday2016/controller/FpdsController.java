package com.reisystems.hackathon.earthday2016.controller;

import com.reisystems.hackathon.earthday2016.model.ContextBasedSpending;
import com.reisystems.hackathon.earthday2016.model.TotalSpending;
import com.reisystems.hackathon.earthday2016.model.Trend;
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

    @RequestMapping(value = "/sustainability/agencies", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Spending by Agencies")
    public HttpEntity getSpendingByAgencies(
            @RequestParam(value = "limit", required = false) Integer limit) {

        if (limit == null) {
            limit = 10;
        }

        List<ContextBasedSpending> agencies = new ArrayList<>();

        ContextBasedSpending agency;
        for (int i = 1; i <= limit; i++) {
            agency = new ContextBasedSpending();
            agency.setIdentifier("1400" + Integer.toString(i));
            agency.setAcronym("A" + Integer.toString(i));
            agency.setName("Agency " + Integer.toString(i));
            agency.setAmount(12300678.0 / i);
            agency.setAmountSustainable(50000.0 / i);
            agencies.add(agency);
        }

        List<Link> links = new ArrayList<>();

        return ResponseEntity.ok().body(new Resources<>(agencies, links));
    }

    @RequestMapping(value = "/sustainability/agency/trend", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Spending by Agencies")
    public HttpEntity getAgencySpendingTrend() {
        List<TotalSpending> trend = new ArrayList<>();

        Trend spending;
        for (int i = 1; i <= 10; i++) {
            spending = new Trend();
            spending.setDate(new Date(2005 + i, 1, 1));
            spending.setAmount(1678.5 * i);
            spending.setAmountSustainable(26.0 * i);
            trend.add(spending);
        }

        List<Link> links = new ArrayList<>();

        return ResponseEntity.ok().body(new Resources<>(trend, links));
    }

    @RequestMapping(value = "/sustainability/states", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Spending by States")
    public HttpEntity getSpendingByStates() {
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
