package com.premknockout.admin.dao;

import com.premknockout.admin.model.RoundResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/*
 * @created 14/07/2020 - 08
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Repository
public interface RoundResultDao extends JpaRepository<RoundResult, UUID> {

    RoundResult findByKnockoutId(UUID knockoutId);

}
