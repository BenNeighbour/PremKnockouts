package com.premknockout.api;

import com.premknockout.api.dao.*;
import com.premknockout.api.model.knockout.Knockout;
import com.premknockout.api.model.round.Round;
import com.premknockout.api.model.team.Team;
import com.premknockout.api.model.user.User;
import com.premknockout.api.service.knockout.KnockoutServiceImpl;
import com.premknockout.api.service.round.RoundService;
import com.premknockout.api.service.round.RoundServiceImpl;
import com.premknockout.api.service.round.roundResult.RoundResultServiceImpl;
import com.premknockout.api.service.user.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/*
 * @created 11/07/2020 - 21
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@RunWith(MockitoJUnitRunner.class)
public class RoundResultServiceTest {

  @Mock RoundDao roundDao;

  @Mock PasswordEncoder encoder;

  @Mock RoundResultDao roundResultDao;

  @Mock UserDao userDao;

  @Mock KnockoutDao knockoutDao;

  @Mock PickDao pickDao;

  @Mock TeamDao teamDao;

  @Mock RoundService roundService;

  @InjectMocks UserServiceImpl userService;

  @InjectMocks KnockoutServiceImpl knockoutService;

  @InjectMocks RoundResultServiceImpl service;

  @InjectMocks RoundServiceImpl roundServiceImpl;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void roundResultShouldPass() {

    // Create all of the objects needed
    User user = new User();
    user.setId(UUID.randomUUID());
    user.setUsername("John Doe");
    user.setPassword("johndoe");
    user.setEmail("jon@doe.com");
    user.setDateOfBirth(new Date());

    BDDMockito.given(userDao.save(user))
        .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

    User savedUser = (User) userService.saveUser(user).getBody();

    Assertions.assertThat(savedUser).isNotNull();

    Team team = new Team();
    team.setAbbreviation("BHA");
    team.setName("Brighton");

    Team team2 = new Team();
    team2.setAbbreviation("LIV");
    team2.setName("Liverpool");

    List<Team> teams = new ArrayList<>();
    teams.add(team);
    teams.add(team2);

    Knockout knockout = new Knockout();
    knockout.setId(UUID.randomUUID());
    knockout.setName("John's Knockout");
    knockout.setTeams(teams);

    BDDMockito.given(userDao.findUserById(savedUser.getId())).willReturn(savedUser);

    BDDMockito.given(knockoutDao.save(knockout))
        .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
    Knockout savedKnockout =
        (Knockout) knockoutService.createKnockout(savedUser.getId(), knockout).getBody();

    List<Knockout> knockouts = new ArrayList<>();
    knockouts.add(savedKnockout);

    Assertions.assertThat(knockouts).isNotNull();

    BDDMockito.given(knockoutDao.findAll()).willReturn(knockouts);

    boolean res = service.processResults();

    Assert.assertEquals(true, res);
  }
}
