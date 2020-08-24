package com.premknockout.api.dao;

import com.premknockout.api.model.knockout.Knockout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/*
 * @created 06/07/2020 - 21
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Repository
public interface KnockoutDao extends JpaRepository<Knockout, UUID> {

  Knockout findKnockoutById(UUID id);

  Knockout findKnockoutByName(String name);

}
