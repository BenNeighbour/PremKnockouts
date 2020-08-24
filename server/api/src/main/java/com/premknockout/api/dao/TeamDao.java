package com.premknockout.api.dao;

import com.premknockout.api.model.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/*
 * @created 07/07/2020 - 07
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Repository
public interface TeamDao extends JpaRepository<Team, UUID> {

    Team findTeamById(UUID id);

    Team findTeamByName(String name);

}
