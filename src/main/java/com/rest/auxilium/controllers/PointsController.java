package com.rest.auxilium.controllers;


import com.rest.auxilium.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class PointsController {

    @Autowired
    private PointsService pointsService;

    @RequestMapping(method = RequestMethod.GET, value = "/points/{uuid}")
    public Long getUserAvailablePoints(@PathVariable String uuid) { return pointsService.getAllUserPoints(uuid); }
}
