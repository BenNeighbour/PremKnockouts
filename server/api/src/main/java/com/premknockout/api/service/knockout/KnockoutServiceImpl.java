package com.premknockout.api.service.knockout;

import com.premknockout.api.dao.*;
import com.premknockout.api.exeptions.UnauthorizedException;
import com.premknockout.api.model.knockout.Knockout;
import com.premknockout.api.model.knockout.KnockoutRoundsPicked;
import com.premknockout.api.model.pick.Pick;
import com.premknockout.api.model.team.Team;
import com.premknockout.api.model.user.User;
import com.premknockout.api.service.round.RoundService;
import com.premknockout.api.service.round.RoundServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/*
 * @created 07/07/2020 - 07
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Service
public class KnockoutServiceImpl implements KnockoutService {

  @Autowired private KnockoutDao knockoutDao;

  @Autowired private UserDao userDao;

  @Autowired private TeamDao teamDao;

  @Autowired private PickDao pickDao;

  @Autowired private RoundService roundService;

  @Override
  @Transactional
  public ResponseEntity<Object> createKnockout(UUID userId, Knockout knockout)
      throws UnauthorizedException {
    if (userDao.findUserById(userId) != null) {
      User firstParticipant = userDao.findUserById(userId);

      knockout.setParticipants(new ArrayList<>());
      knockout.getParticipants().add(firstParticipant);

      roundService.createRound(knockout);

      return ResponseEntity.ok(knockoutDao.save(knockout));
    }

    throw new UnauthorizedException("Something's not right...");
  }

  @Override
  public ResponseEntity<Object> getKnockoutMetadata(UUID knockoutId) {
    return ResponseEntity.ok(knockoutDao.findKnockoutById(knockoutId));
  }

  @Override
  public ResponseEntity<List<Team>> getUserAllowedTeams(UUID userId, UUID knockoutId)
      throws UnauthorizedException {
    if (userDao.findUserById(userId) != null && knockoutDao.findKnockoutById(knockoutId) != null) {
      User user = userDao.findUserById(userId);
      Knockout knockout = knockoutDao.findKnockoutById(knockoutId);

      return ResponseEntity.ok(generateAllowedTeams(knockout, user));
    }

    throw new UnauthorizedException("Something's not right...");
  }

  @Override
  public ResponseEntity<Object> pickTeam(UUID userId, UUID knockoutId, UUID teamId) {
    if (userDao.findUserById(userId) != null
        && knockoutDao.findKnockoutById(knockoutId) != null
        && teamDao.findTeamById(teamId) != null) {
      User user = userDao.findUserById(userId);
      Knockout knockout = knockoutDao.findKnockoutById(knockoutId);
      Team requestedTeam = teamDao.findTeamById(teamId);

      boolean isNotKnockedOut = user.getKnockedOutOf()
              .stream()
              .filter(knockoutKnockedOutOf -> knockoutKnockedOutOf.getKnockoutId().equals(knockout.getId()))
              .collect(Collectors.toList()).isEmpty();

      if (isNotKnockedOut
          && knockout.getCurrentRound().getPickDeadline().after(new Date())) {
        List<Team> allowedTeams = generateAllowedTeams(knockout, user);

        if (allowedTeams.contains(requestedTeam)) {
          Pick pick = new Pick();
          pick.setUser(user);
          pick.setTeam(requestedTeam);

          pickDao.save(pick);

          if (user.getKnockedOutOf() == null) user.setKnockoutRoundsPicked(new ArrayList<>());

          KnockoutRoundsPicked picked = new KnockoutRoundsPicked();
          picked.setKnockoutId(knockout.getId());

          user.getKnockoutRoundsPicked().add(picked);

          knockout.getCurrentRound().getPicks().add(pick);
          knockoutDao.saveAndFlush(knockout);

          userDao.saveAndFlush(user);

          return ResponseEntity.ok().body(pick);
        } else {
          return ResponseEntity.badRequest().body("You cannot pick this team");
        }
      }
    }

    throw new UnauthorizedException("Something is not right...");
  }

  private List<Team> generateAllowedTeams(Knockout knockout, User user) {
    List<Team> allowedTeams = knockout.getTeams();

    // This does not get added/pushed to...
    List<Team> pastTeamPicks = new ArrayList<>();

    knockout.getRounds().stream()
        .filter(round -> !round.equals(knockout.getCurrentRound()))
        .forEach(
            round -> {
              round.getPicks().stream()
                  .filter(pick -> pick.getUser().equals(user))
                  .forEach(
                      pick -> {
                        if (!user.getKnockedOutOf().contains(knockout)) {
                          pastTeamPicks.add(pick.getTeam());

                          if (pastTeamPicks.contains(pick.getTeam())) {
                            // Pop from those teams here
                            allowedTeams.remove(pick.getTeam());
                          }
                        } else {
                          allowedTeams.removeAll(allowedTeams);
                        }
                      });
            });

    return allowedTeams;
  }
}
