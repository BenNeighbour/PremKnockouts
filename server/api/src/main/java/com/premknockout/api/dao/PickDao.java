package com.premknockout.api.dao;

import com.premknockout.api.model.pick.Pick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @created 06/07/2020 - 21
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Repository
public interface PickDao extends JpaRepository<Pick, UUID> {

    Pick findPickById(UUID id);

}
