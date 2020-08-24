package com.premknockout.admin.controller;

import com.premknockout.admin.dao.RoundResultDao;
import com.premknockout.admin.model.RoundResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/*
 * @created 15/07/2020 - 07
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@RestController
@RequestMapping("/")
public class RootController {

  @Autowired private RoundResultDao roundResultDao;

  @GetMapping("all/")
  public RoundResult getAllRoundResults(@RequestParam("knockoutId") UUID knockoutId) {
    return roundResultDao.findByKnockoutId(knockoutId);
  }
}
