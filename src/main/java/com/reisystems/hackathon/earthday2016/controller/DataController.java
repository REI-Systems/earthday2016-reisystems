package com.reisystems.hackathon.earthday2016.controller;

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
@RequestMapping("/v1/data")
public class DataController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaTypes.HAL_JSON_VALUE)
    public HttpEntity home() {
        List<Object> data = new ArrayList<>();
        data.add("Hello Team");

        List<Link> links = new ArrayList<>();

        return ResponseEntity.ok().body(new Resources<>(data, links));
    }
}
