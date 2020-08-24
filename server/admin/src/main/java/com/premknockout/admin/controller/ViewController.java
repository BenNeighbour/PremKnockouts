package com.premknockout.admin.controller;

import com.premknockout.admin.dao.RoundResultDao;
import com.premknockout.admin.dao.TeamDao;
import com.premknockout.admin.model.RoundResult;
import com.premknockout.admin.model.RoundResultForm;
import com.premknockout.admin.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/*
 * @created 14/07/2020 - 19
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Controller
@RequestMapping("/")
public class ViewController {

  @Value("${adminServerUrl}") private String urlProperty;

  @Autowired private TeamDao teamDao;

  @Autowired private RoundResultDao roundResultDao;

  @RequestMapping("dashboard/")
  public String dashboard(Model model) {
    List<String> url = new ArrayList<>();
    url.add(urlProperty);

    List<Integer> numberOfMatches = new ArrayList<>();
//    for (int i = 0; i < 10; i++) {
      numberOfMatches.add(1);
//    }

    model.addAttribute("matches", numberOfMatches);
    model.addAttribute("teams", teamDao.findAll());
    model.addAttribute("form", new RoundResultForm());
    model.addAttribute("url", url);

    return "result";
  }

}
