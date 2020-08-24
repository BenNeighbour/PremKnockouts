package com.premknockout.api.service.user;

import com.premknockout.api.dao.KnockoutDao;
import com.premknockout.api.dao.UserDao;
import com.premknockout.api.exeptions.UnauthorizedException;
import com.premknockout.api.model.other.LoginRequest;
import com.premknockout.api.model.other.LoginResponse;
import com.premknockout.api.model.user.User;
import com.premknockout.api.model.user.role.Role;
import com.premknockout.api.model.user.securityUser.SecurityUser;
import com.premknockout.api.security.token.CookieUtil;
import com.premknockout.api.security.token.JWTResponse;
import com.premknockout.api.security.token.Token;
import com.premknockout.api.security.token.TokenProvider;
import com.premknockout.api.service.round.roundResult.RoundResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/*
 * @created 03/07/2020 - 07
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserDao userDao;

  @Autowired private TokenProvider tokenProvider;

  @Autowired private CookieUtil cookieUtil;

  @Autowired private PasswordEncoder encoder;

  @Autowired private KnockoutDao knockoutDao;

  @Autowired private RoundResultServiceImpl resultService;

  @Override
  public ResponseEntity<Object> saveUser(User user) throws ValidationException {
    if (userDao.findUserByUsername(user.getUsername()) == null
        && userDao.findUserByEmail(user.getEmail()) == null) {

      // TODO: Change this!
      user.setAccountEnabled(true);

      List<Role> userRole = new ArrayList<>();
      userRole.add(new Role());

      user.setPassword(encoder.encode(user.getPassword()));
      user.setRole(userRole);

      return ResponseEntity.ok(userDao.save(user));
    } else {
      return ResponseEntity.badRequest().body("Username or Email is already taken");
    }
  }

  @Override
  public ResponseEntity<Object> updateUser(User user) {
    user.setAccountEnabled(true);
    return ResponseEntity.ok(userDao.saveAndFlush(user));
  }

  @Override
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userDao.findAll());
  }

  @Override
  public ResponseEntity<Object> getUser(UUID id) {
    return ResponseEntity.ok(userDao.findUserById(id));
  }

  @Override
  public void deleteUser(UUID id) {
    userDao.deleteById(id);
  }

  @Override
  public ResponseEntity<LoginResponse> login(
      LoginRequest loginRequest, String accessToken, String refreshToken)
      throws UnauthorizedException {
    String username = loginRequest.getUsername();

    User user = userDao.findUserByUsername(username);

    if (user == null) throw new UnauthorizedException("User does not exist!");

    Boolean accessTokenValid = tokenProvider.validateToken(accessToken);
    Boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);

    HttpHeaders responseHeaders = new HttpHeaders();
    Token newAccessToken = new Token();
    Token newRefreshToken = new Token();

    if (!accessTokenValid && !refreshTokenValid) {
      accessTokenValid = true;
      refreshTokenValid = true;
    }

    if (!accessTokenValid && refreshTokenValid) {
      accessTokenValid = true;
    }

    if (accessTokenValid && refreshTokenValid) {
      newAccessToken = tokenProvider.generateAccessToken(user.getUsername());
      newRefreshToken = tokenProvider.generateRefreshToken(user.getUsername());
      addAccessTokenCookie(responseHeaders, newAccessToken);
      addRefreshTokenCookie(responseHeaders, newRefreshToken);
    }

    // TODO: DELETE THESE AFTER BATCH JOB
    resultService.getRoundResults();
    resultService.processResults();

    LoginResponse loginResponse =
        new LoginResponse(new JWTResponse(newAccessToken, newRefreshToken.getTokenValue()));
    return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
  }

  @Override
  public ResponseEntity<List<Object>> getUserKnockouts(UUID userId) {
    if (userDao.findUserById(userId) != null) {
      User targetUser = userDao.findUserById(userId);

      return ResponseEntity.ok(
          knockoutDao.findAll().stream()
              .filter(knockout -> knockout.getParticipants().contains(targetUser))
              .collect(Collectors.toList()));
    }

    return ResponseEntity.ok(new ArrayList<>());
  }

  @Override
  public ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken)
      throws IllegalArgumentException {
    Boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);

    if (!refreshTokenValid) {
      throw new IllegalArgumentException("Refresh Token is invalid!");
    }

    String usernameFromToken = tokenProvider.getUsernameFromToken(refreshToken);

    Token newAccessToken = tokenProvider.generateAccessToken(usernameFromToken);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add(
        HttpHeaders.SET_COOKIE,
        cookieUtil
            .createAccessTokenCookie(newAccessToken.getTokenValue(), newAccessToken.getDuration())
            .toString());

    LoginResponse loginResponse = new LoginResponse(new JWTResponse(newAccessToken, refreshToken));
    return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
  }

  @Override
  public ResponseEntity<Object> getUserProfile() throws UnauthorizedException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication.getPrincipal() != "anonymousUser") {
      SecurityUser customUserDetails = (SecurityUser) authentication.getPrincipal();

      User user = userDao.findUserByUsername(customUserDetails.getUsername());

      return ResponseEntity.ok(user);
    } else {
      throw new UnauthorizedException("No user was extracted out of cookie!");
    }
  }

  private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token) {
    httpHeaders.add(
        HttpHeaders.SET_COOKIE,
        cookieUtil.createAccessTokenCookie(token.getTokenValue(), token.getDuration()).toString());
  }

  private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token) {
    httpHeaders.add(
        HttpHeaders.SET_COOKIE,
        cookieUtil.createRefreshTokenCookie(token.getTokenValue(), token.getDuration()).toString());
  }
}
