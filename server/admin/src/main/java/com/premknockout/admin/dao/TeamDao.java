package com.premknockout.admin.dao;

import com.premknockout.admin.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/*
 * @created 14/07/2020 - 20
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Repository
public interface TeamDao extends JpaRepository<Team, UUID> {

    Team findTeamByName(String name);

}
