package com.premknockout.api.controller;

import com.premknockout.api.dao.TeamDao;
import com.premknockout.api.model.team.Team;
import com.premknockout.api.service.knockout.KnockoutServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

/*
 * @created 07/07/2020 - 19
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private KnockoutServiceImpl knockoutService;

    @GetMapping(value = "/all/")
    public List<Team> getAllTeams() {
        List<Team> teams = teamDao.findAll();

        return teams;
    }


}

