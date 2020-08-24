package com.premknockout.api.dao;

import com.premknockout.api.model.round.roundResult.RoundResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/*
 * @created 06/07/2020 - 21
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Repository
public interface RoundResultDao extends JpaRepository<RoundResult, UUID> {



}
