package com.premknockout.api.service.round.roundResult;

import com.premknockout.api.dao.*;
import com.premknockout.api.model.knockout.Knockout;
import com.premknockout.api.model.knockout.KnockoutKnockedOutOf;
import com.premknockout.api.model.knockout.KnockoutRoundsPicked;
import com.premknockout.api.model.pick.Pick;
import com.premknockout.api.model.round.Round;
import com.premknockout.api.model.round.roundResult.RoundResult;
import com.premknockout.api.model.team.Team;
import com.premknockout.api.model.user.User;
import com.premknockout.api.service.round.RoundServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/*
 * @created 10/07/2020 - 07
 * @project PremKnockout
 * @author Ben Neighbour
 */
@Service
public class RoundResultServiceImpl implements RoundResultService {

  @Autowired private RoundDao roundDao;

  @Autowired private RoundResultDao roundResultDao;

  @Autowired private UserDao userDao;

  @Autowired private KnockoutDao knockoutDao;

  @Autowired private PickDao pickDao;

  @Autowired private TeamDao teamDao;

  @Autowired private RoundServiceImpl roundService;

  @Autowired private RestTemplate restTemplate;

  // Gets called by the batch job running every 10 mins
  @Override
  public boolean processResults() {
    knockoutDao.findAll().stream()
        .filter(
            knockout ->
                knockout.getCurrentRound() != null
                    && knockout.getCurrentRound().getPickDeadline().before(new Date()))
        .filter(
            knockout ->
                knockout.getCurrentRound().getRoundEnding().before(new Date())
                    || knockout.getCurrentRound().getRoundEnding().equals(new Date()))
        .filter(knockout -> knockout.getRounds() != null)
        .filter(knockout -> knockout.getCurrentRound().getResult() != null)
        .filter(knockout -> !knockout.isFinished())
        .forEach(
            knockout -> {
              // Run the auto-pick
              this.autoPick(knockout);

              List<Team> wonTeams = knockout.getCurrentRound().getResult().getWinningTeams();

              // Deal with the roundResult
              this.roundResultHelper(knockout, knockout.getCurrentRound(), wonTeams);

              // Create the new round
              knockoutDao.saveAndFlush(knockout);

              this.processWinner(knockout);

              roundService.createRound(knockout);
            });

    return true;
  }

  // Gets called from processResults
  private void autoPick(Knockout focusingKnockout) {
    /*
       Get all of the users that have picked something
       during the current round
    */
    List<User> usersPicked = new ArrayList<>();

    focusingKnockout.getCurrentRound().getPicks().stream()
        .forEach(
            pick -> {
              usersPicked.add(pick.getUser());
            });

    // Compare that list of users to all of the participants that are not knocked out yet
    List<User> usersNotPicked = new ArrayList<>();

    focusingKnockout.getParticipants().stream()
        .filter(user -> !user.getKnockedOutOf().contains(focusingKnockout))
        .forEach(
            user -> {
              if (!usersPicked.contains(user)) {
                usersNotPicked.add(user);
              }
            });

    // For each user that has not picked, generate the allowed teams for that user, and pick a
    // random one
    usersNotPicked.forEach(
        user -> {
          List<Team> allowedTeams = generateAllowedTeams(focusingKnockout, user);
          Collections.shuffle(allowedTeams);

          Team chosenTeam = allowedTeams.size() > 0 ? allowedTeams.get(0) : null;

          if (chosenTeam != null) {
            Pick pick = new Pick();
            pick.setUser(user);
            pick.setTeam(chosenTeam);

            pickDao.save(pick);

            if (user.getKnockedOutOf() == null) user.setKnockoutRoundsPicked(new ArrayList<>());

            KnockoutRoundsPicked picked = new KnockoutRoundsPicked();
            picked.setKnockoutId(focusingKnockout.getId());

            user.getKnockoutRoundsPicked().add(picked);

            focusingKnockout.getCurrentRound().getPicks().add(pick);
            knockoutDao.saveAndFlush(focusingKnockout);

            userDao.saveAndFlush(user);
          } else {
            // Knock them out
            if (user.getKnockedOutOf() == null) user.setKnockedOutOf(new ArrayList<>());

            KnockoutKnockedOutOf knockedOut = new KnockoutKnockedOutOf();
            knockedOut.setKnockoutId(focusingKnockout.getId());
            user.getKnockedOutOf().add(knockedOut);

            userDao.saveAndFlush(user);
          }
        });
  }

  private List<Team> generateAllowedTeams(Knockout knockout, User user) {
    List<Team> allowedTeams = knockout.getTeams();
    List<Team> pastTeamPicks = new ArrayList<>();

    knockout.getRounds().stream()
        .filter(round -> !round.equals(knockout.getCurrentRound()))
        .forEach(
            round -> {
              round.getPicks().stream()
                  .filter(pick -> pick.getUser().equals(user))
                  .forEach(
                      pick -> {
                        boolean isNotKnockedOut =
                            user.getKnockedOutOf().stream()
                                .filter(
                                    knockoutKnockedOutOf ->
                                        knockoutKnockedOutOf
                                            .getKnockoutId()
                                            .equals(knockout.getId()))
                                .collect(Collectors.toList())
                                .isEmpty();

                        if (isNotKnockedOut) {
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

  private void roundResultHelper(Knockout knockout, Round currentRound, List<Team> winningTeams) {
    // Get all of the current picks with a winning team
    List<Pick> winningPicks =
        currentRound.getPicks().stream()
            .filter(pick -> winningTeams.contains(pick.getTeam()))
            .filter(
                pick ->
                    knockout.getParticipants().stream()
                        .filter(user -> !user.getKnockedOutOf().contains(knockout))
                        .collect(Collectors.toList())
                        .contains(pick.getUser()))
            .collect(Collectors.toList());

    List<User> participantsThrough = new ArrayList<>();

    winningPicks.forEach(pick -> participantsThrough.add(pick.getUser()));

    // With all of the participants that won, add to knocked out of all of the participants that are
    // not them
    knockout.getParticipants().stream()
        .filter(user -> !user.getKnockedOutOf().contains(knockout))
        .forEach(
            user -> {
              // If they have lost
              if (!participantsThrough.contains(user)) {
                if (user.getKnockedOutOf() == null) user.setKnockedOutOf(new ArrayList<>());

                KnockoutKnockedOutOf knockedOut = new KnockoutKnockedOutOf();
                knockedOut.setKnockoutId(knockout.getId());
                user.getKnockedOutOf().add(knockedOut);
              }

              user.getKnockoutRoundsPicked().remove(knockout);

              userDao.saveAndFlush(user);
            });
  }

  // Run by batch job each 5 mins
  @Override
  public void getRoundResults() {

    knockoutDao.findAll().stream()
        .filter(knockout -> knockout.getCurrentRound() != null)
        .filter(
            knockout ->
                knockout.getCurrentRound().getPickDeadline().before(new Date())
                    && knockout.getCurrentRound().getRoundEnding().before(new Date()))
        .filter(knockout -> knockout.getCurrentRound().getResult() == null)
        .forEach(
            knockout -> {
              // RestTemplate Request to Admin Endpoint that retrieves the data and persists it into
              // this
              // database
              // Whatever that returns is the roundResult of the current round it's knockout is on
              RoundResult result =
                  restTemplate.getForObject(
                      "http://localhost:8090/all/?knockoutId=" + knockout.getId(),
                      RoundResult.class);

              List<Team> winningTeams = new ArrayList<>();
              for (int i = 0; i < result.getWinningTeams().size(); i++) {
                if (teamDao.findTeamByName(result.getWinningTeams().get(i).getName()) != null)
                  winningTeams.add(
                      teamDao.findTeamByName(result.getWinningTeams().get(i).getName()));
              }

              result.setWinningTeams(winningTeams);

              roundResultDao.save(result);
              knockout.getCurrentRound().setResult(result);
              knockoutDao.saveAndFlush(knockout);
            });
  }

  // Function that decides if there is a winner for the current round's results
  private void processWinner(Knockout focusingKnockout) {
    if (focusingKnockout.getCurrentRound().getResult() != null) {

      // First, check the number of participants that are NOT knocked out of this one
      List<User> notKnockedOutUsers = new ArrayList<>();
      focusingKnockout.getParticipants().stream()
          .forEach(
              user -> {
                boolean isNotKnockedOut =
                    user.getKnockedOutOf().stream()
                        .filter(
                            knockoutKnockedOutOf ->
                                knockoutKnockedOutOf
                                    .getKnockoutId()
                                    .equals(focusingKnockout.getId()))
                        .collect(Collectors.toList())
                        .isEmpty();

                if (isNotKnockedOut) notKnockedOutUsers.add(user);
              });

      if (notKnockedOutUsers.size() == 1) {
        focusingKnockout.setWinner(notKnockedOutUsers.get(0));
        focusingKnockout.setFinished(true);
      }

      knockoutDao.saveAndFlush(focusingKnockout);
    }
  }
}
