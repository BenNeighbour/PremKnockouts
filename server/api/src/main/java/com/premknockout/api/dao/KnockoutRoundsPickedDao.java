package com.premknockout.api.dao;

import com.premknockout.api.model.knockout.KnockoutRoundsPicked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/*
 * @created 17/07/2020 - 20
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Repository
public interface KnockoutRoundsPickedDao extends JpaRepository<KnockoutRoundsPicked, UUID> {



}
