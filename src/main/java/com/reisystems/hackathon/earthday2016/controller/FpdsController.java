package com.reisystems.hackathon.earthday2016.controller;

import com.reisystems.hackathon.earthday2016.dao.FpdsDAO;
import com.reisystems.hackathon.earthday2016.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/fpds")
@Api(tags = "FPDS", description = "Federal Procurement Data System")
public class FpdsController {

    @Autowired
    private FpdsDAO fpdsDAO;

    @RequestMapping(value = "/spending", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Spending Federal Procurement")
    public HttpEntity getTotalSpending() {
        Spending totalSpending = fpdsDAO.getTotalSpending();

        totalSpending.add(linkTo(methodOn(FpdsController.class).getTotalSpending()).withSelfRel());

        return ResponseEntity.ok().body(totalSpending);
    }

    @RequestMapping(value = "/sustainability/agencies", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Spending by Agencies")
    public HttpEntity getSpendingByAgencies(
            @RequestParam(value = "limit", required = false) Integer limit) {

        if (limit == null) {
            limit = 10;
        }

        List<ContextSpending> agencies = fpdsDAO.getSpendingByAgencies(limit);

        List<Link> links = new ArrayList<>();

        links.add(linkTo(methodOn(FpdsController.class).getSpendingByAgencies(limit)).withSelfRel());

        return ResponseEntity.ok().body(new Resources<>(agencies, links));
    }

    @RequestMapping(value = "/sustainability/agencies/{agencyId}/trend", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Spending by Agencies")
    public HttpEntity getAgencySpendingTrend(
            @PathVariable("agencyId") String agencyId) {

        List<ContextTrend> trend = fpdsDAO.getAgencyTrend(agencyId);

        List<Link> links = new ArrayList<>();

        return ResponseEntity.ok().body(new Resources<>(trend, links));
    }

    @RequestMapping(value = "/sustainability/trend", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Spending by Fereral Government")
    public HttpEntity getSpendingTrend() {
        List<Trend> trend = fpdsDAO.getTrend();

        List<Link> links = new ArrayList<>();

        links.add(linkTo(methodOn(FpdsController.class).getSpendingTrend()).withSelfRel());

        return ResponseEntity.ok().body(new Resources<>(trend, links));
    }

    @RequestMapping(value = "/sustainability/states", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Spending by States")
    public HttpEntity getSpendingByStates() {
        List<ContextSpending> states = fpdsDAO.getSpendingByStates();

        List<Link> links = new ArrayList<>();

        links.add(linkTo(methodOn(FpdsController.class).getSpendingByStates()).withSelfRel());

        return ResponseEntity.ok().body(new Resources<>(states, links));
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Transactions")
    public HttpEntity getTransactions(
        @RequestParam(value = "agencyId", required = false) String agencyId,
        @RequestParam(value = "stateCode", required = false) String state_code,
        @RequestParam(value = "offset", required = false) Integer offset,
        @RequestParam(value = "limit", required = false) Integer limit) {

        if (limit == null) {
            limit = 10;
        }

        Map<String, Object> query = new HashMap<>();
        if (agencyId != null) {
            query.put("agency_id", agencyId);
        }
        if (state_code != null) {
            query.put("state_code", state_code);
        }

        List<Transaction> transactions = fpdsDAO.getTransactions(query, offset, limit);

        int count = fpdsDAO.getTransactionCount(query);

        if (transactions != null) {
            for (Transaction t: transactions) {
            }
        }

        List<Link> links = new ArrayList<>();
        FpdsController builder = methodOn(FpdsController.class);

        // self
        links.add(linkTo(builder.getTransactions(agencyId, state_code, offset, limit)).withSelfRel());
        // navigation links
        if ((offset != null) && (offset > 0)) {
            links.add(linkTo(builder.getTransactions(agencyId, state_code, null, limit)).withRel(Link.REL_FIRST));
        }
        if (limit != null) {
            if ((transactions.size() == limit)) {
                Integer nextOffset = ((offset == null) ? 0 : offset) + limit;
                links.add(linkTo(builder.getTransactions(agencyId, state_code, nextOffset, limit)).withRel(Link.REL_NEXT));
            }
            if ((offset != null) && (offset > 0)) {
                Integer prevOffset = (offset > limit) ? offset - limit : null;
                links.add(linkTo(builder.getTransactions(agencyId, state_code, prevOffset, limit)).withRel(Link.REL_PREVIOUS));
            }
        }
        // search
        ControllerLinkBuilder searchLinkBuilder = linkTo(builder.getTransactions(null, null, null, null));
        Link searchLink = new Link(searchLinkBuilder.toString() + "{agencyId,state_code,offset,limit}", "search");
        links.add(searchLink);

        TransactionList resource = new TransactionList<>(transactions, links);
        resource.setCount(count);

        return ResponseEntity.ok().body(resource);
    }
}
