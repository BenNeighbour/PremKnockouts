package com.premknockout.admin.controller;

import com.premknockout.admin.dao.RoundResultDao;
import com.premknockout.admin.dao.TeamDao;
import com.premknockout.admin.model.RoundResult;
import com.premknockout.admin.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/*
 * @created 14/07/2020 - 07
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@RestController
@RequestMapping("/admin/result")
public class RoundResultController {

  @Autowired private RoundResultDao roundResultDao;

  @Autowired private TeamDao teamDao;

  @PostMapping("/new/")
  public String submitCurrentRoundResults(
      @RequestParam("knockoutId") String knockoutId, @RequestBody List<String> winningTeams) {
    RoundResult oldRound = roundResultDao.findByKnockoutId(UUID.fromString(knockoutId));
    if (oldRound != null) roundResultDao.delete(oldRound);

    RoundResult roundResult = new RoundResult();
    roundResult.setKnockoutId(UUID.fromString(knockoutId));

    List<Team> winningTeamsObj = new ArrayList<>();
    for (int i = 0; i < winningTeams.size(); i++) {
      winningTeamsObj.add(teamDao.findTeamByName(winningTeams.get(i)));
    }

    roundResult.setWinningTeams(winningTeamsObj);

    roundResultDao.save(roundResult);
    return "";
  }
}
