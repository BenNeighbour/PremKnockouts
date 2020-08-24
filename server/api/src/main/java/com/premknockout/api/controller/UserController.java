package com.premknockout.api.controller;

import com.premknockout.api.model.knockout.Knockout;
import com.premknockout.api.model.team.Team;
import com.premknockout.api.model.user.User;
import com.premknockout.api.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/*
 * @created 07/07/2020 - 19
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value = "/knockouts/all/")
    public ResponseEntity<List<Object>> getUserKnockouts(@RequestParam("userId") UUID userId) {
        return userService.getUserKnockouts(userId);
    }

}

