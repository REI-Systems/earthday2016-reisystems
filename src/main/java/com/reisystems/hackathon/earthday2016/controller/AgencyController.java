package com.reisystems.hackathon.earthday2016.controller;

import com.reisystems.hackathon.earthday2016.dao.AgencyDAO;
import com.reisystems.hackathon.earthday2016.model.Context;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/agencies")
@Api(tags = "Agencies", description = "Federal Agencies")
public class AgencyController {

    @Autowired
    private AgencyDAO agencyDAO;

    @RequestMapping(value = "/{agencyId}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Load Agency Information")
    public HttpEntity getPerson(
            @PathVariable("agencyId") String agencyId) {

        Context context = agencyDAO.getAgency(agencyId);
        if (context == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok().body(context);
        }
    }
}
