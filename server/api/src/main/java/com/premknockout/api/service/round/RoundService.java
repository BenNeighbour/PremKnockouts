package com.premknockout.api.service.round;

import com.premknockout.api.model.knockout.Knockout;
import com.premknockout.api.model.round.Round;

import java.util.UUID;

/*
 * @created 07/07/2020 - 08
 * @project PremKnockout
 * @author  Ben Neighbour
 */
public interface RoundService {

    Round createRound(Knockout parentKnockout);

}
