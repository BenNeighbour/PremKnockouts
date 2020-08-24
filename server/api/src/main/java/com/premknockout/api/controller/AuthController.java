package com.premknockout.api.controller;

import com.premknockout.api.model.other.LoginRequest;
import com.premknockout.api.model.other.LoginResponse;
import com.premknockout.api.model.user.User;
import com.premknockout.api.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/*
 * @created 03/07/2020 - 20
 * @project PremKnockout
 * @author  Ben Neighbour
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;


    @PostMapping(value = "/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return userService.login(loginRequest, accessToken, refreshToken);
    }

    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> refreshToken(@CookieValue(name = "accessToken", required = false) String accessToken,
                                                      @CookieValue(name = "refreshToken", required = false) String refreshToken) {

        return userService.refresh(accessToken, refreshToken);
    }

    @PostMapping(value = "/onboarding/")
    public ResponseEntity<String> onboarding() {

        return new ResponseEntity<>("Successfully onboarded!", HttpStatus.OK);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {

        List<Cookie> cookies = Arrays.asList(request.getCookies());
        cookies.forEach(cookie -> {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath("/");

            response.addCookie(cookie);
        });

        return new ResponseEntity<>("Logged out!", HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<Object> me() {
        return userService.getUserProfile();
    }
}
