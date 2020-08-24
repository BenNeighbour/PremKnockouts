package com.premknockout.api.dao;

import com.premknockout.api.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

//import java.util.UUID;

/*
 * @created 03/07/2020 - 07
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Repository
public interface UserDao extends JpaRepository<User, UUID> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User findUserById(UUID id);

}
