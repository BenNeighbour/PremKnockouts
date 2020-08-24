package com.premknockout.api.service.knockout;

import com.premknockout.api.model.knockout.Knockout;
import com.premknockout.api.model.team.Team;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/*
 * @created 07/07/2020 - 06
 * @project PremKnockout
 * @author  Ben Neighbour
 */
public interface KnockoutService {

    //Creates a knockout
    ResponseEntity<Object> createKnockout(UUID userId, Knockout knockout);

    // Returns the current round and metadata for that knockout/round
    ResponseEntity<Object> getKnockoutMetadata(UUID knockoutId);

    // Returns a list of teams
    ResponseEntity<List<Team>> getUserAllowedTeams(UUID userId, UUID knockoutId);

    // Returns the team that they have picked
    ResponseEntity<Object> pickTeam(UUID userId, UUID knockoutId, UUID teamId);

}
