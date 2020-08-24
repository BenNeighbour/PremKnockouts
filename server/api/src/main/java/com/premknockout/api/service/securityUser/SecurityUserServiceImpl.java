package com.premknockout.api.service.securityUser;

import com.premknockout.api.dao.UserDao;
import com.premknockout.api.model.user.User;
import com.premknockout.api.model.user.securityUser.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
 * @created 03/07/2020 - 07
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Service
public class SecurityUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao dao;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> optionalUser = Optional.ofNullable(dao.findUserByUsername(name));
        optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username or Password is incorrect"));

        UserDetails userDetails = new SecurityUser(optionalUser.get());
        new AccountStatusUserDetailsChecker().check(userDetails);

        return userDetails;
    }

}
