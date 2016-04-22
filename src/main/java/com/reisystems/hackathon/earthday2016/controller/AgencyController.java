package com.reisystems.hackathon.earthday2016.controller;

import com.reisystems.hackathon.earthday2016.model.Context;
import com.reisystems.hackathon.earthday2016.model.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/agencies")
@Api(tags = "Agencies", description = "Federal Agencies")
public class AgencyController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    @ApiOperation(value = "Load Agency Information")
    public HttpEntity getPerson(
            @PathVariable("id") String agencyId) {

        Context context = new Context();
        context.setIdentifier(agencyId);
        context.setAcronym("A" + agencyId);
        context.setName("Name " + agencyId);
        return ResponseEntity.ok().body(context);
    }
}
