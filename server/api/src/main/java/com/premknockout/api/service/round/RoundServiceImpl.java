package com.premknockout.api.service.round;

import com.premknockout.api.dao.KnockoutDao;
import com.premknockout.api.dao.KnockoutRoundsPickedDao;
import com.premknockout.api.dao.RoundDao;
import com.premknockout.api.dao.UserDao;
import com.premknockout.api.model.knockout.Knockout;
import com.premknockout.api.model.knockout.KnockoutRoundsPicked;
import com.premknockout.api.model.round.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * @created 07/07/2020 - 08
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Service
public class RoundServiceImpl implements RoundService {

  @Autowired private RoundDao roundDao;

  @Autowired private KnockoutDao knockoutDao;

  @Autowired private KnockoutRoundsPickedDao knockoutRoundsPickedDao;

  @Autowired private UserDao userDao;

  @Override
  public Round createRound(Knockout parentKnockout) {

    if (!parentKnockout.isFinished()) {
      Round appendingRound = new Round();

      if (parentKnockout.getCurrentRound() == null) appendingRound.setRoundNumber(1);
      else appendingRound.setRoundNumber(parentKnockout.getCurrentRound().getRoundNumber() + 1);

      if (parentKnockout.getRounds() == null) {
        parentKnockout.setRounds(new ArrayList<>());
      }

      parentKnockout.getRounds().add(appendingRound);
      parentKnockout.setCurrentRound(appendingRound);
      parentKnockout.getParticipants().stream()
          .forEach(
              user -> {
                List<KnockoutRoundsPicked> keptKnockoutRoundsForUser = new ArrayList<>();

                user.getKnockoutRoundsPicked().stream()
                    .filter(
                        knockoutRoundsPicked ->
                            !knockoutRoundsPicked.getKnockoutId().equals(parentKnockout.getId()))
                    .forEach(
                        knockoutRoundsPicked -> {
                          keptKnockoutRoundsForUser.add(knockoutRoundsPicked);
                        });
                user.setKnockoutRoundsPicked(keptKnockoutRoundsForUser);
                userDao.saveAndFlush(user);

                keptKnockoutRoundsForUser.stream()
                    .forEach(
                        one -> {
                          knockoutRoundsPickedDao.delete(one);
                        });
              });

      roundDao.save(appendingRound);
      knockoutDao.saveAndFlush(parentKnockout);
      return appendingRound;
    }

    return null;
  }
}
