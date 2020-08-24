package com.premknockout.api.service.user;

import com.premknockout.api.exeptions.UnauthorizedException;
import com.premknockout.api.model.other.LoginRequest;
import com.premknockout.api.model.other.LoginResponse;
import com.premknockout.api.model.user.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/*
 * @created 03/07/2020 - 07
 * @project PremKnockout
 * @author  Ben Neighbour
 */
public interface UserService {

    ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken);

    ResponseEntity<LoginResponse> refresh(String accessToken, String refreshToken);

    ResponseEntity<Object> getUserProfile() throws UnauthorizedException;

    ResponseEntity<Object> saveUser(User user);

    ResponseEntity<Object> updateUser(User user);

    ResponseEntity<List<User>> getAllUsers();

    ResponseEntity<Object> getUser(UUID id);

    ResponseEntity<List<Object>> getUserKnockouts(UUID userId);

    void deleteUser(UUID id);

}
