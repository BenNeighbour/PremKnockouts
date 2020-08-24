package com.premknockout.api.controller;

import com.premknockout.api.dao.KnockoutDao;
import com.premknockout.api.model.knockout.Knockout;
import com.premknockout.api.model.team.Team;
import com.premknockout.api.service.knockout.KnockoutServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/*
 * @created 07/07/2020 - 19
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@RestController
@RequestMapping("/api/knockout")
public class KnockoutController {

    @Autowired
    private KnockoutServiceImpl knockoutService;

    @Autowired
    private KnockoutDao knockoutDao;

    @PostMapping(value = "/create/")
    public ResponseEntity<Object> createKnockout(@RequestParam("userId") UUID userId, @Valid @RequestBody Knockout knockout) {
        return knockoutService.createKnockout(userId, knockout);
    }

    @GetMapping(value = "/picks/allowed/")
    public ResponseEntity<List<Team>> getUserAllowedPicks(@RequestParam("userId") UUID userId, @RequestParam("knockoutId") UUID knockoutId) {
        return knockoutService.getUserAllowedTeams(userId, knockoutId);
    }

    @PostMapping(value = "/picks/pick/")
    public ResponseEntity<Object> pickTeam(@RequestParam("userId") UUID userId, @RequestParam("knockoutId") UUID knockoutId, @RequestParam("teamId") UUID teamId) {
        return knockoutService.pickTeam(userId, knockoutId, teamId);
    }

    @GetMapping(value = "/by/name/")
    public ResponseEntity<Object> getKnockoutByName(@RequestParam("knockoutName") String name) {
        return ResponseEntity.ok().body(knockoutDao.findKnockoutByName(name));
    }

}

